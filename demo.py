"""
Полный демонстрационный сценарий:
  1. Импорт студентов из CSV
  2. Создание темы
  3. Привязка студентов к теме
  4. Копирование в специализации
  5. ML-сортировка
  6. Просмотр результата

Запуск: python demo.py
"""

import csv
import json
import sys
from pathlib import Path
from _common import post, put, get, log, ok, err, BASE_URL

CSV_FILE    = "students.csv"
RESULT_FILE = "demo_result.json"
THEME = {
    "name": "Разработка системы предсказания цен недвижимости",
    "description": (
        "Создание ML модели для предсказания цен на недвижимость "
        "на основе исторических данных и характеристик объектов. "
        "Применение методов регрессии и нейронных сетей."
    ),
    "author": "Проф. Смирнов",
    "specializations": ["Machine Learning", "Data Science", "Backend"],
    "priorityStudents": [],
}


def import_students(csv_file: str) -> list[dict]:
    created = []
    with open(csv_file, encoding="utf-8") as f:
        for row in csv.DictReader(f):
            if not row.get("name", "").strip():
                continue
            payload = {
                "name":       row["name"].strip(),
                "hardSkill":  row["hardSkill"].strip(),
                "background": row["background"].strip(),
                "interests":  row["interests"].strip(),
            }
            if row.get("timeInWeek", "").strip():
                payload["timeInWeek"] = row["timeInWeek"].strip()

            r = post("/students", payload)
            if r.status_code == 200:
                student = r.json()
                created.append(student)
                ok(f"{student['name']}  id={student['id']}")
            else:
                err(f"{row['name']}: {r.status_code} {r.text}")
    return created


def create_theme(theme_payload: dict) -> str:
    r = post("/themes", theme_payload)
    if r.status_code != 200:
        err(f"Не удалось создать тему: {r.status_code} {r.text}")
        sys.exit(1)
    theme = r.json()
    ok(f"id={theme['id']}")
    ok(f"Специализации: {theme['specializations']}")
    return theme["id"]


def bind_students(theme_id: str, student_ids: list[str]):
    r = post(f"/themes/{theme_id}/students", student_ids)
    if r.status_code == 200:
        ok(f"Привязано студентов: {len(student_ids)}")
    else:
        err(f"{r.status_code} {r.text}")
        sys.exit(1)


def copy_to_specializations(theme_id: str):
    r = put(f"/themes/{theme_id}/copy-to-specializations")
    if r.status_code == 200:
        ok("Студенты скопированы во все специализации")
    else:
        err(f"{r.status_code} {r.text}")
        sys.exit(1)


def ml_sort(theme_id: str, specializations: list[str]):
    for spec in specializations:
        r = post(f"/themes/{theme_id}/specializations/{spec.replace(' ', '%20')}/ml-sort", {})
        if r.status_code == 200:
            ok(f"ML-сортировка: {spec}")
        else:
            err(f"{spec}: {r.status_code} {r.text}")


def print_results(theme_id: str, specializations: list[str]):
    for spec in specializations:
        r = get(
            f"/themes/{theme_id}/specializations/{spec.replace(' ', '%20')}/students"
            f"?useMLSorting=true&onlyActive=true"
        )
        if r.status_code != 200:
            err(f"{spec}: {r.status_code}")
            continue
        print(f"\n  [ {spec} ]")
        for s in r.json():
            print(f"    {s['priority'] + 1:>2}. {s['studentName']} ({s['hardSkill']})")


# ── main ──────────────────────────────────────────

log("Шаг 1: Импорт студентов из CSV")
students = import_students(CSV_FILE)
print(f"\n  Итого создано: {len(students)}")

log("Шаг 2: Создание темы")
theme_id = create_theme(THEME)

log("Шаг 3: Привязка студентов к теме")
student_ids = [s["id"] for s in students]
bind_students(theme_id, student_ids)

log("Шаг 4: Копирование в специализации")
copy_to_specializations(theme_id)

log("Шаг 5: ML-сортировка")
ml_sort(theme_id, THEME["specializations"])

log("Шаг 6: Результат")
print_results(theme_id, THEME["specializations"])

log("Готово")
result = {"theme_id": theme_id, "theme_name": THEME["name"], "student_ids": student_ids}
with open(RESULT_FILE, "w", encoding="utf-8") as f:
    json.dump(result, f, ensure_ascii=False, indent=2)
ok(f"Данные сохранены в {RESULT_FILE}")
ok(f"GET {BASE_URL}/themes/{theme_id}")
