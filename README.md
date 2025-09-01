# Shopping Cart

Shopping Cart это пример REST приложения на фреймворке Spring Boot.

## Требования

Для запуска приложения понадобятся **Java 24** с правильно настроенной 
переменной среды `JAVA_HOME` и **PostgreSQL 17**. Чтобы запустить приложение 
без изменения настроек, в Postgres надо создать пользователя `pguser` с паролем
`password` и базу данных `shoppingcart`. Postgres должен работать на порту 
`5433`.

Пример установки Java 24 для систем Ubuntu:
```bash
$ wget https://download.oracle.com/java/24/latest/jdk-24_linux-x64_bin.deb
$ sudo apt install ./jdk-24_linux-x64_bin.deb
```

При наличии Docker'a быстро поднять БД в Postgres можно следующим образом:
```bash
$ sudo docker run -p 5433:5432 -e POSTGRES_USER=pguser -e POSTGRES_PASSWORD=password -e POSTGRES_DB=shoppingcart -d postgres:17
```

## Запуск

Если вы удовлетворили все требования, скопируйте репозиторий на свой компьютер:
```bash
$ git clone https://github.com/notLinode/shopping-cart.git
```

Зайдите в папку и сделайте `mvnw` исполняемым файлом:
```bash
$ cd shopping-cart
$ chmod u+x mvnw
```

Запустите приложение:
```bash
$ mvnw spring-boot:run
```

## Документация

Сгенерированную документацию эндпоинтов можно будет найти в
`/swagger-ui/index.html`.
