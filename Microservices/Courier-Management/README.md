# Courier Management

O **Courier Domain** concentra toda a lógica de negócio e modelagem de dados relacionada aos **entregadores** (couriers).  
Ele representa a camada de domínio do microserviço **Courier Management**, sendo responsável por entidades, agregados e regras específicas deste contexto.

---

## 🧩 Responsabilidades

- Modelagem do entregador (Courier) e suas propriedades
- Cálculo de ganhos e disponibilidade
- Gestão de rotas e atribuições
- Comunicação com o **Delivery Domain** para recebimento de tarefas
- Publicação de eventos de atualização de status do entregador

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
| `GET`  | `/couriers` | Lista todos os entregadores |
| `GET`  | `/couriers/{id}` | Detalha um entregador específico |
| `POST` | `/couriers` | Registra um novo entregador |
| `PATCH` | `/couriers/{id}/availability` | Atualiza disponibilidade |
| `GET`  | `/couriers/{id}/earnings` | Consulta ganhos acumulados |

---

## 🧩 Diagramas
### Diagrama de classes
![Diagrama de classes](../../Docs/courier-domain-class-diagram.png)

### Diagrama completo
![Diagrama de classes](../../Docs/courier-full-diagram.png)

---
## Links Relacionados
[Voltar ao README principal](../../README.md)