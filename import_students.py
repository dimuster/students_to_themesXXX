"""
Импорт студентов из CSV файла.

Запуск: python import_students.py [students.csv]
"""

import csv
import json
import sys
from pathlib import Path
from _common import post, ok, err, BASE_URL

REQUIRED_COLUMNS = {"name", "hardSkill", "background", "interests"}


def load_csv(filepath: str) -> list[dict]:
    with open(filepath, encoding="utf-8") as f:
        reader = csv.DictReader(f)
        missing = REQUIRED_COLUMNS - set(reader.fieldnames or [])
        if missing:
            print(f"Ошибка: в CSV отсутствуют столбцы: {missing}")
            sys.exit(1)
        return list(reader)


def create_student(row: dict) -> dict | None:
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
        ok(f"{student['name']}  id={student['id']}")
        return student
    else:
        err(f"{row['name']}: {r.status_code} {r.text}")
        return None


def main():
    filepath = sys.argv[1] if len(sys.argv) >= 2 else "students.csv"

    if not Path(filepath).exists():
        print(f"Файл не найден: {filepath}")
        sys.exit(1)

    rows = load_csv(filepath)
    print(f"Найдено строк: {len(rows)}")
    print(f"Сервис:        {BASE_URL}")
    print()

    created, failed = [], []

    for i, row in enumerate(rows, 1):
        if not row.get("name", "").strip():
            print(f"  -- Строка {i}: пустое имя, пропускаем")
            continue

        print(f"[{i}/{len(rows)}] {row['name']}")
        student = create_student(row)
        (created if student else failed).append(row["name"] if not student else student)

    print(f"\n{'=' * 40}")
    print(f"Создано: {len(created)}")
    print(f"Ошибок:  {len(failed)}")

    if failed:
        print("Не удалось создать:")
        for name in failed:
            print(f"  - {name}")

    if created:
        output = Path(filepath).stem + "_created_ids.json"
        with open(output, "w", encoding="utf-8") as f:
            json.dump(
                [{"id": s["id"], "name": s["name"]} for s in created],
                f, ensure_ascii=False, indent=2,
            )
        print(f"\nID сохранены в: {output}")


if __name__ == "__main__":
    main()
