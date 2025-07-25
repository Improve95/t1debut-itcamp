### Сокрытие данных
я использовал JWE токен для сокрытия данных от посторонних, 
без private ключа получить информацию из токена не представляется возможным

а еще здесь я исправил подход к auth системе, сделал ее token-base, а не session, как в 4 дз

### Запуск
1) создать приватные ключи auth-access-private.pem и auth-refresh-private.pem в пакете
   auth-service/src/main/resources/keys/private

   создать к ним публичные ключи в пакете public
2) docker compose up -d
3) все эндпоинты по адресу: http://localhost:8074/swagger-ui/index.html#/