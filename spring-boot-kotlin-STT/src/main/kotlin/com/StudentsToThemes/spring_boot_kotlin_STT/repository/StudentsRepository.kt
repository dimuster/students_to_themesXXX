package com.StudentsToThemes.spring_boot_kotlin_STT.repository

import com.StudentsToThemes.spring_boot_kotlin_STT.entity.StudentEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional
import java.util.UUID

interface StudentsRepository: JpaRepository<StudentEntity, UUID>, JpaSpecificationExecutor<StudentEntity> {
    @EntityGraph(attributePaths = ["themes", "specializationThemes", "specializationThemes.theme"])
    override fun findAll(): List<StudentEntity>

    @EntityGraph(attributePaths = ["themes", "themes.priorityStudents", "specializationThemes", "specializationThemes.theme"])
    override fun findById(id: UUID): Optional<StudentEntity>

    /**
     * Find students by name containing the given string, ignoring case.
     * @param name the name to search for
     * @return a list of students
     */
    fun findByNameContainingIgnoreCase(name: String): List<StudentEntity>

    /**
     * Remove all entries for a student from the theme_student_priority join table.
     * Must be called before deleting a student to avoid FK constraint violations.
     */
    @Modifying
    @Query(
        value = "DELETE FROM theme_student_priority WHERE student_id = :studentId",
        nativeQuery = true
    )
    fun deleteFromThemeStudentPriority(@Param("studentId") studentId: UUID)

    /**
     * Remove entries for multiple students from the theme_student_priority join table.
     */
    @Modifying
    @Query(
        value = "DELETE FROM theme_student_priority WHERE student_id IN :studentIds",
        nativeQuery = true
    )
    fun deleteAllFromThemeStudentPriority(@Param("studentIds") studentIds: List<UUID>)

    /**
     * Remove all entries from the theme_student_priority join table.
     */
    @Modifying
    @Query(value = "DELETE FROM theme_student_priority", nativeQuery = true)
    fun deleteAllFromThemeStudentPriority()

    /**
     * Delete all students that are not active.
     */
    fun deleteAllByActiveFalse()

    /**
     * Find all students that are active.
     * @return a list of students
     */
    fun findAllByActiveTrue(): List<StudentEntity>

    /**
     * Find all students that are not active.
     * @return a list of students
     */
    @EntityGraph(attributePaths = ["themes", "themes.priorityStudents"])
    fun findAllByActiveFalse(): List<StudentEntity>

    /**
     * Find all students by ids with themes eagerly loaded.
     */
    @EntityGraph(attributePaths = ["themes", "themes.priorityStudents"])
    override fun findAllById(ids: Iterable<UUID>): List<StudentEntity>
}