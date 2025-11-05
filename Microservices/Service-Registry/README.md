# 🧭 Service Registry

O **Service Registry** é o **ponto central de registro e descoberta de serviços** na arquitetura de microserviços do sistema **Studies Delivery**.  
Ele utiliza o **Netflix Eureka Server**, provendo uma interface para **monitorar, registrar e descobrir** serviços de forma dinâmica e centralizada.

Isso elimina a necessidade de configurar manualmente endereços IP e portas entre microsserviços, tornando o sistema **mais resiliente, escalável e desacoplado**.

---

## 📘 Sumário

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Papel na Arquitetura](#-papel-na-arquitetura)
- [Técnicas e Padrões](#-técnicas-e-padrões)
- [Observações Técnicas](#-observações-técnicas)
- [Links relacionados](#-links-relacionados)

---

## 🧩 Tecnologias Utilizadas
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue)
![Eureka Server](https://img.shields.io/badge/Eureka%20Server-Netflix-orange)
![Java](https://img.shields.io/badge/Java-21-red)
![Maven](https://img.shields.io/badge/Maven-Build%20Tool-purple)


| Tecnologia | Descrição | Link |
|-------------|------------|------|
| **Spring Boot 3.5.6** | Framework que simplifica a criação, configuração e execução de aplicações Java baseadas em Spring. | [spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
| **Spring Cloud 2025.0.0** | Conjunto de ferramentas que estendem o Spring Boot para arquiteturas distribuídas e microsserviços. | [spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud) |
| **Netflix Eureka Server** | Implementação do padrão *Service Registry*, permitindo o registro e descoberta dinâmica de microsserviços. | [github.com/Netflix/eureka](https://github.com/Netflix/eureka) |
| **Java 21** | Linguagem de programação utilizada no projeto, trazendo novos recursos e melhor performance. | [openjdk.org/projects/jdk/21](https://openjdk.org/projects/jdk/21/) |
| **Maven** | Ferramenta de build e gerenciamento de dependências, responsável pelo ciclo de vida do projeto. | [maven.apache.org](https://maven.apache.org/) |


---

## 🏗️ Papel na Arquitetura

O **Service Registry** atua como o **núcleo de descoberta de serviços (Service Discovery)**.  
Todos os microsserviços da arquitetura se registram automaticamente neste servidor, permitindo comunicação desacoplada.

### ⚙️ Funções principais:
- Centraliza o **registro de todos os serviços ativos**.
- Permite que microsserviços se **descubram dinamicamente**.
- **Evita configurações fixas** de endereço entre serviços.
- Facilita **balanceamento de carga** e **tolerância a falhas**.

### 🔗 Relação com outros serviços:
- O **Gateway** usa o Service Registry para **rotear requisições** corretamente.
- Microsserviços como **Delivery Tracking**, **Courier Management**, etc., se **registram e consultam** o Eureka para comunicação entre si.

---

## 🧱 Técnicas e Padrões

Apesar de sua simplicidade, o projeto segue princípios sólidos de arquitetura distribuída e práticas do ecossistema **Spring Cloud Netflix**.

| Técnica / Padrão | Aplicação |
|------------------|-----------|
| **Service Discovery Pattern** | Implementado via **Eureka Server**, permitindo que cada serviço encontre dinamicamente outros serviços disponíveis. |
| **Centralized Service Registry** | O registro de serviços é centralizado, eliminando acoplamento estático entre instâncias. |
| **Cloud-Native Architecture** | Totalmente compatível com ambientes em nuvem e escalonamento horizontal. |
| **Configuration via YAML** | Simplifica a manutenção das propriedades da aplicação. |
| **Stateless Service** | O servidor não mantém estado de sessão — a informação de registro é efêmera e se renova via *heartbeats*. |

## 🧭 Observações Técnicas

- O projeto não possui lógica de domínio — é um componente de infraestrutura.
- É stateless e pode ser replicado em **múltiplas instâncias** para garantir **alta disponibilidade e failover**.
- Idealmente, deve ser o primeiro serviço a ser iniciado na arquitetura.
- A interface web do Eureka exibe em tempo real os serviços registrados e seu status.
- Pode ser facilmente integrado a ferramentas de monitoramento (como Spring Boot Admin).

---
## 🔗 Links Relacionados
- [Voltar ao README principal](../../README.md)
- [Delivery Tracking - README](../Delivery-Tracking/README.md)
- [Courier Management - README](../Courier-Management/README.md)
- [Gateway - README](../Gateway/README.md)

