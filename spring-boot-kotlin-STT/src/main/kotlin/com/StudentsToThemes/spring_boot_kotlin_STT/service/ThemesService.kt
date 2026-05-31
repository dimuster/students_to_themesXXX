package com.StudentsToThemes.spring_boot_kotlin_STT.service

import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.CreateThemeRequest
import com.StudentsToThemes.spring_boot_kotlin_STT.entity.StudentEntity
import com.StudentsToThemes.spring_boot_kotlin_STT.exception.StudentNotFoundException
import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.StudentWithPriorityDto
import com.StudentsToThemes.spring_boot_kotlin_STT.exception.ThemeNotFoundException
import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.ThemeResponseDto
import com.StudentsToThemes.spring_boot_kotlin_STT.entity.ThemeSpecializationStudent
import com.StudentsToThemes.spring_boot_kotlin_STT.queriesBuilder.ThemeSpecifications
import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.ThemeWithPriorityDto
import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.UpdateThemePriorityRequest
import com.StudentsToThemes.spring_boot_kotlin_STT.DTO.UpdateThemeRequest
import com.StudentsToThemes.spring_boot_kotlin_STT.repository.StudentsRepository
import com.StudentsToThemes.spring_boot_kotlin_STT.repository.ThemeSpecializationStudentRepository
import com.StudentsToThemes.spring_boot_kotlin_STT.repository.ThemesRepository
import jakarta.persistence.EntityManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * Service class for managing themes, specializations, and student-theme relationships.
 * Provides CRUD operations and specialized methods for theme and student management.
 *
 * @property themesRepository Repository for theme operations
 * @property studentsRepository Repository for student operations
 * @property themeSpecializationStudentRepository Repository for theme-specialization-student relationships
 * @property mlSortingService Service for ML-based student sorting
 * @property entityManager JPA entity manager for cache management
 */
@Service
@Transactional
class ThemesService(
    private val themesRepository: ThemesRepository,
    private val studentsRepository: StudentsRepository,
    private val themeSpecializationStudentRepository: ThemeSpecializationStudentRepository,
    private val mlSortingService: MLSortingService, // Добавляем ML сервис
    private val entityManager: EntityManager
) {
    private val log = LoggerFactory.getLogger(ThemesService::class.java)

    /**
     * Get all themes.
     * @return a list of themes
     */
    fun getThemes(name: String?, description: String?, author: String?): List<ThemeResponseDto> {
        log.debug("searching themes with filters - name: {}, description: {}, author: {}"
            , name, description, author)

        //Build dynamic query
        val spec = ThemeSpecifications.createSearchSpecification(
            name = name,
            description = description,
            author = author
        )
        return themesRepository.findAll(spec).map { it.toResponseDto() }
    }

    /**
     * Get theme by id.
     * @param id the id of the theme to get
     * @return the theme
     */
    fun getThemeById(id: UUID): ThemeResponseDto {
        log.info("Getting theme by id: {}", id)
        return themesRepository.findById(id)
            .map { it.toResponseDto() }
            .orElseThrow { ThemeNotFoundException(id) }
    }

    /**
     * Create a new theme.
     * @param createRequest the request containing the theme details
     * @return the created theme
     */
    fun createTheme(createRequest: CreateThemeRequest): ThemeResponseDto {
        log.info("Creating new theme: {}", createRequest.name)

        try {
            // Validation of the request is performed in the init block of CreateThemeRequest

            val theme = createRequest.toEntity()

            // If priorityStudents are given, add them to the theme
            if (createRequest.priorityStudents.isNotEmpty()) {
                val students = studentsRepository.findAllById(createRequest.priorityStudents)
                val foundIds = students.map { it.id!! }
                val missingIds = createRequest.priorityStudents - foundIds.toSet()

                if (missingIds.isNotEmpty()) {
                    throw StudentNotFoundException(missingIds.first())
                }

                theme.priorityStudents.addAll(students)
            }

            val savedTheme = themesRepository.save(theme)
            log.info("Successfully created theme with id: {}", savedTheme.id)

            return savedTheme.toResponseDto()
        } catch (e: IllegalArgumentException) {
            log.error("Validation error while creating theme: {}", e.message)
            throw e // Rethrow for controller to handle
        }
    }

    /**
     * Update the priority of students in a theme.
     * @param themeId the id of the theme to update
     * @param updateRequest the request containing the updated student priorities
     * @return the updated theme
     */
    fun updateThemePriority(themeId: UUID, updateRequest: UpdateThemePriorityRequest): ThemeResponseDto {
        log.info("Updating priorities for theme: {}", themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        val students = studentsRepository.findAllById(updateRequest.studentIds)

        // Clear and add students in the order specified in the request
        theme.priorityStudents.clear()
        theme.priorityStudents.addAll(students)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully updated priorities for theme: {}", themeId)

        return updatedTheme.toResponseDto()
    }

    /**
     * Update an existing theme.
     * @param themeId the id of the theme to update
     * @param updateRequest the request containing the updated theme details
     * @return the updated theme
     */
    fun updateTheme(themeId: UUID, updateRequest: UpdateThemeRequest): ThemeResponseDto {
        log.info("Updating theme: {}", themeId)

        try {
            // Validation is already in init block of UpdateThemeRequest

            val theme = themesRepository.findById(themeId)
                .orElseThrow { ThemeNotFoundException(themeId) }

            theme.name = updateRequest.name
            theme.description = updateRequest.description
            theme.author = updateRequest.author
            theme.priorityStudents = updateRequest.priorityStudents.map {
                studentsRepository.findById(it).orElseThrow { StudentNotFoundException(it) }
            }.toMutableList()

            // Update specializations
            theme.updateSpecializations(updateRequest.specializations)

            val updatedTheme = themesRepository.save(theme)
            log.info("Successfully updated theme: {}", themeId)

            return updatedTheme.toResponseDto()
        } catch (e: IllegalArgumentException) {
            log.error("Validation error while updating theme: {}", e.message)
            throw e
        }
    }

    /**
     * Delete a theme by id.
     * @param themeId the id of the theme to delete
     * @return the deleted theme
     */
    fun deleteTheme(themeId: UUID): ThemeResponseDto {
        log.info("Deleting theme: {}", themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        themesRepository.delete(theme)
        log.info("Successfully deleted theme: {}", themeId)

        return theme.toResponseDto()
    }

    /**
     * Delete multiple themes by their ids.
     * @param themeIds the ids of the themes to delete
     */
    fun deleteThemes (themeIds: List<UUID>) {
        log.info("Deleting themes: {}", themeIds)

        val themes = themesRepository.findAllById(themeIds)
        themesRepository.deleteAll(themes)
        log.info("Successfully deleted themes: {}", themeIds)
    }

    /**
     * Add a student to a theme.
     * @param themeId the id of the theme to add the student to
     * @param studentId the id of the student to add
     * @return the updated theme
     */
    fun addStudentToTheme(themeId: UUID, studentId: UUID): ThemeResponseDto {
        log.info("Adding student {} to theme {}", studentId, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        val student = studentsRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException(studentId) }

        if (theme.priorityStudents.contains(student)) {
            log.info("Student {} is already in theme {}", studentId, themeId)
            return theme.toResponseDto()
        }
        // Add student to the end of the list (lowest priority)
        theme.priorityStudents.add(student)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully added student to theme")

        return updatedTheme.toResponseDto()
    }

    /**
     * Add multiple students to a theme.
     * @param themeId the id of the theme to add the students to
     * @param studentIds the ids of the students to add
     * @return the updated theme
     */
    fun addStudentsToTheme(themeId: UUID, studentIds: List<UUID>): ThemeResponseDto {
        log.info("Adding students {} to theme {}", studentIds, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        val students = studentsRepository.findAllById(studentIds)
        val foundIds = students.map { it.id!! }
        val missingIds = studentIds - foundIds.toSet()

        if (missingIds.isNotEmpty()) {
            log.error("Students not found with ids: {}", missingIds)
            throw StudentNotFoundException(missingIds.first())
        }

        log.info("Found {} students with given filters", students.size)

        // Create a list of students to add
        val studentsToAdd = mutableListOf<StudentEntity>()

        // Check if each student is already in the theme and add them to the list if not
        students.forEach { student ->
            if (theme.priorityStudents.contains(student)) {
                log.info("Student {} is already in theme {}", student.id, themeId)
            } else {
                studentsToAdd.add(student)
            }
        }

        theme.priorityStudents.addAll(studentsToAdd)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully added {} students to theme", studentsToAdd.size)

        return updatedTheme.toResponseDto()
    }

    /**
     * Change the activity of students in a theme.
     * @param themeId the id of the theme to change the activity of the students
     * @param active the new activity status
     * @return the updated theme
     */
    fun changeStudentsActivityInTheme(themeId: UUID, active: Boolean): ThemeResponseDto {
        log.info("Changing activity of students in theme {}", themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        theme.priorityStudents.forEach { it.active = active }

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully changed activity of students in theme {}", themeId)

        return updatedTheme.toResponseDto()
    }

    /**
     * Remove a student from a theme.
     * @param themeId the id of the theme to remove the student from
     * @param studentId the id of the student to remove
     * @return the updated theme
     */
    fun removeStudentFromTheme(themeId: UUID, studentId: UUID): ThemeResponseDto {
        log.info("Removing student {} from theme {}", studentId, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        theme.priorityStudents.removeIf { it.id == studentId }

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully removed student from theme")

        return updatedTheme.toResponseDto()
    }

    /**
     * Remove multiple students from a theme.
     * @param themeId the id of the theme to remove the students from
     * @param studentIds the ids of the students to remove
     * @return the updated theme
     */
    fun removeStudentsFromTheme(themeId: UUID, studentIds: List<UUID>): ThemeResponseDto {
        log.info("Removing students {} from theme {}", studentIds, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        theme.priorityStudents.removeIf { it.id in studentIds }

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully removed students from theme")

        return updatedTheme.toResponseDto()
    }

    /**
     * Get the students in a theme.
     * @param themeId the id of the theme to get the students from
     * @param limit the maximum number of students to return
     * @return a list of students in the theme
     */
    fun getThemeStudents(themeId: UUID, limit: Int? = null): List<StudentWithPriorityDto> {
        log.debug("Getting students for theme {} with limit: {}", themeId, limit)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        val studentsWithPriority = theme.priorityStudents.mapIndexed { index, student ->
            StudentWithPriorityDto(
                studentId = student.id!!,
                studentName = student.name,
                priority = index,
                hardSkill = student.hardSkill,
                background = student.background,
                active = student.active
            )
        }

        return if (limit != null) {
            studentsWithPriority.take(limit)
        } else {
            studentsWithPriority
        }
    }

    /**
     * Get the themes for a student.
     * @param studentId the id of the student to get the themes for
     * @return a list of themes for the student
     */
    fun getStudentThemes(studentId: UUID): List<ThemeWithPriorityDto> {
        log.debug("Getting themes for student: {}", studentId)

        val student = studentsRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException(studentId) }

        return student.themes.map { theme ->
            val priority = theme.priorityStudents.indexOfFirst { it.id == studentId }
            ThemeWithPriorityDto(
                themeId = theme.id!!,
                themeName = theme.name,
                priority = if (priority >= 0) priority else -1,
                description = theme.description,
                author = theme.author
            )
        }.filter { it.priority >= 0 } // Only themes where the student is actually present
    }

    /**
     * Update the students for a specialization in a theme.
     * @param themeId the id of the theme to update the students for
     * @param specializationName the name of the specialization to update the students for
     * @param studentIds the ids of the students to update
     * @return the updated theme
     */
    fun updateSpecializationStudents(themeId: UUID, specializationName: String, studentIds: List<UUID>): ThemeResponseDto {
        log.info("Updating students for specialization {} in theme {}", specializationName, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // Validation of existence of specialization
        val exactSpecializationName = theme.getExactSpecializationName(specializationName)
            ?: throw IllegalArgumentException("Specialization '$specializationName' not found in theme")

        // Validation of students
        val students = studentsRepository.findAllById(studentIds)
        val foundIds = students.map { it.id!! }
        val missingIds = studentIds - foundIds.toSet()

        if (missingIds.isNotEmpty()) {
            throw StudentNotFoundException(missingIds.first())
        }

        // Checking students duplicates
        val duplicateStudents = studentIds.groupBy { it }.filter { it.value.size > 1 }
        require (duplicateStudents.isEmpty()) {
            "Duplicate student IDs found: ${duplicateStudents.keys}"
        }

        // Critical: clear cache BEFORE any operations to avoid cached entities
        entityManager.clear()

        // Get existing records for this specialization
        val existingRecords = themeSpecializationStudentRepository
            .findByThemeIdAndSpecializationName(themeId, exactSpecializationName)

        log.info("Found {} existing records for specialization {} in theme {}",
            existingRecords.size, exactSpecializationName, themeId)

        // Delete existing records if any
        if (existingRecords.isNotEmpty()) {
            themeSpecializationStudentRepository.deleteAll(existingRecords)
            entityManager.flush()
            log.info("Deleted {} existing records", existingRecords.size)
        }

        // Clear cache AGAIN after deletion
        entityManager.clear()

        // Creating new records
        students.forEachIndexed { index, student ->
            val specializationStudent = ThemeSpecializationStudent(
                theme = theme,
                specializationName = exactSpecializationName,
                student = student,
                priorityOrder = index
            )
            themeSpecializationStudentRepository.save(specializationStudent)
        }

        // Fixing the insertion
        entityManager.flush()

        // We clear the cache and reload the data
        entityManager.clear()

        log.info("Successfully updated students for specialization {} in theme {}", specializationName, themeId)

        // Reloading theme with new data
        return themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }
            .toResponseDto()
    }

    /**
     * Copy the students from the theme to a specialization.
     * @param themeId the id of the theme to copy the students from
     * @param specializationName the name of the specialization to copy the students to
     * @return the updated theme
     */
    fun copyThemeStudentsToSpecialization(themeId: UUID, specializationName: String): ThemeResponseDto {
        log.info("Copying theme students to specialization {} in theme {}", specializationName, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        require (theme.specializations.contains(specializationName)) {
            "Specialization $specializationName not found in theme"
        }

        // Copy the main list of students to the specialization
        return updateSpecializationStudents(themeId, specializationName,
            theme.priorityStudents.map { it.id!! })
    }

    /**
     * Add a student to a specialization in a theme.
     * @param themeId the id of the theme to add the student to
     * @param specializationName the name of the specialization to add the student to
     * @param studentId the id of the student to add
     * @return the updated theme
     */
    fun addStudentToSpecialization(themeId: UUID, specializationName: String, studentId: UUID): ThemeResponseDto {
        log.info("Adding student {} to specialization {} in theme {}", studentId, specializationName, themeId)

        // Check the existence of theme
        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // Checking the existence of a specialization in a topic
        require(theme.specializations.contains(specializationName)) {
            "Specialization $specializationName not found in theme"
        }

        // Verifying the student's existence
        val student = studentsRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException(studentId) }

        // Checking if a student has already been added
        val existing = themeSpecializationStudentRepository
            .findByThemeIdAndSpecializationNameAndStudentId(themeId, specializationName, studentId)

        if (existing.isPresent) {
            log.info("Student {} is already in specialization {} of theme {}", studentId, specializationName, themeId)
            // Clearing the cache before rebooting
            entityManager.clear()
            // Reload the theme to get the current state
            val updatedTheme = themesRepository.findById(themeId)
                .orElseThrow { ThemeNotFoundException(themeId) }
            return updatedTheme.toResponseDto()
        }

        // Determining the next priority
        val nextPriority = themeSpecializationStudentRepository
            .findMaxPriorityOrderByThemeAndSpecialization(themeId, specializationName)
            ?.plus(1) ?: 0

        // Creating a new entry
        val specializationStudent = ThemeSpecializationStudent(
            theme = theme,
            specializationName = specializationName,
            student = student,
            priorityOrder = nextPriority
        )

        // Saving a new entry
        themeSpecializationStudentRepository.save(specializationStudent)

        // We explicitly commit changes to the database
        entityManager.flush()

        log.info("Successfully added student to specialization")

        // Clearing the cache before rebooting
        entityManager.clear()

        // Reload the theme to get the current state
        val updatedTheme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        return updatedTheme.toResponseDto()
    }

    /**
     * Remove a student from a specialization in a theme.
     * @param themeId the id of the theme to remove the student from
     * @param specializationName the name of the specialization to remove the student from
     * @param studentId the id of the student to remove
     * @return the updated theme
     */
    fun removeStudentFromSpecialization(themeId: UUID, specializationName: String, studentId: UUID): ThemeResponseDto {
        log.info("Removing student {} from specialization {} in theme {}", studentId, specializationName, themeId)

        // Checking the existence of a topic
        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // Checking the existence of a specialization in a topic
        require(theme.specializations.contains(specializationName)) {
            "Specialization $specializationName not found in theme"
        }

        // Direct deletion via the repository
        val deletedCount = themeSpecializationStudentRepository
            .deleteByThemeIdAndSpecializationNameAndStudentId(themeId, specializationName, studentId)

        if (deletedCount == 0) {
            log.warn("Student {} not found in specialization {} of theme {}", studentId, specializationName, themeId)
        } else {
            log.info("Student {} removed from specialization {} in theme {}", studentId, specializationName, themeId)
        }

        // CRITICAL: Clear cache before rebooting
        entityManager.clear()

        // Reloading the theme to get the current state
        val updatedTheme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        return updatedTheme.toResponseDto()
    }

    /**
     * Get the specializations for a student.
     * @param studentId the id of the student to get the specializations for
     * @return a map of specializations to themes
     */
    fun getStudentSpecializations(studentId: UUID): Map<String, Map<UUID, Int>> {
        log.debug("Getting all specializations for student: {}", studentId)

        val student = studentsRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException(studentId) }

        return student.specializationThemes
            .groupBy { it.specializationName }
            .mapValues { (_, specializations) ->
                specializations.associate { specialization ->
                    specialization.theme.id!! to specialization.priorityOrder
                }
            }
    }

    /**
     * Add a specialization to a theme.
     * @param themeId the id of the theme to add the specialization to
     * @param specialization the name of the specialization to add
     * @return the updated theme
     */
    fun addSpecializationToTheme(themeId: UUID, specialization: String): ThemeResponseDto {
        log.info("Adding specialization {} to theme {}", specialization, themeId)

        // Pre-Validation
        require(!specialization.isBlank()) {
            "Specialization name cannot be blank"
        }

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // Checking if the specialization already exists
        require(!theme.hasSpecialization(specialization)) {
            "Specialization '$specialization' already exists in theme"
        }

        theme.addSpecialization(specialization)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully added specialization {} to theme {}", specialization, themeId)

        return updatedTheme.toResponseDto()
    }

    /**
     * Remove a specialization from a theme.
     * @param themeId the id of the theme to remove the specialization from
     * @param specialization the name of the specialization to remove
     * @return the updated theme
     */
    fun removeSpecializationFromTheme(themeId: UUID, specialization: String): ThemeResponseDto {
        log.info("Removing specialization {} from theme {}", specialization, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // Checking existence of specialisation
        require(theme.hasSpecialization(specialization)) {
            "Specialization '$specialization' not found in theme"
        }

        theme.removeSpecialization(specialization)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully removed specialization {} from theme {}", specialization, themeId)

        return updatedTheme.toResponseDto()
    }

    /**
     * Update the specializations for a theme.
     * @param themeId the id of the theme to update the specializations for
     * @param specializations the list of specializations to update
     * @return the updated theme
     */
    fun updateThemeSpecializations(themeId: UUID, specializations: List<String>): ThemeResponseDto {
        log.info("Updating specializations for theme {}: {}", themeId, specializations)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        theme.updateSpecializations(specializations)

        val updatedTheme = themesRepository.save(theme)
        log.info("Successfully updated specializations for theme {}", themeId)

        return updatedTheme.toResponseDto()
    }

    /**
     * Copy students from theme to all specializations
     * @param themeId the id of the theme to copy the students from
     * @return the updated theme
     */
    fun copyThemeStudentsToSpecializations(themeId: UUID): ThemeResponseDto {
        log.info("Copying theme students to all specializations for theme: {}", themeId)

        // Clearing the cache before operations
        entityManager.clear()

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // 1. Remove all students from all specializations in this topic
        theme.specializations.forEach { specialization ->
            themeSpecializationStudentRepository.deleteByThemeIdAndSpecializationName(themeId, specialization)
            log.debug("Deleted all students from specialization: {}", specialization)
        }

        entityManager.flush() // Fixing the deletion
        entityManager.clear() // Clearing the cache

        // 2. Get a fresh theme after clearing the cache
        val freshTheme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // 3. Copying students from the main list to each specialization
        freshTheme.specializations.forEach { specialization ->
            freshTheme.priorityStudents.forEachIndexed { index, student ->
                val specializationStudent = ThemeSpecializationStudent(
                    theme = freshTheme,
                    specializationName = specialization,
                    student = student,
                    priorityOrder = index
                )
                themeSpecializationStudentRepository.save(specializationStudent)
            }
            log.debug("Copied {} students to specialization: {}", freshTheme.priorityStudents.size, specialization)
        }

        entityManager.flush() // Fixing all the inserts
        entityManager.clear() // Clearing the cache

        log.info("Successfully copied theme students to all specializations for theme: {}", themeId)

        // We are returning the updated theme
        return themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }
            .toResponseDto()
    }

    /**
     * Change activity of students in a specialization
     * @param themeId the id of the theme
     * @param specializationName the name of the specialization
     * @param active the new activity status
     * @return the updated theme
     */
    fun changeStudentsActivityInSpecialization(
        themeId: UUID,
        specializationName: String,
        active: Boolean
    ): ThemeResponseDto {
        log.info("Changing activity of students in specialization {} in theme {} to {}",
            specializationName, themeId, active)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        require(theme.hasSpecialization(specializationName)) {
            "Specialization '$specializationName' not found in theme"
        }

        // Getting students in specialisation
        val specializationStudents = themeSpecializationStudentRepository
            .findByThemeIdAndSpecializationName(themeId, specializationName)

        // Change activity of students in specialisation
        specializationStudents.forEach { specializationStudent ->
            specializationStudent.student.active = active
        }

        // Save changes
        studentsRepository.saveAll(specializationStudents.map { it.student })

        log.info("Successfully changed activity of students in specialization {} to {}",
            specializationName, active)

        return themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }
            .toResponseDto()
    }

    /**
     * Add students from theme to all specializations without removing existing ones
     * @param themeId the id of the theme to add the students from
     * @return the updated theme
     */
    fun addThemeStudentsToSpecializations(themeId: UUID): ThemeResponseDto {
        log.info("Adding theme students to all specializations for theme: {}", themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        // For every specialisation in theme
        theme.specializations.forEach { specialization ->
            addThemeStudentsToSpecialization(themeId, specialization)
        }

        log.info("Successfully added theme students to all specializations for theme: {}", themeId)

        return themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }
            .toResponseDto()
    }

    /**
     * Add students from theme to a specific specialization without removing existing ones
     * @param themeId the id of the theme to add the students from
     * @param specializationName the name of the specialization to add the students to
     * @return the updated theme
     */
    fun addThemeStudentsToSpecialization(themeId: UUID, specializationName: String): ThemeResponseDto {
        log.info("Adding theme students to specialization {} in theme: {}", specializationName, themeId)

        val theme = themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }

        require(theme.hasSpecialization(specializationName)) {
            "Specialization '$specializationName' not found in theme"
        }

        // Getting current students in specialization
        val currentSpecializationStudents = themeSpecializationStudentRepository
            .findByThemeIdAndSpecializationName(themeId, specializationName)

        val existingStudentIds = currentSpecializationStudents.map { it.student.id!! }.toSet()

        // Finding next available priorityOrder
        val nextPriorityOrder = currentSpecializationStudents.maxOfOrNull { it.priorityOrder }?.plus(1) ?: 0

        // Adding students from the main theme that are not in the specialization
        val studentsToAdd = theme.priorityStudents
            .filter { student -> student.id !in existingStudentIds }
            .mapIndexed { index, student ->
                ThemeSpecializationStudent(
                    theme = theme,
                    specializationName = specializationName,
                    student = student,
                    priorityOrder = nextPriorityOrder + index
                )
            }

        if (studentsToAdd.isNotEmpty()) {
            themeSpecializationStudentRepository.saveAll(studentsToAdd)
            log.info("Added {} students to specialization {}", studentsToAdd.size, specializationName)
        } else {
            log.info("No new students to add to specialization {}", specializationName)
        }

        // CRITICAL: Clear the Hibernate cache and commit changes
        entityManager.flush() // Fixing all changes in the database
        entityManager.clear() // Clearing the entire cache of the first level

        // Now we are loading a fresh topic with full data
        return themesRepository.findById(themeId)
            .orElseThrow { ThemeNotFoundException(themeId) }
            .toResponseDto()
    }
}