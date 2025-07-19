### запуск: 
1) docker-compose up
2) через консоль создать необходимый топик
````
docker exec -it kafka-service bash /usr/bin/kafka-topics --bootstrap-server kafka:29092 --create --topic weyland-logging-topic --partitions 1 --replication-factor 1
````

3) перезапустить сервис bishop-service

4) Api вызова Weyland методов
````
http://localhost:8080/bishop/weyland
{
    "stringArg": "description",
    "intArg": 6
}
````

5) Отслеживание логов из кафки через консоль
````
docker exec -it kafka-service bash /usr/bin/kafka-console-consumer --topic weyland-logging-topic --bootstrap-server kafka:29092 --from-beginning
````

6) Api вызова команд:
````
http://localhost:8080/commands/request

{
    "description": "description",
    "priority": "COMMON",
    "author": "author name",
    "time": "2000-10-31T01:30:00.000"
}
````