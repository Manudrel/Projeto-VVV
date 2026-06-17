<div align="center">

# ✈️ Projeto VVV

**Plataforma web de gestão de viagens com reservas, pagamentos e vendas.**

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.x-red?style=flat-square&logo=apachemaven)

> Projeto acadêmico desenvolvido na disciplina de Engenharia de Software.

</div>

---

## 📌 Visão Geral

O **Projeto VVV** é uma aplicação web desenvolvida em **Spring Boot 4** com interface **Thymeleaf**. O sistema gerencia o ciclo completo de viagens, incluindo:

- cadastro e autenticação de usuários
- controle de clientes, funcionários e transportadoras
- gestão de cidades, pontos de venda e modais de transporte
- criação e consulta de reservas e rotas
- emissão de tickets e processamento de pagamentos

A aplicação combina páginas server-side com APIs REST para atender diferentes cenários de uso.

---

## 🌟 Funcionalidades

- Autenticação e cadastro de clientes e funcionários
- Dashboard administrativo
- Gestão de clientes, funcionários, transportadoras, modais e pontos de venda
- Cadastro e consulta de cidades, rotas e reservas
- Emissão de tickets
- Processamento de pagamentos e cancelamento de transações
- Aprovação de vendas online

---

## 🛠️ Tecnologias

- Java 21
- Spring Boot 4.0.5
- Spring MVC
- Thymeleaf
- Spring Data JPA
- Spring Security
- PostgreSQL
- H2 (testes)
- Maven
- Docker / Docker Compose

---

## 📁 Estrutura do Projeto

```
Projeto-VVV/
└── demo/
    ├── pom.xml
    ├── docker-compose.yaml
    ├── dockerfile
    ├── mvnw
    ├── mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/com/projectvvv/
        │   │   ├── DemoApplication.java        # Classe principal
        │   │   └── domain/
        │   │       ├── controller/             # Controllers REST e de página
        │   │       ├── dto/                    # Objetos de transferência de dados
        │   │       ├── exception/              # Tratamento de erros
        │   │       ├── model/                  # Entidades de domínio
        │   │       ├── repository/             # Repositórios JPA
        │   │       └── service/                # Lógica de negócio
        │   └── resources/
        │       ├── application.properties      # Configurações da aplicação
        │       └── templates/                  # Views Thymeleaf
        └── test/
            └── java/                          # Testes unitários e de integração
```

---

## ⚙️ Configuração

O arquivo de configuração principal está em `demo/src/main/resources/application.properties`.

```properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/projectvvv
spring.datasource.username=admin
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

> Ajuste os valores de conexão conforme seu ambiente local.

---

## ▶️ Execução

### Com Docker Compose

```bash
cd demo
docker compose up --build
```

Abra no navegador:

```text
http://localhost:8080
```

Para encerrar:

```bash
docker compose down
```

### Local com Maven

```bash
cd demo
./mvnw clean spring-boot:run
```

Ou, se você tiver Maven instalado:

```bash
cd demo
mvn clean spring-boot:run
```

---

## 🧪 Testes

Executar todos os testes:

```bash
cd demo
./mvnw test
```

Executar um único teste:

```bash
cd demo
./mvnw -Dtest=NomeDaClasseTest test
```

> O projeto usa `spring-boot-webmvc-test` e `spring-boot-data-jpa-test` para suportar os testes do Spring Boot 4.

---

## 🌐 Rotas Principais

### Páginas

- `/` — Página inicial
- `/dashboard` — Dashboard
- `/auth/login` — Login
- `/auth/cadastro-cliente` — Cadastro de cliente
- `/auth/cadastro-funcionario` — Cadastro de funcionário
- `/cidades` — Gestão de cidades
- `/gerente/funcionarios` — Gestão de funcionários
- `/funcionario/clientes` — Gestão de clientes
- `/transportadoras` — Gestão de transportadoras
- `/pontos-venda` — Gestão de pontos de venda
- `/modais` — Gestão de modais
- `/reservas/nova` — Nova reserva
- `/reservas/consultar` — Consulta de reservas
- `/reservas/detalhe` — Detalhe de reserva
- `/tickets` — Gestão de tickets
- `/vendas-online/aprovacao` — Aprovação de vendas online

### APIs REST

- `/api/cidades`
- `/api/clientes`
- `/api/funcionarios`
- `/api/modais`
- `/api/pontos-venda`
- `/api/pagamentos`
- `/api/transportadoras`
- `/api/reservas`
- `/api/rota`
- `/api/rota-da-reserva`
- `/api/tickets`

---

## 💡 Observações

- Combina controllers de interface (`@Controller`) e APIs REST (`@RestController`).
- PostgreSQL é o banco principal; H2 é usado apenas nos testes.
- Projeto com foco acadêmico para demonstrar fluxo de gestão de viagens.

---

## 📄 Licença

Licenciado sob **MIT License**.


---
