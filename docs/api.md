

## <a id="полное-описание-api">📡 Полное описание API</a>

- [🎓 Управление студентами (`/students`)](#управление-студентами-students)
  - [🔍 Поиск и получение студентов](#поиск-и-получение-студентов)
    - [1. Получить всех студентов с фильтрацией](#1-получить-всех-студентов-с-фильтрацией)
    - [2. Получить студента по ID](#2-получить-студента-по-id)
    - [3. Получить студентов по списку ID](#3-получить-студентов-по-списку-id)
    - [4. Получить активных студентов](#4-получить-активных-студентов)
    - [5. Получить неактивных студентов](#5-получить-неактивных-студентов)
  - [➕ Создание студентов](#создание-студентов)
    - [6. Создать нового студента](#6-создать-нового-студента)
    - [7. Создать нескольких студентов](#7-создать-несколько-студентов)
  - [✏️ Обновление студентов](#обновление-студентов)
    - [8. Обновить данные студента](#8-обновить-данные-студента)
    - [9. Изменить активность студента](#9-изменить-активность-студента)
    - [10. Изменить активность нескольких студентов](#10-изменить-активность-нескольких-студентов)
  - [🗑️ Удаление студентов](#удаление-студентов)
    - [11. Удалить студента по ID](#11-удалить-студента-по-id)
    - [12. Удалить нескольких студентов](#12-удалить-несколько-студентов)
    - [13. Удалить всех студентов](#13-удалить-всех-студентов)
    - [14. Удалить неактивных студентов](#14-удалить-неактивных-студентов)

- [🎯 Управление темами (`/themes`)](#управление-темами-themes)
  - [🔍 Поиск и получение тем](#поиск-и-получение-тем)
    - [15. Получить все темы с фильтрацией](#15-получить-все-темы-с-фильтрацией)
    - [16. Получить тему по ID](#16-получить-тему-по-id)
  - [➕ Создание тем](#создание-тем)
    - [17. Создать новую тему](#17-создать-новую-тему)
  - [✏️ Обновление тем](#обновление-тем)
    - [18. Обновить данные темы](#18-обновить-данные-темы)
    - [19. Обновить приоритеты студентов в теме](#19-обновить-приоритеты-студентов-в-теме)
  - [👥 Управление студентами в темах](#управление-студентами-в-темах)
    - [20. Добавить студента к теме](#20-добавить-студента-к-теме)
    - [21. Добавить нескольких студентов к теме](#21-добавить-несколько-студентов-к-теме)
    - [22. Удалить студента из темы](#22-удалить-студента-из-темы)
    - [23. Удалить нескольких студентов из темы](#23-удалить-несколько-студентов-из-темы)
    - [24. Изменить активность студентов в теме](#24-изменить-активность-студентов-в-теме)
  - [📊 Получение данных о студентах в темах](#получение-данных-о-студентах-в-темах)
    - [25. Получить студентов темы](#25-получить-студентов-темы)
    - [26. Получить темы студента](#26-получить-темы-студента)
  - [🗑️ Удаление тем](#удаление-тем)
    - [27. Удалить тему](#27-удалить-тему)
    - [28. Удалить несколько тем](#28-удалить-несколько-тем)

- [🔧 Управление специализациями](#управление-специализациями)
  - [➕ Создание и управление специализациями](#создание-и-управление-специализациями)
    - [29. Добавить специализацию к теме](#29-добавить-специализацию-к-теме)
    - [30. Удалить специализацию из темы](#30-удалить-специализацию-из-темы)
    - [31. Обновить список специализаций темы](#31-обновить-список-специализаций-темы)
  - [👥 Управление студентами в специализациях](#управление-студентами-в-специализациях)
    - [32. Обновить студентов в специализации](#32-обновить-студентов-в-специализации)
    - [33. Добавить студента в специализацию](#33-добавить-студента-в-специализацию)
    - [34. Удалить студента из специализации](#34-удалить-студента-из-специализации)
    - [35. Получить студентов специализации](#35-получить-студентов-специализации)
  - [📋 Получение данных о специализациях](#получение-данных-о-специализациях)
    - [36. Получить специализации студента](#36-получить-специализации-студента)
  - [🔄 Копирование данных между темами и специализациями](#копирование-данных-между-темами-и-специализациями)
    - [37. Скопировать студентов темы в специализацию (с заменой)](#37-скопировать-студентов-темы-в-специализацию-с-заменой)
    - [38. Скопировать студентов темы во все специализации (с заменой)](#38-скопировать-студентов-темы-во-все-специализации-с-заменой)
    - [39. Добавить студентов темы в специализацию (без удаления существующих)](#39-добавить-студентов-темы-в-специализацию-без-удаления-существующих)
    - [40. Добавить студентов темы во все специализации (без удаления существующих)](#40-добавить-студентов-темы-во-все-специализации-без-удаления-существующих)
  - [⚡ Управление активностью в специализациях](#управление-активностью-в-специализациях)
    - [41. Изменить активность студентов в специализации](#41-изменить-активность-студентов-в-специализации)

- [🤖 ML Функциональность](#ml-функциональность)
  - [42. Применить ML сортировку к специализации](#42-применить-ml-сортировку-к-специализации)
  - [43. Применить ML сортировку ко всем специализациям темы](#43-применить-ml-сортировку-ко-всем-специализациям-темы)
  - [44. Проверить статус ML сервиса](#44-проверить-статус-ml-сервиса)







### <a id="управление-студентами-students">🎓 Управление студентами (`/students`)</a>

#### <a id="поиск-и-получение-студентов">🔍 Поиск и получение студентов</a>

**<a id="1-получить-всех-студентов-с-фильтрацией">1. Получить всех студентов с фильтрацией</a>**
```http
GET /students?name=Иван&hardSkill=ML&background=Python&interests=NLP&timeInWeek=20
```
**Параметры:**
- `name` (опционально) - поиск по имени (частичное совпадение)
- `hardSkill` (опционально) - поиск по основному навыку
- `background` (опционально) - поиск по опыту работы
- `interests` (опционально) - поиск по интересам
- `timeInWeek` (опционально) - поиск по доступному времени

**Пример запроса:**
```bash
curl "http://localhost:8080/students?name=Иван&hardSkill=Machine Learning" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Иван Петров",
    "hardSkill": "Machine Learning",
    "background": "Опыт работы с Python, ML, глубокое обучение 2 года",
    "interests": "NLP, компьютерное зрение, анализ данных",
    "timeInWeek": "20 часов",
    "themePriorities": {
      "660e8400-e29b-41d4-a716-446655440001": 0,
      "660e8400-e29b-41d4-a716-446655440002": 1
    },
    "specializationPriorities": {
      "Machine Learning": {
        "660e8400-e29b-41d4-a716-446655440001": 0
      }
    },
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-15T10:30:00Z"
  }
]
```

**<a id="2-получить-студента-по-id">2. Получить студента по ID</a>**
```http
GET /students/{id}
```
**Пример:**
```bash
curl "http://localhost:8080/students/550e8400-e29b-41d4-a716-446655440000" \
  -H "Content-Type: application/json"
```

**<a id="3-получить-студентов-по-списку-id">3. Получить студентов по списку ID</a>**
```http
GET /students/by-ids
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "550e8400-e29b-41d4-a716-446655440000",
  "550e8400-e29b-41d4-a716-446655440001",
  "550e8400-e29b-41d4-a716-446655440002"
]
```
**Пример:**
```bash
curl -X GET "http://localhost:8080/students/by-ids" \
  -H "Content-Type: application/json" \
  -d '["550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440001"]'
```

**<a id="4-получить-активных-студентов">4. Получить активных студентов</a>**
```http
GET /students/active
```
**Пример:**
```bash
curl "http://localhost:8080/students/active" \
  -H "Content-Type: application/json"
```

**<a id="5-получить-неактивных-студентов">5. Получить неактивных студентов</a>**
```http
GET /students/unactive
```
**Пример:**
```bash
curl "http://localhost:8080/students/unactive" \
  -H "Content-Type: application/json"
```

#### ➕ <a id="создание-студентов">Создание студентов</a>

**<a id="6-создать-нового-студента">6. Создать нового студента</a>**
```http
POST /students
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "name": "Анна Сидорова",
  "hardSkill": "Data Science",
  "background": "Стажировка в Яндекс, опыт Pandas, NumPy, SQL 1 год",
  "interests": "Анализ данных, визуализация, ML",
  "timeInWeek": "15 часов"
}
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/students" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Анна Сидорова",
    "hardSkill": "Data Science",
    "background": "Стажировка в Яндекс, опыт Pandas, NumPy, SQL 1 год",
    "interests": "Анализ данных, визуализация, ML",
    "timeInWeek": "15 часов"
  }'
```

**<a id="7-создать-несколько-студентов">7. Создать нескольких студентов</a>**
```http
POST /students/by-ids
Content-Type: application/json
```
**Тело запроса:**
```json
[
  {
    "name": "Петр Иванов",
    "hardSkill": "Backend Development",
    "background": "Java, Spring Boot, PostgreSQL 3 года",
    "interests": "Микросервисы, облачные технологии",
    "timeInWeek": "25 часов"
  },
  {
    "name": "Мария Козлова", 
    "hardSkill": "Frontend Development",
    "background": "React, TypeScript, CSS 2 года",
    "interests": "UI/UX, мобильная разработка",
    "timeInWeek": "20 часов"
  }
]
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/students/by-ids" \
  -H "Content-Type: application/json" \
  -d '[
    {
      "name": "Петр Иванов",
      "hardSkill": "Backend Development",
      "background": "Java, Spring Boot, PostgreSQL 3 года",
      "interests": "Микросервисы, облачные технологии",
      "timeInWeek": "25 часов"
    }
  ]'
```

#### <a id="обновление-студентов">✏️ Обновление студентов</a>

**<a id="8-обновить-данные-студента">8. Обновить данные студента</a>**
```http
PUT /students/{id}
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "name": "Анна Сидорова (обновлено)",
  "hardSkill": "Senior Data Science",
  "background": "Стажировка в Яндекс, опыт Pandas, NumPy, SQL 2 года",
  "interests": "Анализ данных, визуализация, ML, Big Data",
  "timeInWeek": "20 часов"
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/students/550e8400-e29b-41d4-a716-446655440000" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Анна Сидорова (обновлено)",
    "hardSkill": "Senior Data Science", 
    "background": "Стажировка в Яндекс, опыт Pandas, NumPy, SQL 2 года",
    "interests": "Анализ данных, визуализация, ML, Big Data",
    "timeInWeek": "20 часов"
  }'
```

**<a id="9-изменить-активность-студента">9. Изменить активность студента</a>**
```http
PUT /students/{id}/change-activity
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "active": false
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/students/550e8400-e29b-41d4-a716-446655440000/change-activity" \
  -H "Content-Type: application/json" \
  -d '{"active": false}'
```

**<a id="10-изменить-активность-нескольких-студентов">10. Изменить активность нескольких студентов</a>**
```http
PUT /students/change-activities
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "ids": [
    "550e8400-e29b-41d4-a716-446655440000",
    "550e8400-e29b-41d4-a716-446655440001"
  ],
  "active": true
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/students/change-activities" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": ["550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440001"],
    "active": true
  }'
```

#### <a id="удаление-студентов">🗑️ Удаление студентов</a>

**<a id="11-удалить-студента-по-id">11. Удалить студента по ID</a>**
```http
DELETE /students/{id}
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/students/550e8400-e29b-41d4-a716-446655440000"
```

**<a id="12-удалить-несколько-студентов">12. Удалить нескольких студентов</a>**
```http
DELETE /students/by-ids
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "550e8400-e29b-41d4-a716-446655440000",
  "550e8400-e29b-41d4-a716-446655440001"
]
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/students/by-ids" \
  -H "Content-Type: application/json" \
  -d '["550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440001"]'
```

**<a id="13-удалить-всех-студентов">13. Удалить всех студентов</a>**
```http
DELETE /students/all
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/students/all"
```

**<a id="14-удалить-неактивных-студентов">14. Удалить неактивных студентов</a>**
```http
DELETE /students/unactive
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/students/unactive"
```

### <a id="управление-темами-themes">🎯 Управление темами (/themes)</a>

#### <a id="поиск-и-получение-тем">🔍 Поиск и получение тем</a>

**<a id="15-получить-все-темы-с-фильтрацией">15. Получить все темы с фильтрацией</a>**
```http
GET /themes?name=ML&description=анализ&author=Петров
```
**Параметры:**
- `name` (опционально) - поиск по названию темы
- `description` (опционально) - поиск по описанию
- `author` (опционально) - поиск по автору

**Пример:**
```bash
curl "http://localhost:8080/themes?name=Machine Learning&author=Петров" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
[
    {
        "id": "cc874cda-98b6-436f-9e04-d47ff985e33e",
        "name": "Web Development",
        "description": "Web development technologies",
        "author": "John Doe",
        "specializations": [
            "Frontend",
            "Backend",
            "DevOps",
            "Mobile Development"
        ],
        "priorityStudents": [],
        "studentPriorities": {},
        "specializationStudents": {
            "Frontend": [
                {
                    "studentId": "34cc685b-ebac-45d8-ba82-cba01155db2a",
                    "studentName": "Alice Smith",
                    "priority": 0,
                    "hardSkill": "React",
                    "background": "Frontend developer",
                    "active": false
                },
                {
                    "studentId": "0524abd4-bf5b-4e93-982c-dc01e40679d3",
                    "studentName": "John Smith",
                    "priority": 1,
                    "hardSkill": "React",
                    "background": "Frontend developer",
                    "active": false
                }
            ]
        },
        "createdAt": "2025-11-23T17:47:03.076274Z",
        "updatedAt": "2025-11-23T17:47:03.076274Z"
    },
    {
        "id": "d2750d87-e56f-44b0-8789-54a25cf5b653",
        "name": "ML Project Test",
        "description": "Тестовый проект машинного обучения с NLP",
        "author": "Test Author",
        "specializations": [
            "Machine Learning",
            "Data Science"
        ],
        "priorityStudents": [],
        "studentPriorities": {},
        "specializationStudents": {},
        "createdAt": "2025-11-27T21:12:49.121740Z",
        "updatedAt": "2025-11-27T21:12:49.121740Z"
    }
]
```

**<a id="16-получить-тему-по-id">16. Получить тему по ID</a>**
```http
GET /themes/{id}
```
**Пример:**
```bash
curl "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000" \
  -H "Content-Type: application/json"
```

#### <a id="создание-тем">➕ Создание тем</a>

**<a id="17-создать-новую-тему">17. Создать новую тему</a>**
```http
POST /themes
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "name": "Разработка системы рекомендаций",
  "description": "Создание системы рекомендаций на основе коллаборативной фильтрации и контентного анализа",
  "author": "Проф. Иванова",
  "specializations": ["Machine Learning", "Data Science", "Backend"],
  "priorityStudents": [
    "550e8400-e29b-41d4-a716-446655440000",
    "550e8400-e29b-41d4-a716-446655440001"
  ]
}
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Разработка системы рекомендаций",
    "description": "Создание системы рекомендаций на основе коллаборативной фильтрации и контентного анализа",
    "author": "Проф. Иванова", 
    "specializations": ["Machine Learning", "Data Science", "Backend"],
    "priorityStudents": ["550e8400-e29b-41d4-a716-446655440000"]
  }'
```

#### <a id="обновление-тем">✏️ Обновление тем</a>

**<a id="18-обновить-данные-темы">18. Обновить данные темы</a>**
```http
PUT /themes/{themeId}
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "name": "Разработка системы рекомендаций (обновлено)",
  "description": "Создание системы рекомендаций с использованием ML алгоритмов и веб-интерфейса",
  "author": "Проф. Иванова",
  "specializations": ["Machine Learning", "Data Science", "Backend", "Frontend"],
  "priorityStudents": [
    "550e8400-e29b-41d4-a716-446655440000",
    "550e8400-e29b-41d4-a716-446655440002"
  ]
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Разработка системы рекомендаций (обновлено)",
    "description": "Создание системы рекомендаций с использованием ML алгоритмов и веб-интерфейса",
    "author": "Проф. Иванова",
    "specializations": ["Machine Learning", "Data Science", "Backend", "Frontend"],
    "priorityStudents": ["550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440002"]
  }'
```

**<a id="19-обновить-приоритеты-студентов-в-теме">19. Обновить приоритеты студентов в теме</a>**
```http
PUT /themes/{themeId}/priority
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "studentIds": [
    "550e8400-e29b-41d4-a716-446655440002",
    "550e8400-e29b-41d4-a716-446655440000", 
    "550e8400-e29b-41d4-a716-446655440001"
  ]
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/priority" \
  -H "Content-Type: application/json" \
  -d '{
    "studentIds": [
      "550e8400-e29b-41d4-a716-446655440002",
      "550e8400-e29b-41d4-a716-446655440000",
      "550e8400-e29b-41d4-a716-446655440001"
    ]
  }'
```

#### <a id="управление-студентами-в-темах">👥 Управление студентами в темах</a>

**<a id="20-добавить-студента-к-теме">20. Добавить студента к теме</a>**
```http
POST /themes/{themeId}/students/{studentId}
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students/550e8400-e29b-41d4-a716-446655440003"
```

**<a id="21-добавить-несколько-студентов-к-теме">21. Добавить нескольких студентов к теме</a>**
```http
POST /themes/{themeId}/students
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "550e8400-e29b-41d4-a716-446655440003",
  "550e8400-e29b-41d4-a716-446655440004",
  "550e8400-e29b-41d4-a716-446655440005"
]
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students" \
  -H "Content-Type: application/json" \
  -d '["550e8400-e29b-41d4-a716-446655440003", "550e8400-e29b-41d4-a716-446655440004"]'
```

**<a id="22-удалить-студента-из-темы">22. Удалить студента из темы</a>**
```http
DELETE /themes/{themeId}/students/{studentId}
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students/550e8400-e29b-41d4-a716-446655440003"
```

**<a id="23-удалить-несколько-студентов-из-темы">23. Удалить нескольких студентов из темы</a>**
```http
DELETE /themes/{themeId}/students
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "550e8400-e29b-41d4-a716-446655440003",
  "550e8400-e29b-41d4-a716-446655440004"
]
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students" \
  -H "Content-Type: application/json" \
  -d '["550e8400-e29b-41d4-a716-446655440003", "550e8400-e29b-41d4-a716-446655440004"]'
```

**<a id="24-изменить-активность-студентов-в-теме">24. Изменить активность студентов в теме</a>**
```http
PUT /themes/{themeId}/students/active
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "active": false
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students/active" \
  -H "Content-Type: application/json" \
  -d '{"active": false}'
```

#### <a id="получение-данных-о-студентах-в-темах">📊 Получение данных о студентах в темах</a>

**<a id="25-получить-студентов-темы">25. Получить студентов темы</a>**
```http
GET /themes/{themeId}/students?limit=10
```
**Параметры:**
- `limit` (опционально) - ограничение количества возвращаемых студентов

**Пример:**
```bash
curl "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/students?limit=5" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
[
  {
    "studentId": "550e8400-e29b-41d4-a716-446655440000",
    "studentName": "Иван Петров",
    "priority": 0,
    "hardSkill": "Machine Learning",
    "background": "Опыт работы с Python, ML, глубокое обучение 2 года",
    "active": true
  },
  {
    "studentId": "550e8400-e29b-41d4-a716-446655440001",
    "studentName": "Анна Сидорова",
    "priority": 1,
    "hardSkill": "Data Science",
    "background": "Стажировка в Яндекс, опыт Pandas, NumPy, SQL 1 год",
    "active": true
  }
]
```

**<a id="26-получить-темы-студента">26. Получить темы студента</a>**
```http
GET /themes/students/{studentId}/themes
```
**Пример:**
```bash
curl "http://localhost:8080/themes/students/550e8400-e29b-41d4-a716-446655440000/themes" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
[
  {
    "themeId": "660e8400-e29b-41d4-a716-446655440000",
    "themeName": "Разработка ML модели для классификации текстов",
    "priority": 0,
    "description": "Создание и обучение модели для автоматической классификации customer reviews",
    "author": "Др. Петров"
  },
  {
    "themeId": "660e8400-e29b-41d4-a716-446655440001", 
    "themeName": "Разработка системы рекомендаций",
    "priority": 1,
    "description": "Создание системы рекомендаций на основе коллаборативной фильтрации",
    "author": "Проф. Иванова"
  }
]
```

#### <a id="удаление-тем">🗑️ Удаление тем</a>

**<a id="27-удалить-тему">27. Удалить тему</a>**
```http
DELETE /themes/{themeId}
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000"
```

**<a id="28-удалить-несколько-тем">28. Удалить несколько тем</a>**
```http
DELETE /themes/
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "131efa81-d12b-43da-b21d-9ff308df3c00",
  "660e8400-e29b-41d4-a716-446655440000"
]
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes" \
  -H "Content-Type: application/json" \
  -d '["131efa81-d12b-43da-b21d-9ff308df3c00", "660e8400-e29b-41d4-a716-446655440000"]'
```

### <a id="управление-специализациями">🔧 Управление специализациями</a>

#### <a id="создание-и-управление-специализациями">➕ Создание и управление специализациями</a>

**<a id="29-добавить-специализацию-к-теме">29. Добавить специализацию к теме</a>**
```http
POST /themes/{themeId}/specializations
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "name": "Computer Vision"
}
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations" \
  -H "Content-Type: application/json" \
  -d '{"name": "Computer Vision"}'
```

**<a id="30-удалить-специализацию-из-темы">30. Удалить специализацию из темы</a>**
```http
DELETE /themes/{themeId}/specializations/{specializationName}
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Computer Vision"
```

**<a id="31-обновить-список-специализаций-темы">31. Обновить список специализаций темы</a>**
```http
PUT /themes/{themeId}/specializations
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "Machine Learning",
  "Data Science", 
  "Computer Vision",
  "NLP"
]
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations" \
  -H "Content-Type: application/json" \
  -d '["Machine Learning", "Data Science", "Computer Vision", "NLP"]'
```

#### <a id="управление-студентами-в-специализациях">👥 Управление студентами в специализациях</a>

**<a id="32-обновить-студентов-в-специализации">32. Обновить студентов в специализации</a>**
```http
PUT /themes/{themeId}/specializations/{specializationName}/students
Content-Type: application/json
```
**Тело запроса:**
```json
[
  "550e8400-e29b-41d4-a716-446655440000",
  "550e8400-e29b-41d4-a716-446655440001",
  "550e8400-e29b-41d4-a716-446655440002"
]
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/students" \
  -H "Content-Type: application/json" \
  -d '["550e8400-e29b-41d4-a716-446655440000", "550e8400-e29b-41d4-a716-446655440001", "550e8400-e29b-41d4-a716-446655440002"]'
```

**<a id="33-добавить-студента-в-специализацию">33. Добавить студента в специализацию</a>**
```http
POST /themes/{themeId}/specializations/{specializationName}/students/{studentId}
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/students/550e8400-e29b-41d4-a716-446655440003"
```

**<a id="34-удалить-студента-из-специализации">34. Удалить студента из специализации</a>**
```http
DELETE /themes/{themeId}/specializations/{specializationName}/students/{studentId}
```
**Пример:**
```bash
curl -X DELETE "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/students/550e8400-e29b-41d4-a716-446655440003"
```

**<a id="35-получить-студентов-специализации">35. Получить студентов специализации</a>**
```http
GET /themes/{themeId}/specializations/{specializationName}/students?limit=5&useMLSorting=true&onlyActive=true
```
**Параметры:**
- `limit` (опционально) - ограничение количества
- `useMLSorting` (опционально, по умолчанию false) - использовать ML сортировку
- `onlyActive` (опционально, по умолчанию false) - только активные студенты

**Пример:**
```bash
curl "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/students?limit=10&useMLSorting=true&onlyActive=true" \
  -H "Content-Type: application/json"
```

#### <a id="получение-данных-о-специализациях">📋 Получение данных о специализациях</a>

**<a id="36-получить-специализации-студента">36. Получить специализации студента</a>**
```http
GET /themes/students/{studentId}/specializations
```
**Пример:**
```bash
curl "http://localhost:8080/themes/students/550e8400-e29b-41d4-a716-446655440000/specializations" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
{
  "Machine Learning": {
    "660e8400-e29b-41d4-a716-446655440000": 0,
    "660e8400-e29b-41d4-a716-446655440001": 1
  },
  "Data Science": {
    "660e8400-e29b-41d4-a716-446655440000": 2
  }
}
```

#### <a id="копирование-данных-между-темами-и-специализациями">🔄 Копирование данных между темами и специализациями</a>

**<a id="37-скопировать-студентов-темы-в-специализацию-с-заменой">37. Скопировать студентов темы в специализацию (с заменой)</a>**
```http
PUT /themes/{themeId}/specializations/{specializationName}/copy-from-theme
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/copy-from-theme"
```

**<a id="38-скопировать-студентов-темы-во-все-специализации-с-заменой">38. Скопировать студентов темы во все специализации (с заменой)</a>**
```http
PUT /themes/{themeId}/copy-to-specializations
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/copy-to-specializations"
```

**<a id="39-добавить-студентов-темы-в-специализацию-без-удаления-существующих">39. Добавить студентов темы в специализацию (без удаления существующих)</a>**
```http
POST /themes/{themeId}/specializations/{specializationName}/add-from-theme
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/add-from-theme"
```

**<a id="40-добавить-студентов-темы-во-все-специализации-без-удаления-существующих">40. Добавить студентов темы во все специализации (без удаления существующих)</a>**
```http
POST /themes/{themeId}/add-to-specializations
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/add-to-specializations"
```

#### <a id="управление-активностью-в-специализациях">⚡ Управление активностью в специализациях</a>

**<a id="41-изменить-активность-студентов-в-специализации">41. Изменить активность студентов в специализации</a>**
```http
PUT /themes/{themeId}/specializations/{specializationName}/activity
Content-Type: application/json
```
**Тело запроса:**
```json
{
  "active": false
}
```
**Пример:**
```bash
curl -X PUT "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/activity" \
  -H "Content-Type: application/json" \
  -d '{"active": false}'
```

### <a id="ml-функциональность">🤖 ML Функциональность</a>

**<a id="42-применить-ml-сортировку-к-специализации">41. Применить ML сортировку к специализации</a>**
```http
POST /themes/{themeId}/specializations/{specializationName}/ml-sort
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/specializations/Machine Learning/ml-sort"
```

**<a id="43-применить-ml-сортировку-ко-всем-специализациям-темы">43. Применить ML сортировку ко всем специализациям темы</a>**
```http
POST /themes/{themeId}/ml-sort-all
```
**Пример:**
```bash
curl -X POST "http://localhost:8080/themes/660e8400-e29b-41d4-a716-446655440000/ml-sort-all"
```

**<a id="44-проверить-статус-ml-сервиса">44. Проверить статус ML сервиса</a>**
```http
GET /themes/ml-health
```
**Пример:**
```bash
curl "http://localhost:8080/themes/ml-health" \
  -H "Content-Type: application/json"
```

**Пример ответа:**
```json
{
  "status": "healthy",
  "service": "ML Matching Service"
}
```