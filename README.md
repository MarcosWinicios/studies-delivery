# 🚚 Delivery System — Microservices Architecture

Arquitetura de microserviços desenvolvida durante a **Imersão Microserviços - Algaworks**, simulando o fluxo completo de entregas: desde o gerenciamento de entregadores até o rastreamento de entregas em tempo real.

> Sistema baseado em **Spring Cloud + Eureka + Kafka**, com cada serviço isolado e seu próprio banco de dados.

---

## 📘 Sumário
- [Visão Geral](#-visão-geral)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura e Comunicação](#-arquitetura-e-comunicação)
- [Especificações dos Microserviços](#-especificações-dos-microserviços)
- [Como Executar o Projeto Localmente](#-como-executar-o-projeto-localmente)
- [Autor](#-autor)

---

## 🚀 Visão Geral

O sistema é composto por múltiplos microserviços independentes que se comunicam entre si via **HTTP balanceado com Eureka** (Service Discovery), utilizando o ecossistema **Spring Cloud**.  
O **Service Registry (Eureka)** atua como ponto central de descoberta, permitindo que os serviços se encontrem dinamicamente sem dependência de endereços fixos.


## 🧱 Estrutura do Projeto

| Diretório | Descrição | Porta Padrão |
|-----------|------------|--------------|
| **service-registry** | Registro de serviços (**Eureka Server**) | `8761` |
| **gateway** | API Gateway (roteamento via `lb://`) | `9999` |
| **delivery-tracking** | Serviço de entregas (publica eventos Kafka, expõe APIs REST) | `8081` |
| **courier-management** | Serviço de couriers (consome eventos Kafka, expõe APIs REST) | `8082` |
| **docker-compose.yml** | Infraestrutura local (Kafka, Kafka UI, PostgreSQL) | — |


---

## 🛠️ Tecnologias Utilizadas


![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-6DB33F?logo=spring&logoColor=white)
![Spring Kafka](https://img.shields.io/badge/Kafka-Event_Driven-black?logo=apachekafka)
![Eureka](https://img.shields.io/badge/Eureka%20Server-Service%20Registry-6DB33F?logo=spring&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.x-231F20?logo=apachekafka&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-blue?logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-C71A36?logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-orange?logo=junit5)

| Tecnologia | Descrição | Link Oficial |
|:-----------|:-----------|:--------------|
| **Java 21** | Linguagem base do projeto. | [oracle.com/java](https://www.oracle.com/java/) |
| **Spring Boot 3.5.6** | Framework principal para os microserviços. | [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| **Spring Cloud 2025.0.0** | Suite de ferramentas para microsserviços. | [spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud) |
| **Netflix Eureka Server** | Registro e descoberta de serviços. | [spring-cloud-netflix](https://cloud.spring.io/spring-cloud-netflix/reference/html/) |
| **Spring Cloud Gateway** | API Gateway para roteamento e filtros. | [spring-cloud-gateway](https://spring.io/projects/spring-cloud-gateway) |
| **Spring Kafka** | Integração com Apache Kafka para comunicação assíncrona e eventos. | [spring.io/projects/spring-kafka](https://spring.io/projects/spring-kafka) |
| **Apache Kafka / Spring Kafka** | Mensageria assíncrona entre serviços. | [kafka.apache.org](https://kafka.apache.org/) / [spring-kafka](https://spring.io/projects/spring-kafka) |
| **PostgreSQL** | Banco de dados relacional individual por serviço. | [postgresql.org](https://www.postgresql.org/) |
| **Docker / Compose** | Containerização e orquestração local. | [docker.com](https://www.docker.com/) |
| **Maven** | Gerenciador de build e dependências. | [maven.apache.org](https://maven.apache.org/) |
| **Lombok** | Reduz boilerplate de código Java. | [projectlombok.org](https://projectlombok.org/) |
| **Resilience4j** | Implementa circuit breaker e retry. | [resilience4j.readme.io](https://resilience4j.readme.io/) |
| **JUnit 5** | Framework de testes unitários e de integração. | [junit.org/junit5](https://junit.org/junit5/) |

---

## 🧩 Arquitetura e Comunicação

**Principais Padrões:**

- **HTTP via Gateway:**  
  Cliente externo → **API Gateway** (`http://localhost:9999`) → roteamento dinâmico via `lb://service-id`.

- **HTTP interno entre serviços:**  
  Os serviços utilizam o `@LoadBalanced` (RestClient / RestTemplate / WebClient) para resolver nomes lógicos de serviços no Eureka e comunicar-se diretamente — **o tráfego não passa pelo Eureka**.

- **Mensageria (Kafka):**
  - `delivery-tracking` publica eventos no tópico `deliveries.v1.events`.
  - `courier-management` consome esses eventos via `@KafkaListener`.
  - Broker e interface (`Kafka UI`) estão definidos no `docker-compose`.

- **Persistência:**  
  Cada microserviço possui seu **banco PostgreSQL próprio**, aplicando o princípio *database per service*.

**🗺️ Diagrama da Arquitetura:**
![Arquitetura](Docs/architecture.png)


---
## 📁 Especificações dos microserviços

- [Courier Management Service - README](Microservices/Courier-Management/README.md)
- [Delivery Tracking Service - README](Microservices/Delivery-Tracking/README.md)
- [Gateway - README](Microservices/Gateway/README.md)
- [Service Registry - README](Microservices/Service-Registry/README.md)

---

## 🧩 Como Executar o Projeto Localmente

Siga as etapas abaixo para executar todo o ambiente de forma local, incluindo o Eureka, os microserviços e o ambiente de mensageria (Kafka + Postgres).

---

### 🐳 1. Subir os containers de infraestrutura

O projeto utiliza **Docker Compose** para subir os serviços auxiliares:
- **Kafka** (broker de eventos)
- **Kafka UI** (interface de visualização dos tópicos e mensagens)
- **PostgreSQL** (banco de dados dos microserviços)

Execute no diretório raiz do projeto:

```bash
  docker-compose up -d
```

### ⚙️ 2. Iniciar o Service Registry (Eureka Server)
O Eureka Server deve ser executado fora do Docker, pois os demais serviços estão configurados para registrar-se nele via rede do host.
```bash
  cd service-registry
  mvn spring-boot:run
```

Acesse o painel do Eureka em: 👉 http://localhost:8761

Você verá a lista dos microserviços conforme eles forem subindo.

---
### 🧠 3. Iniciar os Microserviços
```bash
  cd gateway
  mvn spring-boot:run
```
```bash
  cd delivery-tracking
  mvn spring-boot:run
```
```bash
  cd courier-management
  mvn spring-boot:run
```
🔍 Aguarde até que todos apareçam como UP no painel do Eureka (http://localhost:8761).

---

### 🌐 4. Acessar o Gateway e os Endpoints
Todas as requisições externas devem ser feitas via API Gateway, que realiza o roteamento automático com base nos nomes lógicos dos serviços registrados no Eureka.

Exemplo de requisição:

```bash
  curl -X GET http://localhost:9999/api/v1/deliveries
```


### 💬 5. Testar com o Postman
Uma collection Postman com os endpoints configurados está disponível na pasta:
`/DOCS/DeliverySystem.postman_collection.json`

### 📊 6. Visualizar Mensagens no Kafka

Após publicar eventos no sistema (delivery-tracking → courier-management):

Acesse o Kafka UI em:
👉 http://localhost:8089

Você poderá ver os tópicos, mensagens e consumidores registrados.

---

### ✍️ Autor


Desenvolvido durante a **Imersão Microserviços - Algaworks**  
por **Marcos Winicios**.
