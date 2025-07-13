запуск: 
1) docker-compose up -d
2) в консоль прописать

2.1) docker exec -it kafka-service bash /usr/bin/kafka-topics --bootstrap-server kafka:29092 --create --topic weather-event --partitions 1 --replication-factor 1

2.2) docker exec -it kafka-service bash /usr/bin/kafka-configs --bootstrap-server kafka:29092 --alter --entity-type topics --entity-name weather-event --add-config retention.ms=120000

3) перезапустить сервис weather-event-service