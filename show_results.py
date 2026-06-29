"""
Показывает результат ML-сортировки по данным из demo_result.json

Запуск: python show_results.py
"""

import json
import sys
from pathlib import Path
from _common import get, BASE_URL

RESULT_FILE = "demo_result.json"

if not Path(RESULT_FILE).exists():
    print(f"Файл {RESULT_FILE} не найден — сначала запустите demo.py")
    sys.exit(1)

with open(RESULT_FILE, encoding="utf-8") as f:
    result = json.load(f)

theme_id    = result["theme_id"]
student_ids = result["student_ids"]

theme           = get(f"/themes/{theme_id}").json()
specializations = theme["specializations"]

full_students   = get("/students/by-ids", student_ids).json()
student_details = {s["id"]: s for s in full_students}

print(f"Тема:  {theme['name']}")
print(f"Автор: {theme['author']}")

for spec in specializations:
    students = get(
        f"/themes/{theme_id}/specializations/{spec.replace(' ', '%20')}/students"
        f"?useMLSorting=true&onlyActive=true"
    ).json()

    print(f"\nСпециализация: {spec}")
    print(f"  {'#':<4} {'Имя':<22} {'Специализация':<22} {'Бэкграунд':<35} {'Интересы'}")
    print(f"  {'-'*4} {'-'*22} {'-'*22} {'-'*35} {'-'*30}")

    for s in students:
        details    = student_details.get(s["studentId"], {})
        background = details.get("background", "")[:33]
        interests  = details.get("interests",  "")[:30]
        print(f"  {s['priority'] + 1:<4} {s['studentName']:<22} {s['hardSkill']:<22} {background:<35} {interests}")
