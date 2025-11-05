# 🚪 Gateway

O **Gateway** é o ponto de entrada principal do ecossistema de microsserviços do sistema de entregas.  
Ele atua como **roteador inteligente**, responsável por direcionar as requisições HTTP aos serviços internos — como **Delivery Tracking** e **Courier Management** — utilizando **balanceamento de carga** e **descoberta de serviços via Eureka**.

Este serviço também implementa mecanismos de **resiliência e tolerância a falhas**, como **circuit breakers**, **retries** e **timeouts**, garantindo estabilidade na comunicação entre microsserviços.

---

## 🧭 Sumário
- [Tecnologias e Dependências](#-tecnologias-e-dependências)
- [Responsabilidades](#-responsabilidades)
- [Estrutura e Estratégia de Roteamento](#-estrutura-e-estratégia-de-roteamento)
- [Resiliência e Tolerância a Falhas](#-resiliência-e-tolerância-a-falhas)
- [Técnicas e Padrões de Projeto](#-técnicas-e-padrões-de-projeto)
- [Links Relacionados](#links-relacionados)

---

## 🛠️ Tecnologias e Dependências

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-brightgreen?logo=springboot)
![Spring Cloud Gateway](https://img.shields.io/badge/Spring_Cloud_Gateway-WebFlux-black?logo=spring)
![Eureka Client](https://img.shields.io/badge/Eureka-Discovery_Client-orange?logo=spring)
![Resilience4j](https://img.shields.io/badge/Resilience4j-CircuitBreaker-red?logo=resilience4j)

| Tecnologia | Descrição | Link |
|-------------|------------|------|
| **Java 21** | Linguagem base do projeto. | [https://www.oracle.com/java/](https://www.oracle.com/java/) |
| **Spring Boot** | Framework principal de inicialização e configuração. | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| **Spring Cloud Gateway** | Framework de roteamento reativo, baseado em WebFlux. | [https://spring.io/projects/spring-cloud-gateway](https://spring.io/projects/spring-cloud-gateway) |
| **Spring WebFlux** | Motor reativo do Spring para lidar com IO não-bloqueante. | [https://docs.spring.io/spring-framework/reference/web/webflux.html](https://docs.spring.io/spring-framework/reference/web/webflux.html) |
| **Eureka Client** | Cliente de descoberta de serviços (Service Discovery). | [https://spring.io/projects/spring-cloud-netflix](https://spring.io/projects/spring-cloud-netflix) |
| **Resilience4j** | Implementa circuit breakers e políticas de resiliência. | [https://resilience4j.readme.io/](https://resilience4j.readme.io/) |

---

## 🧩 Responsabilidades

- Atuar como **API Gateway** do ecossistema.
- Gerenciar **roteamento** e **balanceamento de carga** entre serviços registrados no **Eureka**.
- Implementar **filtros** para manipulação de requisições e respostas.
- Aplicar **políticas de resiliência** com **Resilience4j CircuitBreaker**.
- Proteger rotas públicas e internas com filtros e reescrita de caminhos.
- Centralizar logs e monitorar o tráfego entre microsserviços.

---

## 🛣️ Estrutura e Estratégia de Roteamento

O Gateway utiliza o **Spring Cloud Gateway** para definir rotas e filtros reativos declarativamente via `application.yml`.

### Exemplo de Rotas Configuradas:

| Serviço | Path de Roteamento | URI Interna | Observações |
|----------|--------------------|--------------|--------------|
| **Delivery Tracking** | `/api/v1/deliveries/**` | `lb://delivery-tracking` | Inclui `Retry` e `CircuitBreaker`. |
| **Courier Management** | `/api/v1/couriers/**` | `lb://courier-management` | Rotas privadas internas. |
| **Courier Management (Público)** | `/public/couriers/**` | `lb://courier-management` | Resposta sanitizada (remove dados sensíveis). |

### Exemplos de Filtros Utilizados
- `Retry` → Tentativas automáticas em falhas temporárias (`500`, `502`).
- `CircuitBreaker` → Interrompe chamadas quando um serviço está instável.
- `RewritePath` → Reescreve o caminho da rota para compatibilizar APIs públicas.
- `RemoveJsonAttributesResponseBody` → Remove campos confidenciais (como `phone` e `pendingDeliveries`).

---

## 🛡️ Resiliência e Tolerância a Falhas

O projeto aplica **Resilience4j** integrado ao **Spring Cloud Gateway** para garantir robustez no tráfego entre serviços.

### Estratégias configuradas:
- **CircuitBreaker:** Isola falhas persistentes, evitando sobrecarga.
- **Retry:** Reexecuta chamadas temporariamente falhas com *backoff exponencial*.
- **Timeouts:** Define limite máximo de resposta (300ms por requisição).
- **Logs reativos:** O bean `Resilience4jCircuitBreakerEventConsumer` registra os eventos do circuito, facilitando observabilidade e debugging.

---

## 🧠 Técnicas e Padrões de Projeto

| Técnica/Padrão | Aplicação |
|----------------|------------|
| **API Gateway Pattern** | Centraliza o tráfego e abstrai a comunicação com os microsserviços. |
| **Reactive Programming (WebFlux)** | Processamento não-bloqueante e escalável. |
| **Service Discovery Pattern** | Uso de **Eureka Client** para descobrir serviços dinamicamente. |
| **Circuit Breaker Pattern** | Implementado com **Resilience4j**. |
| **Retry & Backoff** | Reexecução inteligente de chamadas com falhas temporárias. |
| **Configuration as Code** | Toda a lógica de roteamento e resiliência é declarada no `application.yml`. |

---

## 🔗 Links Relacionados
- [Voltar ao README principal](../../README.md)
- [Delivery Tracking - README](../Delivery-Tracking/README.md)
- [Courier Management - README](../Courier-Management/README.md)
- [Service Registry - README](../Service-Registry/README.md)
