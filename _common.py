import sys
import io
import json
import requests

sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding="utf-8")

BASE_URL = "http://localhost:8080"


def post(path: str, body) -> requests.Response:
    return requests.post(
        f"{BASE_URL}{path}",
        headers={"Content-Type": "application/json"},
        data=json.dumps(body, ensure_ascii=False).encode("utf-8"),
    )


def get(path: str, body=None) -> requests.Response:
    kwargs = {"headers": {"Content-Type": "application/json"}}
    if body is not None:
        kwargs["data"] = json.dumps(body, ensure_ascii=False).encode("utf-8")
    return requests.get(f"{BASE_URL}{path}", **kwargs)


def put(path: str, body=None) -> requests.Response:
    kwargs = {"headers": {"Content-Type": "application/json"}}
    if body is not None:
        kwargs["data"] = json.dumps(body, ensure_ascii=False).encode("utf-8")
    return requests.put(f"{BASE_URL}{path}", **kwargs)


def log(msg: str):
    print(f"\n{'=' * 52}\n  {msg}\n{'=' * 52}")


def ok(msg: str):
    print(f"  [OK]  {msg}")


def err(msg: str):
    print(f"  [ERR] {msg}")
