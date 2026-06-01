## 🔧 Устранение неисправностей и поддержка

- [🔧 Устранение неисправностей](#устранение-неисправностей)
  - [Распространенные проблемы](#распространенные-проблемы)
  - [Логи и диагностика](#логи-и-диагностика)
- [📞 Поддержка](#поддержка)
  - [Полезные команды](#полезные-команды)
  - [Контакты для поддержки](#контакты-для-поддержки)
  - [Полезные ссылки](#полезные-ссылки)


## <a id = "устранение-неисправностей">🔧 Устранение неисправностей </a>

### <a id="распространенные-проблемы">Распространенные проблемы</a>

**1. Ошибка подключения к базе данных:**
```bash
# Проверка подключения к PostgreSQL
psql -h localhost -U postgres -d student_themes

# Проверка расширения UUID
\c student_themes
SELECT * FROM pg_extension WHERE extname = 'uuid-ossp';
```

**2. ML сервис недоступен:**
```bash
# Проверка порта
netstat -tulpn | grep 8000

# Проверка зависимостей Python
python -c "import sentence_transformers; print('OK')"

# Перезапуск ML сервиса
pkill -f "python main.py"
python main.py
```

**3. Ошибки миграции Flyway:**
```bash
# Проверка состояния миграций
./gradlew flywayInfo -Dspring.profiles.active=prod

# Восстановление после ошибки
./gradlew flywayRepair -Dspring.profiles.active=prod
```

**4. Проблемы с памятью:**
```bash
# Увеличить память JVM
export JAVA_OPTS="-Xmx2g -Xms1g"

# Для ML сервиса (если большая модель)
export TRANSFORMERS_CACHE=/path/to/cache
```

### <a id="логи-и-диагностика">Логи и диагностика</a>

**Просмотр логов:**
```bash
# Backend логи
tail -f logs/application.log

# ML сервис логи (консоль)
# Или перенаправление в файл:
python main.py > ml-service.log 2>&1

# Логи базы данных
sudo tail -f /var/lib/postgresql/*/log/postgresql-*.log
```

**Проверка здоровья системы:**
```bash
#!/bin/bash
# health-check.sh

echo "=== System Health Check ==="

# Database
echo "Database:"
psql -h localhost -U postgres -d student_themes -c "SELECT version();" 2>/dev/null || echo "Database connection failed"

# ML Service
echo "ML Service:"
curl -s http://localhost:8000/health | jq . 2>/dev/null || echo "ML Service unavailable"

echo "=== Check Complete ==="
```

## <a id = "поддержка">📞 Поддержка </a>

### <a id="полезные-команды">Полезные команды</a>

**Быстрая проверка системы:**
```bash
# Однострочник для проверки всех компонентов
curl -s http://localhost:8080/themes/ml-health && \
curl -s http://localhost:8000/health && \
echo "All systems operational"
```


### <a id="контакты-для-поддержки">Контакты для поддержки</a>

- **Backend вопросы**: 
- **ML вопросы**:  
- **Базы данных**: 
- **Экстренные случаи**: 

### <a id="полезные-ссылки">Полезные ссылки</a>

- **Документация API**: 
- **ML Service Docs**:
- **База данных**: 
- **Мониторинг**:

---

**Лицензия**: MIT  
**Версия**: 1.0.0  
**Дата последнего обновления**: 2025-11-28

Для получения дополнительной помощи обращайтесь к документации или создавайте issue в репозитории проекта.