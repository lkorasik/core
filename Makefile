# Пересоздать контейнер с базой данных
# 1. Остановить контейнер с базой данных
# 2. Удаляем контейнер с базой данным
# 3. Создает новый контейнер с базой данных
recreate_db:
	@echo "Start database recreating"
	@docker container stop db
	@docker container rm db
	@docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=180401 -e POSTGRES_USER=postgres -e POSTGRES_DB=Groopster --name db postgres:alpine3.19
	@echo "End database recreating"
