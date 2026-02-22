# Sistema CTO - README PRINCIPAL

### Sumario
- [Visão Geral](#1-sistema-cto---visão-geral)
- [Funcionalidades Principais](#2-funcionalidades-principais)
    - [CTO](#21-cto)
    - [Models](#22-models)
    - [Sensor](#23-sensor)
    - [Integração com SNS](#24-integração-com-sns)
- [Arquitetura](#3-arquitetura)
- [Documentação de API](#4-documentação-de-api)
- [Tecnologias Utilizadas](#5-tecnologias-utilizadas)
- [Documentação](#6-documentação)

## 1. Sistema CTO - Visão Geral
O **Sistema CTO** é uma aplicação back-end desenvolvida para gerenciar **CTOs (Caixas de Terminação Óptica)**, **Modelos** e **Sensores**, com integração a eventos assíncronos via **AWS SNS (simulado com LocalStack)**.

O sistema foi projetado seguindo boas práticas de arquitetura **REST**, validações de domínio e mensageria, garantindo consistência dos dados e regras de negócio bem definidas.

## 2. Funcionalidades Principais

### 2.1 CTO

- Cadastro de CTOs

- Validação para impedir nomes duplicados

- Associação obrigatória a um modelo existente

- Impedimento de exclusão caso exista sensor com status ALARMADO

### 2.2 Models

- Cadastro de modelos

- Validação para impedir duplicidade de nome + fabricante

- Permite modelos com mesmo nome desde que fabricantes sejam diferentes

### 2.3 Sensor

- Cadastro de sensores associados a uma CTO

- Consulta de sensores por CTO

- Controle de status:
    - ```ALARMADO```
    - ```NÃO_ALARMADO```

### 2.4 Integração com SNS

- Recebimento de eventos de sensores via SNS

- Processamento de eventos para atualização de status

- Simulação do ambiente AWS utilizando LocalStack

---

## 3. Arquitetura

- API REST desenvolvida com Spring Boot

- Persistência com Spring Data JPA

- Mensageria assíncrona via AWS SNS

- Serialização JSON com Jackson

- Documentação automática via Swagger / OpenAPI

A aplicação segue separação clara de responsabilidades:

```Controller``` - Camada de entrada (HTTP)

```Service``` - Regra de negócio

```Repository``` - Persistência

```DTOs``` - Contratos de entrada e saída

```Mapper``` - Conversão entre entidades e DTOs

---

## 4. Documentação de API

A API é documentada utilizando Swagger e pode ser acessada após subir a aplicação:

```
http://localhost:8080/swagger-ui.html
```
ou
```
http://localhost:8080/swagger-ui/index.html
```

---

## 5. Tecnologias Utilizadas

- **Java 21**

- **Spring Boot**

- **Spring Data JPA**

- **Maven**

- **Banco de dados relacional (H2)**

- **Docker & Docker Compose**

- **LocalStack(AWS SNS)**

- **Swagger / OpenAPI**


---

## 6. Documentação

Abaixo estão os documentos disponíveis:

| Documento                                     | Descrição                                                       |
|-----------------------------------------------|-----------------------------------------------------------------|
| [Guia de Instalação](./docs/installation.md)  | Instruções para configurar o ambiente local e executar o projeto |

