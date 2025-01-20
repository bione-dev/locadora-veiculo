# Locadora de Veículos

Este projeto é uma API RESTful para uma locadora de veículos, construída com Quarkus, Kafka e PostgreSQL.

## Estrutura do Projeto

- **com.locadora.api**: Contém os endpoints REST.
- **com.locadora.model**: Entidades do banco de dados.
- **com.locadora.kafka**: Comunicação com Kafka (Produtor e Consumidor).
- **com.locadora.repository**: Repositórios para persistência.
- **com.locadora.service**: Lógica de negócio.

## Dependências

- Quarkus RESTEasy
- Quarkus RESTEasy Jackson
- Quarkus Hibernate ORM Panache
- Quarkus JDBC PostgreSQL
- Quarkus Smallrye Reactive Messaging Kafka
- Quarkus Arc
- Quarkus JUnit5 (testes)
- Rest Assured (testes)

## Configurações

### `application.properties`

```properties
# Configuração do Kafka
kafka.bootstrap.servers=localhost:9092
mp.messaging.incoming.topic-in.connector=smallrye-kafka
mp.messaging.incoming.topic-in.topic=meu-topico
mp.messaging.outgoing.topic-out.connector=smallrye-kafka
mp.messaging.outgoing.topic-out.topic=meu-topico

# Configuração do Banco de Dados
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=meu-usuario
quarkus.datasource.password=minha-senha
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/meu-banco
quarkus.hibernate-orm.database.generation=update
```

## Endpoints

### Criar Veículo

Este endpoint faz parte das funcionalidades básicas e permite o cadastro de novos veículos. Novos endpoints relacionados à locação de veículos serão implementados futuramente.

- **URL**: `/veiculos`
- **Método**: `POST`

**Corpo da Requisição:**
```json
{
  "modelo": "Fusca"
}
```

**Resposta:**
```json
{
  "id": 1,
  "modelo": "Fusca"
}
```

### Obter Veículo por ID

Este endpoint permite consultar um veículo específico pelo seu identificador. Faz parte do módulo inicial de consulta e funcionalidades futuras incluirão mais recursos relacionados à locação de veículos.

- **URL**: `/veiculos/{id}`
- **Método**: `GET`

**Resposta:**
```json
{
  "id": 1,
  "modelo": "Fusca"
}
```

### Listar Todos os Veículos

Permite listar todos os veículos cadastrados na aplicação.

- **URL**: `/veiculos`
- **Método**: `GET`

**Resposta:**
```json
[
  {
    "id": 1,
    "modelo": "Fusca"
  },
  {
    "id": 2,
    "modelo": "Corolla"
  }
]
```

## Desenvolvimento Futuro

Atualmente, o projeto inclui funcionalidades básicas de cadastro e consulta de veículos. No futuro, será implementado um sistema completo de locação, utilizando Kafka para comunicação de eventos entre módulos, garantindo escalabilidade e alta disponibilidade.

## Rodando a Aplicação

Para rodar a aplicação em modo de desenvolvimento, utilize o seguinte comando:

```bash
mvn compile quarkus:dev
```

## Testando a API

### Usando cURL

#### Criar Veículo:
```bash
curl -X POST "http://localhost:8080/veiculos" -H "Content-Type: application/json" -d '{"modelo":"Fusca"}'
```

#### Obter Veículo por ID:
```bash
curl -X GET "http://localhost:8080/veiculos/1"
```

#### Listar Todos os Veículos:
```bash
curl -X GET "http://localhost:8080/veiculos"
```

### Usando Postman

#### Criar Veículo:
- **Método**: `POST`
- **URL**: `http://localhost:8080/veiculos`
- **Headers**: `Content-Type: application/json`
- **Body**: `{ "modelo": "Fusca" }`

#### Obter Veículo por ID:
- **Método**: `GET`
- **URL**: `http://localhost:8080/veiculos/1`

#### Listar Todos os Veículos:
- **Método**: `GET`
- **URL**: `http://localhost:8080/veiculos`

## Licença

Este projeto é licenciado sob os termos da [licença MIT](LICENSE).
