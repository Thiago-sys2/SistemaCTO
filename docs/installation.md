# SistemaCTO - Guia Completo de Instalação

## Sumario
- [Visão Geral](#1-sistemacto---visão-geral)
- [Clonando o Repositório](#2-clonando-o-repositório)
- [Ferramentas Necessários](#3-ferramentas-necessárias)
    - [Java](#31-java)
    - [Maven](#32-maven)
    - [Git](#33-git)
    - [Docker & Docker Compose](#34-docker--docker-compose)
    - [AWS](#35-aws)
- [Subindo o banco com Docker](#4-subindo-o-localstack)
- [Subindo o Container](#5-subindo-o-container)
- [Configuração do SNS no LocalStack](#6-configuração-do-sns-no-localstack)
    - [Criar o tópico SNS - PowerShell](#61-criar-o-tópico-sns---powershell )
    - [Criar a subscription HTTP](#62-criar-a-subscription-http-listener-da-aplicação---powershell)
    - [Testando o SNS](#63-testando-o-snspublicar-evento)
- [Configurando o application.yml](#7-configure-o-applicationyml)
- [Executando e verificando se funcionou](#8-executando-e-verificando-se-funcionou)


## 1. SistemaCTO - Visão Geral
Este documento descreve **passo a passo** como instalar, configurar e executar o projeto **Sistema CTO** em um ambiente local, desde o clone do repositório até o acesso à API.

---

## 2. Clonando o Repositório
Primeiro, clone o repositório do projeto para sua máquina local:

```
git clone https://github.com/Thiago-sys2/SistemaCTO
```

---

## 3. Ferramentas Necessárias

Instale **antes de tudo**:

### 3.1 Java
- Versão **21**
- Verificar instalação:
``` 
java -version
```

### 3.2 Maven
- Versão 3.8+
- Verificar instalação:
```
mvn -version
```

### 3.3 Git
- Verificar instalação:
```
git --version
```

---

### 3.4 Docker & Docker Compose
- Utilizados para subir o banco de dados:
```
docker --version
docker compose version
```

### 3.5 AWS
- Verifique a versão da aws instalado:
```
aws --version
```

---

### 4. Subindo o LocalStack
- Verifique ou crie o arquivo ```docker-compose.yml``` na raiz do projeto:

``` 
version: "3.8"

services:
  localstack:
    image: localstack/localstack
    container_name: localstack
    ports:
      - "4566:4566"
    environment:
      - SERVICE=sns
      - DEFAULT_REGION=us-east-1
    volumes:
      - "/var/run/docker.sock:/var/run/docker.sock"
```

---

## 5. Subindo o Container
- No diretório do projeto:
  ``` docker compose up -d ```

- Verifique se o container está rodando:
  ``` docker ps ```

---

## 6. Configuração do SNS no LocalStack

### 6.1 Criar o tópico SNS - PowerShell
```
aws --endpoint-url=http://localhost:4566 sns create-topic ^
  --name events-sensor
```
O retorno será algo como:
```
{
  "TopicArn": "arn:aws:sns:us-east-1:000000000000:events-sensor"
}

```

### 6.2 Criar a subscription HTTP (listener da aplicação) - PowerShell
```
aws --endpoint-url=http://localhost:4566 sns subscribe ^
  --topic-arn arn:aws:sns:us-east-1:000000000000:events-sensor ^
  --protocol http ^
  --notification-endpoint http://host.docker.internal:8080/sns/receive
```

### 6.3 Testando o SNS(Publicar Evento)

Para simular um evento de sensor via SNS:

```
aws --endpoint-url=http://localhost:4566 sns publish ^
  --topic-arn arn:aws:sns:us-east-1:000000000000:events-sensor ^
  --message "{\"ctoId\":1,\"status\":\"ALARMED\"}"
```

---

## 7. Configure o application.yml
- Verifique se está desse mesmo formato:

```
server:
  port: 8080

spring:
  application:
    name: sistema-cto

  datasource:
    url: jdbc:h2:mem:sistema_cto
    driver-class-name: org.h2.Driver
    username: sa
    password: sa

  h2:
    console:
      enabled: true
      path: /h2-console

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect

  cloud:
    aws:
      region:
        static: us-east-1
      credentials:
        access-key: test
        secret-key: test
      endpoint:
        sns: http://localhost:4566

logging:
  level:
    software.amazon.awssdk: INFO
    software.amazon.awssdk.services.sns: DEBUG

app:
  sns:
    sensor-topic: sensor-status-topic
```

---

## 8. Executando e verificando se funcionou
- Opção 1: ``` mvn spring-boot:run ```
- Opção 2: Execute a classe ``` SistemaCtoApplication.java```

- Após iniciar, a aplicação estará disponível em:
  ``` http://localhost:8080 ```, ``` http://localhost:8080/swagger-ui.html ``` ou ```http://localhost:8080/swagger-ui/index.html```