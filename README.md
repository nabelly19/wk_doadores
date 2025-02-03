# WK Doadores
Aplicativo de teste técnico na WK Technology

## Introdução
O sistema **WK Doadores** foi desenvolvido para atender à necessidade de um banco de sangue que busca processar dados de candidatos a doadores e extrair informações relevantes.

A API foi construída utilizando **Java com Spring Boot**, a aplicação móvel foi implementada em **Flutter** e o banco de dados escolhido foi **MySQL**.

### Objetivo do Sistema
O principal objetivo deste sistema é armazenar e processar os dados enviados pelo dispositivo móvel, respondendo a consultas que incluem:
- Quantidade de candidatos por estado do Brasil.
- IMC médio por faixa etária.
- Percentual de obesos entre homens e mulheres.
- Média de idade para cada tipo sanguíneo.
- Número de doadores possíveis para cada tipo sanguíneo receptor.

**Critérios para doação:**
- Idade entre **16 e 69 anos**.
- Peso superior a **50 kg**.

---

## Arquitetura
O projeto segue uma arquitetura baseada em camadas:

### 1. Camada de Controle (Controller)
**Arquivo:** `CandidateController.java`
- Responsável por expor os endpoints da API.
- Implementa endpoints para processar dados e calcular estatísticas de doadores.

#### Principais Endpoints
| Método | Endpoint | Descrição |
|--------|-------------------------------|--------------------------------|
| GET | `/api/candidates/count-by-state` | Quantidade de candidatos por estado |
| GET | `/api/candidates/average-bmi-by-age-group` | IMC médio por faixa etária |
| GET | `/api/candidates/obesity-percentage` | Percentual de obesidade |
| GET | `/api/candidates/average-age-by-blood-type` | Média de idade por tipo sanguíneo |
| GET | `/api/candidates/eligible-donors` | Quantidade de doadores aptos |
| GET | `/api/candidates/donors-per-recipient` | Quantidade de doadores por receptor |
| POST | `/api/candidates/load-data` | Carga dos candidatos na base |

### 2. Camada de Serviço (Service)
**Arquivo:** `CandidateService.java`
- Implementa a lógica de processamento dos dados.
- Conta doadores por estado, calcula IMC médio, percentual de obesidade e compatibilidade sanguínea.

### 3. Camada de Persistência (Repository)
**Arquivo:** `CandidateRepository.java`
- Usa **Spring Data JPA** para interagir com o banco **MySQL**.
- Permite persistir, recuperar e manipular os candidatos no banco.

### 4. Modelo de Dados (Entity)
**Arquivo:** `Candidate.java`
- Representa os candidatos no banco de dados.
- Usa **JPA** para mapeamento e **Jackson** para conversão JSON.

---

## Implementação
O backend foi desenvolvido utilizando **Spring Boot** e expõe uma API REST que processa os dados no **MySQL**.

**Swagger:**
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- JSON OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

**Dependências principais:** (arquivo `pom.xml`)
- Spring Boot (starter-web, starter-data-jpa, starter-validation)
- MySQL Connector
- SpringDoc OpenAPI
- JUnit 5 + Mockito

### Frontend
As telas foram desenvolvidas em **Flutter** e incluem:

# WK Doadores

## Introdução
O sistema **WK Doadores** foi desenvolvido para atender à necessidade de um banco de sangue que busca processar dados de candidatos a doadores e extrair informações relevantes.

A API foi construída utilizando **Java com Spring Boot**, a aplicação móvel foi implementada em **Flutter** e o banco de dados escolhido foi **MySQL**.

### Objetivo do Sistema
O principal objetivo deste sistema é armazenar e processar os dados enviados pelo dispositivo móvel, respondendo a consultas que incluem:
- Quantidade de candidatos por estado do Brasil.
- IMC médio por faixa etária.
- Percentual de obesos entre homens e mulheres.
- Média de idade para cada tipo sanguíneo.
- Número de doadores possíveis para cada tipo sanguíneo receptor.

**Critérios para doação:**
- Idade entre **16 e 69 anos**.
- Peso superior a **50 kg**.

---

## Arquitetura
O projeto segue uma arquitetura baseada em camadas:

### 1. Camada de Controle (Controller)
**Arquivo:** `CandidateController.java`
- Responsável por expor os endpoints da API.
- Implementa endpoints para processar dados e calcular estatísticas de doadores.

#### Principais Endpoints
| Método | Endpoint | Descrição |
|--------|-------------------------------|--------------------------------|
| GET | `/api/candidates/count-by-state` | Quantidade de candidatos por estado |
| GET | `/api/candidates/average-bmi-by-age-group` | IMC médio por faixa etária |
| GET | `/api/candidates/obesity-percentage` | Percentual de obesidade |
| GET | `/api/candidates/average-age-by-blood-type` | Média de idade por tipo sanguíneo |
| GET | `/api/candidates/eligible-donors` | Quantidade de doadores aptos |
| GET | `/api/candidates/donors-per-recipient` | Quantidade de doadores por receptor |
| POST | `/api/candidates/load-data` | Carga dos candidatos na base |

### 2. Camada de Serviço (Service)
**Arquivo:** `CandidateService.java`
- Implementa a lógica de processamento dos dados.
- Conta doadores por estado, calcula IMC médio, percentual de obesidade e compatibilidade sanguínea.

### 3. Camada de Persistência (Repository)
**Arquivo:** `CandidateRepository.java`
- Usa **Spring Data JPA** para interagir com o banco **MySQL**.
- Permite persistir, recuperar e manipular os candidatos no banco.

### 4. Modelo de Dados (Entity)
**Arquivo:** `Candidate.java`
- Representa os candidatos no banco de dados.
- Usa **JPA** para mapeamento e **Jackson** para conversão JSON.

---

## Implementação
O backend foi desenvolvido utilizando **Spring Boot** e expõe uma API REST que processa os dados no **MySQL**.

**Swagger:**
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- JSON OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

**Dependências principais:** (arquivo `pom.xml`)
- Spring Boot (starter-web, starter-data-jpa, starter-validation)
- MySQL Connector
- SpringDoc OpenAPI
- JUnit 5 + Mockito

### Frontend
As telas foram desenvolvidas em **Flutter** e incluem:

1. **Tela de splash** – Identificação do APP.  
   <img src="screens/splash_sreen.png" alt="Tela de splash" width="300">

2. **Tela de conexão API** – Verifica a disponibilidade da API.  
   <img src="screens/connecting_screen.png" alt="Tela de conexão API" width="300">

3. **Tela de indisponível** – Indica servidor offline.  
   <img src="screens/indisponible_screen.png" alt="Tela de indisponível" width="300">

4. **Tela de carga de dados** – Permite acesso a pastas do dispositivo.  
   <img src="screens/load_data_screen.png" alt="Tela de carga de dados" width="300">

5. **Tela de seleção do JSON** – Faz upload do arquivo JSON.  
   <img src="screens/data_screen.png" alt="Tela de seleção do JSON" width="300">

6. **Tela do relatório** – Exibe informações solicitadas pelo usuário.  
   <img src="screens/report_screen.png" alt="Tela do relatório" width="300">

---

---

## Configuração

### Repositório e Arquivos
O código está versionado no **GitHub** em um repositório privado. Para acesso, entre em contato com o desenvolvedor.

**Estrutura do projeto:**
- `app/` – Código do frontend (Flutter).
- `back/` – Código das APIs (Spring Boot).

### Backend
A conexão com o banco está no arquivo:
```plaintext
src/main/resources/application.properties
```

### Frontend
A configuração da conexão com a API está no arquivo:
```plaintext
.env
```

Textos da UI podem ser alterados no arquivo:
```plaintext
assets/lang/pt-BR.json
```

---

## Testes
Foram implementados testes unitários para validar as funções da classe **CandidateService**, incluindo:
- Cálculo de IMC médio por faixa etária.
- Contagem de candidatos por estado.
- Cálculo do percentual de obesidade.
- Verificação da média de idade por tipo sanguíneo.
- Determinação de doadores aptos.
- Validação da compatibilidade sanguínea.

Para conferência dos dados, foi criado o arquivo:
```plaintext
queries_to_validate.sql
```
Ele permite extrair valores da base para validação com os dados exibidos na interface.

---

## Contato
Dúvidas ou contribuições? Entre em contato com o desenvolvedor.

