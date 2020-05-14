# Research масштабирования кафки в рамках задачи TSKAD-209

_https://jira.stdev.ru/browse/TSKAD-209_

## Как запускать

Для начала, нужно запустить окружение, и создать 1 специальный topic. 

В репозитории есть 3 docker-compose файла

 - docker-compose.yaml 
 - docker-compose-flexible-env.yaml
 - docker-compose-lenses.yaml
 
Разберем по порядку

### docker-compose-lenses.yaml
Предназначен для запуска kafka-окружения с облегченным вариантом панели lenses.io
Ссылка на документацию https://github.com/lensesio/fast-data-dev

### docker-compose-flexible-env.yaml
Предназначен для запуска собственного окружения в гибкой среде, чтобы можно было настраивать компоненты
(kafka, zookeeper) так, как вам необходимо. Например, мы хотим создать 3 брокера кафки и 3 ноды zookeeper,
Но по умолчанию создается по 1 ноде. Нам просто нужно сделать copy-paste нужных нам сервисов и запустить docker-compose

```
  zoo1:
    image: confluentinc/cp-zookeeper
    restart: unless-stopped
    hostname: zoo1
    ports:
      - "2181:2181"
    environment:
      ZOO_MY_ID: 1
      ZOO_PORT: 2181
      ZOO_SERVERS: server.1=zoo1:2888:3888
    volumes:
      - kafka:/zoo1/data
      - kafka:/zoo1/datalog

  zoo2:
    image: confluentinc/cp-zookeeper
    restart: unless-stopped
    hostname: zoo2
    ports:
      - "2182:2181"
    environment:
      ZOO_MY_ID: 2
      ZOO_PORT: 2182
      ZOO_SERVERS: server.1=zoo1:2888:3888
    volumes:
      - kafka:/zoo2/data
      - kafka:/zoo2/datalog

  zoo3:
    image: confluentinc/cp-zookeeper
    restart: unless-stopped
    hostname: zoo3
    ports:
      - "2183:2181"
    environment:
      ZOO_MY_ID: 3
      ZOO_PORT: 2183
      ZOO_SERVERS: server.1=zoo1:2888:3888
    volumes:
      - kafka:/zoo3/data
      - kafka:/zoo3/datalog

  kafka1:
    image: confluentinc/cp-kafka
    hostname: kafka1
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka1:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zoo1:2181"
      KAFKA_BROKER_ID: 1
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO,kafka.producer.async.DefaultEventHandler=INFO,state.change.logger=INFO"
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    volumes:
      - kafka:/var/lib/kafka/data
    depends_on:
      - zoo1
      - zoo2
      - zoo3
 
  kafka2:
    image: confluentinc/cp-kafka
    hostname: kafka2
    ports:
      - "9093:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka1:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zoo2:2181"
      KAFKA_BROKER_ID: 2
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO,kafka.producer.async.DefaultEventHandler=INFO,state.change.logger=INFO"
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    volumes:
      - kafka:/var/lib/kafka/data
    depends_on:
      - zoo1
      - zoo2
      - zoo3
 
  kafka2:
    image: confluentinc/cp-kafka
    hostname: kafka2
    ports:
      - "9094:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://kafka1:19092,LISTENER_DOCKER_EXTERNAL://${DOCKER_HOST_IP:-127.0.0.1}:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zoo3:2181"
      KAFKA_BROKER_ID: 3
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO,kafka.producer.async.DefaultEventHandler=INFO,state.change.logger=INFO"
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    volumes:
      - kafka:/var/lib/kafka/data
    depends_on:
      - zoo1
      - zoo2
      - zoo3

```

Тем самым, Вы получаете распределенную среду для разработки и тестирования кафки, с возможностью ее гибко настраивать и тестировать.

### docker-compose.yaml (default)

Коробочное решение от lenses.io, но чтоб его получить, надо сначала зарегистрироваться тут:

https://lenses.io/downloads/lenses/?s=from-fdd

Затем, в `EULA` указать ссылку, которая вам придет по почте, после регистрации

```
  lensesio-box:
    image: lensesio/box
    ports:
      - 3030:3030
      - 9092:9092
      - 8081:8081
    environment:
      - ADV_HOST=127.0.0.1
      - EULA=${EULA} <---- вот тут
```
Данная коробка имеет намного больше возможностей, по сравнению с `lensesio/fast-data-dev`

### Создание топика `fat_topic`

Сервис `deliver` имеет 2 реализации продюсеров:

- FatTopicProducerService
- ThinTopicProducerService

**ThinTopicProducerService** отправляет сообщение в `thin_topic_${prefix}`, где `prefix` - это рандомный номер. При отправке сообщения, проверяется создан ли этот продюсер и есть ли он в кэше, если нет, создается топик и продюсер, и кладется в кэш. Затем уже отправляется сообщение в нужный топик. Это реализация создания топиков "на лету"

**FatTopicProducerService** отправляется сообщение в `fat_topic`. Он должен быть создан вручную в кафке. 