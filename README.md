# AutoManager — Atividade V: Arquitetura de Microsserviços

Evolução do sistema AutoManager para uma **arquitetura de microsserviços**, separando o sistema em módulos independentes que se comunicam via API REST.

## 📋 Sobre o Projeto

Quinta e última atividade da série AutoManager (disciplina de Desenvolvimento Web — FATEC São José dos Campos). O sistema foi reestruturado em uma arquitetura de **microsserviços**, onde cada componente é um serviço independente com sua própria base de código, permitindo escalabilidade, manutenção e deploy independentes.

## 🔧 Tecnologias

- Java 17+
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- Spring HATEOAS
- Spring Web (REST)
- Maven
- H2 / MySQL
- Arquitetura de Microsserviços

## 🏗️ Arquitetura do Sistema

O projeto é composto por dois módulos/serviços independentes:

```
Atv-5-DW/
├── api/          # Microsserviço de API (gateway / serviço principal)
│   ├── src/
│   └── pom.xml
├── sistema/      # Microsserviço do Sistema (lógica de negócio)
│   ├── src/
│   └── pom.xml
└── .project
```

### Microsserviço `api`
- Responsável por receber e encaminhar as requisições externas
- Gerencia autenticação e autorização (JWT)
- Exposição dos endpoints REST públicos

### Microsserviço `sistema`
- Contém a lógica de negócio principal
- Gerencia as entidades: Usuários, Empresas, Veículos, Mercadorias, Serviços, Vendas
- Comunicação interna via REST

## 🔐 Segurança

- Autenticação via **JWT (JSON Web Token)**
- Controle de acesso por perfis (roles): ADMIN, GERENTE, VENDEDOR
- Filtragem de requisições com filtros de segurança
- Comunicação segura entre microsserviços

## 🌐 Endpoints Principais

### Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/login` | Autentica e retorna token JWT |

### Recursos (requerem JWT)
| Recurso | Endpoint Base |
|---------|---------------|
| Usuários | `/usuarios`, `/usuario/{id}` |
| Empresas | `/empresas`, `/empresa/{id}` |
| Veículos | `/veiculos`, `/veiculo/{id}` |
| Mercadorias | `/mercadorias`, `/mercadoria/{id}` |
| Serviços | `/servicos`, `/servico/{id}` |
| Vendas | `/vendas`, `/venda/{id}` |

## ▶️ Como Executar

Esta arquitetura requer a execução de cada microsserviço separadamente:

### 1. Iniciar o microsserviço `sistema`
```bash
git clone https://github.com/BrennoLyrio/Atv-5-DW.git
cd Atv-5-DW/sistema
./mvnw spring-boot:run
```

### 2. Iniciar o microsserviço `api`
```bash
cd ../api
./mvnw spring-boot:run
```

API disponível em `http://localhost:8080`

## 💡 Principais Conceitos Aplicados

- **Microsserviços**: Separação de responsabilidades em serviços independentes
- **API REST**: Comunicação entre serviços via HTTP
- **JWT**: Autenticação stateless entre serviços
- **HATEOAS**: Respostas auto-descritivas com links de navegação
- **Spring Security**: Proteção dos endpoints

## 👨‍💻 Autor

**Brenno Lyrio** — [GitHub](https://github.com/BrennoLyrio)
