# Marketplace Calculator

Игрушечный RESTful API калькулятор корзин для абстрактного маркетплейса в вакууме.  
Используется Java 11, Spring Boot и Maven.

* В используемом подходе к проектированию, как главное, можно отметить _иммутабельность внутренних
  абстракций_, ибо это облегчает как программирование, так и отладку.
* Несмотря на _стремление к функциональному стилю_, данный подход больше подходит под определение
  _мультипарадигменного проектирования_, поскольку функциональные концепты не везде удачно
  натягиваются на реальный мир, как например при кешировании, или при работе с БД.
* _Проверяемые исключения не используются_, вместо них используются непроверяемые исключения или
  _Optional_. Как показывает практика, проверяемые исключения неудобны в работе, и больше не
  используются в современных решениях, например в Kotlin.
* Проект _тщательно задокументирован_, в частности, для всех абстракций приводятся примеры и
  объяснения тех или иных технических решений. Не лишним будет почитать и _package-info объясняющие
  логику декомпозиции предметной области_.
* Настроен CI на основе Docker-образа, контейнер разворачивается в любом k8s кластере.

## Сборка с Java

Вам понадобится система сборки Maven. Выполните команду `mvn clean package`. Полученный
self-executable jar можно запускать, например

```shell
 java -jar target/*.jar
```

По умолчанию, приложение будет запущено на [localhost:8080](http://localhost:8080/)

## Сборка с Docker

Выполните команду `docker build . -t marketplace-calculator` чтобы собрать образ. Теперь вы можете
запустить контейнер с приложением:

```shell
docker run -v `pwd`:`pwd` -w `pwd` -p 80:8080 --rm marketplace-calculator
```

Приложение будет развернуто на [localhost](http://localhost/) и готово к использованию.

## Deploy

Благодаря Dockerfile мы легко можем развернуть наше приложение где угодно, всего лишь описав
k8s-дескриптор с секретами
и [переменными среды окружения](./src/main/java/com/alidi/calculator/BackendApplication.java) для
нужной инфраструктуры. В данном примере, оно уже развернуто в Digital Ocean App Platform по адресу:

* [marketplace-calculator-cl95n.ondigitalocean.app](https://marketplace-calculator-cl95n.ondigitalocean.app/price?productId=1)

## Использование

Для расчёта цены корзины используется HTTP endpoint `POST /calculate-cart-price?id={}`, например:

```shell
curl -d '{"payment":"Google Pay","products":[{"id":1,"count":2}],"addressId":3}' -H "Content-Type: application/json" -X POST https://marketplace-calculator-cl95n.ondigitalocean.app/calculate-cart-price/
```

Поскольку приложение доступно из сети, эту команду можно сразу запустить в терминале и поиграть с
разными параметрами наблюдая ответы сервиса, ну, или попытаться всё сломать 🙂





