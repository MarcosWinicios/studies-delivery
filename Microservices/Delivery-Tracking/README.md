# Delivery Tracking

Este módulo representa o **núcleo de domínio** do serviço de entregas dentro do ecossistema da aplicação.  
Ele contém todas as **entidades, agregados, value objects, eventos e regras de negócio** relacionadas ao processo de **gestão de entregas**, sem dependências diretas com frameworks ou infraestrutura.

---

## 🧩 Responsabilidades

- Modelagem do domínio de entregas (entidades e value objects)
- Cálculo de taxas de entrega, tempo estimado e distância
- Geração e publicação de eventos de domínio
- Representação do ciclo de vida da entrega (preparação, coleta, transporte, conclusão)
- Integração com o **Courier Domain** para obtenção de informações sobre o entregador

---

## 🛠️ Tecnologias e Dependências

- [Java 21](https://openjdk.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Maven](https://maven.apache.org/)

---

## 🌐 Endpoints Principais

| Método | Endpoint | Descrição |
|:-------|:----------|:-----------|
| `GET`  | `/deliveries` | Lista todas as entregas registradas |
| `GET`  | `/deliveries/{id}` | Detalha uma entrega específica |
| `POST` | `/deliveries` | Cria uma nova entrega |
| `PATCH` | `/deliveries/{id}/assign-courier` | Atribui um entregador à entrega |
| `PATCH` | `/deliveries/{id}/complete` | Finaliza uma entrega |

---

## 🧩 Diagramas
### Diagrama de classes
![Diagrama de classes](../../Docs/delivery-tracking-domain-class-diagram.png)

### Diagrama completo
![Diagrama de classes](../../Docs/delivery-tracking-full-diagram.png)

---
## Links Relacionados
[Voltar ao README principal](../../README.md)
