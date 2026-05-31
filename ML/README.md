# 📚 ML Модуль: Student-Themes Matching Model

🎯 ML модель для автоматического подбора студентов к учебным/научным темам на основе:

- Семантического сходства текстов (Sentence Transformers)

- Совпадения специализаций (машинное обучение → Machine Learning)

- Совпадения навыков (Python, Docker, React и т.д.)

- Доступности времени (часов в неделю)

## 1. **Установка**

Шаг 1: Клонируйте проект

Скопируйте репозиторий

`git clone https://github.com/Netnol/Students_To_Themes.git`

Перейдите в папку с ML моделью

`cd Students_To_Themes/ML`

Шаг 2: Установите зависимости

`pip install -r requirements.txt`

## 2. Форматы данных

###  Студенты 

**Поля:**
| Поле | Тип | Описание | Пример |
|------|-----|----------|--------|
| `id` | string | Уникальный идентификатор | `"student_001"` |
| `name` | string | Имя студента | `"Иван Иванов"` |
| `hardSkill` | string | Основная специализация | `"Machine Learning"` |
| `background` | string | Опыт и навыки (текст) | `"Python, TensorFlow, SQL"` |
| `interests` | string | Научные интересы | `"нейросети, компьютерное зрение"` |
| `timeInWeek` | string | Доступное время в неделю | `"15"` |


### 📋 Темы от преподавателей

Поля:

| Поле | Тип | Описание | Пример |
|------|-----|----------|--------|
| **`id`** | string | Уникальный ID темы | `"theme_2024_ml_001"` |
| **`name`** | string | Название темы | `"Анализ тональности отзывов"` |
| **`description`** | string | Подробное описание темы | `"Разработка NLP модели для анализа эмоций в текстах..."` |
| **`author`** | string | Автор/руководитель темы | `"Проф. Сидоров А.П., кафедра ИИ"` |
| **`specializations`** | array | Подходящие специализации | `["NLP", "Machine Learning"]` |


## 3. Как менять модель

### 3.1 Сменить модель эмбеддингов

В файле main.py измените 

https://github.com/Netnol/Students_To_Themes/blob/4ecdebb928e9294e59a5448ce10a16c75a51cad8/ML/main.py#L43

К примеру, если важна скорость и ограничена память:

`def __init__(self, model_name='sentence-transformers/all-MiniLM-L6-v2'):`

Или точность:

`def __init__(self, model_name='sentence-transformers/paraphrase-multilingual-mpnet-base-v2'):`


### 3.2 Добавить новую специализацию
В файле main.py найдите 

https://github.com/Netnol/Students_To_Themes/blob/941d96b820402e4033d994bb1bc0eace472d0c55/ML/main.py#L46-L50

Добавьте новую запись, к примеру нейробиология:

`['Нейробиология'] = [
    'Нейробиология',
    'нейробиология', 
    'neuroscience',
    'brain research'
]
`

И добавьте связи между специализациями:

https://github.com/Netnol/Students_To_Themes/blob/baa632df5ce2437f0efd8c21ceff11abe426ef5c/ML/main.py#L188-L191

`['Нейробиология'] = ['Machine Learning', 'Data Science']`

### 3.3 Добавить новый навык
В файле main.py найдите словарь skill_keywords 

https://github.com/Netnol/Students_To_Themes/blob/3c510ac5cc4a6d2e44d5afec4df5afbd7d4854f6/ML/main.py#L118-L121

Добавьте новую запись (в нашем случае к примеру выше):

`['bioinformatics'] = [
    'биоинформатика',
    'bioinformatics',
    'геномика',
    'genomics',
    'ДНК',
    'DNA'
]`
### 3.4 Изменить веса оценки
В файле main.py найдите метод 

https://github.com/Netnol/Students_To_Themes/blob/7e9a2d17ecc6b9b73a86179a801f03f2bfb7ad1d/ML/main.py#L218-L220

Измените веса

Можно уделить больше внимания семантике, например:

`weights = {'semantic': 0.5, 'specialization': 0.25, 'skills': 0.15, 'hours': 0.1}`

## 4. Тестирование

После того, как вы обновили main.py, вы можете протестировать, к примеру, нормализацию или тест скиллов

Также обновите тесты, чтобы посмотреть поведение системы после изменений (добавьте то, что вы ожидаете увидеть после работы алгоритма)

Для запуска тестов

`python -m pytest tests/`

Или запустите конкретный тест

`python tests/test_normalization.py`

`python tests/test_skills.py`



























