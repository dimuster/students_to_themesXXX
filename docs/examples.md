

## 📊 Примеры использования

- [Сценарий 1: Создание темы и распределение студентов](#сценарий-1-создание-темы-и-распределение-студентов)
- [Сценарий 2: Массовое управление студентами](#сценарий-2-массовое-управление-студентами)
- [Сценарий 3: Поиск и фильтрация](#сценарий-3-поиск-и-фильтрация)





### <a id="сценарий-1-создание-темы-и-распределение-студентов">Сценарий 1: Создание темы и распределение студентов</a>

```bash
# 1. Создаем студентов
curl -X POST "http://localhost:8080/students" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Алексей ML разработчик",
    "hardSkill": "Machine Learning",
    "background": "Опыт Python, TensorFlow, PyTorch 3 года",
    "interests": "Глубокое обучение, компьютерное зрение",
    "timeInWeek": "25 часов"
  }'

curl -X POST "http://localhost:8080/students" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Мария Data Scientist", 
    "hardSkill": "Data Science",
    "background": "Pandas, NumPy, SQL, статистика 2 года",
    "interests": "Анализ данных, визуализация",
    "timeInWeek": "20 часов"
  }'

# 2. Создаем тему
curl -X POST "http://localhost:8080/themes" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Разработка системы предсказания цен недвижимости",
    "description": "Создание ML модели для предсказания цен на недвижимость на основе исторических данных и характеристик объектов",
    "author": "Проф. Смирнов",
    "specializations": ["Machine Learning", "Data Science", "Backend"],
    "priorityStudents": []
  }'

# 3. Добавляем студентов к теме
curl -X POST "http://localhost:8080/themes/THEME_ID/students" \
  -H "Content-Type: application/json" \
  -d '["STUDENT_1_ID", "STUDENT_2_ID"]'

# 4. Копируем студентов в специализации
curl -X POST "http://localhost:8080/themes/THEME_ID/copy-to-specializations"

# 5. Применяем ML сортировку для Machine Learning специализации
curl -X POST "http://localhost:8080/themes/THEME_ID/specializations/Machine%20Learning/ml-sort"

# 6. Проверяем результат
curl "http://localhost:8080/themes/THEME_ID/specializations/Machine%20Learning/students?useMLSorting=true"
```

### <a id="сценарий-2-массовое-управление-студентами">Сценарий 2: Массовое управление студентами</a>

```bash
# 1. Создаем несколько студентов
curl -X POST "http://localhost:8080/students/by-ids" \
  -H "Content-Type: application/json" \
  -d '[
    {
      "name": "Дмитрий Backend",
      "hardSkill": "Backend Development",
      "background": "Java, Spring Boot, PostgreSQL 4 года",
      "interests": "Микросервисы, облачные технологии",
      "timeInWeek": "30 часов"
    },
    {
      "name": "Ольга Frontend",
      "hardSkill": "Frontend Development", 
      "background": "React, TypeScript, CSS 3 года",
      "interests": "UI/UX, мобильная разработка",
      "timeInWeek": "25 часов"
    }
  ]'

# 2. Деактивируем группу студентов
curl -X PUT "http://localhost:8080/students/change-activities" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": ["STUDENT_1_ID", "STUDENT_2_ID"],
    "active": false
  }'

# 3. Удаляем неактивных студентов
curl -X DELETE "http://localhost:8080/students/unactive"
```

### <a id="сценарий-3-поиск-и-фильтрация">Сценарий 3: Поиск и фильтрация</a>

```bash
# Поиск студентов по навыкам
curl "http://localhost:8080/students?hardSkill=Machine%20Learning&background=Python"

# Поиск тем по автору и описанию
curl "http://localhost:8080/themes?author=Петров&description=анализ"

# Получение активных студентов с ограничением
curl "http://localhost:8080/students/active"

# Получение студентов специализации с ML сортировкой
curl "http://localhost:8080/themes/THEME_ID/specializations/Data%20Science/students?useMLSorting=true&onlyActive=true"
```
