Locadora de Veículos
Este projeto é uma API RESTful para uma locadora de veículos, construída com Quarkus, Kafka e PostgreSQL.

Estrutura do Projeto
com.locadora.api: Contém os endpoints REST.
com.locadora.model: Entidades do banco de dados.
com.locadora.kafka: Comunicação com Kafka (Produtor e Consumidor).
com.locadora.repository: Repositórios para persistência.
com.locadora.service: Lógica de negócio.
Dependências
Quarkus RESTEasy
Quarkus RESTEasy Jackson
Quarkus Hibernate ORM Panache
Quarkus JDBC PostgreSQL
Quarkus Smallrye Reactive Messaging Kafka
Quarkus Arc
Quarkus OpenAPI
Quarkus JUnit5 (testes)
Rest Assured (testes)
Configurações
application.properties
properties
Copiar
Editar
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
Endpoints
1. Criar Categoria
Permite criar uma nova categoria para vincular aos veículos.

URL: /categorias
Método: POST
Corpo da Requisição:

json
Copiar
Editar
{
  "nome": "Carros Urbanos"
}
Resposta (Sucesso - 201):

json
Copiar
Editar
{
  "id": 1,
  "nome": "Carros Urbanos"
}
2. Listar Categorias
Lista todas as categorias disponíveis.

URL: /categorias
Método: GET
Resposta:

json
Copiar
Editar
[
  {
    "id": 1,
    "nome": "Carros Urbanos"
  },
  {
    "id": 2,
    "nome": "Carros Premium"
  }
]
3. Criar Veículo
Permite criar um novo veículo vinculado a uma categoria existente.

URL: /veiculos
Método: POST
Corpo da Requisição:

json
Copiar
Editar
{
  "modelo": "Onix",
  "categoria": {
    "id": 1
  }
}
Resposta (Sucesso - 201):

json
Copiar
Editar
{
  "id": 1,
  "modelo": "Onix",
  "categoria": {
    "id": 1,
    "nome": "Carros Urbanos"
  }
}
4. Obter Veículo por ID
Permite consultar um veículo específico pelo seu identificador.

URL: /veiculos/{id}
Método: GET
Resposta (Sucesso - 200):

json
Copiar
Editar
{
  "id": 1,
  "modelo": "Onix",
  "categoria": {
    "id": 1,
    "nome": "Carros Urbanos"
  }
}
Erro (Não Encontrado - 404):

json
Copiar
Editar
{
  "message": "Veículo não encontrado com o ID: {id}"
}
5. Listar Todos os Veículos
Lista todos os veículos cadastrados na aplicação.

URL: /veiculos
Método: GET
Resposta (Sucesso - 200):

json
Copiar
Editar
[
  {
    "id": 1,
    "modelo": "Onix",
    "categoria": {
      "id": 1,
      "nome": "Carros Urbanos"
    }
  },
  {
    "id": 2,
    "modelo": "Corolla",
    "categoria": {
      "id": 2,
      "nome": "Carros Premium"
    }
  }
]
6. Listar Veículos por Categoria
Lista todos os veículos associados a uma categoria específica.

URL: /veiculos/categoria/{categoriaNome}
Método: GET
Resposta (Sucesso - 200):

json
Copiar
Editar
[
  {
    "id": 1,
    "modelo": "Onix",
    "categoria": {
      "id": 1,
      "nome": "Carros Urbanos"
    }
  }
]
Erro (Nenhum Veículo Encontrado - 204): Retorna sem corpo.

Desenvolvimento Futuro
Implementar sistema de locação de veículos.
Melhorar a integração com Kafka para comunicação de eventos entre módulos.
Adicionar autenticação e autorização usando JWT.
Rodando a Aplicação
Para rodar a aplicação em modo de desenvolvimento, utilize o seguinte comando:

bash
Copiar
Editar
mvn compile quarkus:dev
Testando a API
Usando cURL
Criar Categoria
bash
Copiar
Editar
curl -X POST "http://localhost:8080/categorias" \
     -H "Content-Type: application/json" \
     -d '{"nome": "Carros Urbanos"}'
Criar Veículo
bash
Copiar
Editar
curl -X POST "http://localhost:8080/veiculos" \
     -H "Content-Type: application/json" \
     -d '{
           "modelo": "Onix",
           "categoria": {
             "id": 1
           }
         }'
Listar Categorias
bash
Copiar
Editar
curl -X GET "http://localhost:8080/categorias"
Listar Veículos
bash
Copiar
Editar
curl -X GET "http://localhost:8080/veiculos"
