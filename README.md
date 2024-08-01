# VR - Mini Autorizador

API em Java com rotas para gerenciamento de cartões e transações.

# Tecnologias

- Java 17
- Gradle 7.6.3
- Spring 3.3.2
- MySQL 5.7 + Flyway

# Preparação do Ambiente

## Recursos

**PS:** Os comandos abaixo precisam ser executados dentro da pasta `docker`.

### Docker

Para baixar e subir os recursos necessários em sua máquina, execute o comando:

```shell
docker-compose up -d
```

Para validar o funcionamento dos recursos, execute o comando:

```shell
docker ps
```

Para parar qualquer um dos recursos, execute o comando:

```shell
docker stop {container_id}
```

## Aplicação

**PS:** Os comandos abaixo precisam ser executados na pasta raiz do projeto.

### Compilação

Para baixar as dependências e compilar o projeto, execute o comando:

```shell
./gradlew clean build -x test
```

### Testes

Para rodar os testes do projeto, execute o comando:

```shell
./gradlew clean test
```

Para obter o resultado da cobertura dos testes (localizado na pasta [jacoco](./build/jacoco)), execute o comando:

```shell
./gradlew clean jacocoRootReport
```

### Execução

Para rodar o projeto via terminal, execute o comando:

```shell
./gradlew clean bootRun
```

Se der certo, serão aplicadas as migrations no banco de dados (caso necessário) e aparecerá a seguinte
mensagem: `Started StartApplication in {time} seconds`. Por padrão, a aplicação
utiliza a porta 8080 (setado no arquivo de configurações).

# Endpoints

Com a aplicação rodando, basta acessar a seguinte URL para visualizar os endpoints disponíveis e os respectivos
contratos de requisição/resposta: http://localhost:8080/swagger-ui/index.html

A aplicação possui autenticação JWT (desabilitada por padrão), onde na qual as validações são feitas por meio do token
enviado através do
header `Authorization`. Se caso a validação falhar, será retornado status 401 ao solicitante.

Este token pode ser gerado no site https://jwt.io utilizando a secret-key setada nas envs da
aplicação ou obtido através do link do Swagger informado anteriormente (disponível apenas no ambiente local).

## Actuator Health

Exemplo de cURL:

```shell
curl -X 'GET' 'http://localhost:8080/actuator/health'
```

Este endpoint verifica a saúde da aplicação e de seus recursos, sendo o único endpoint que não exige autenticação.

## Criação de Cartão

Exemplo de cURL:

```shell
curl -X 'POST' 'http://localhost:8080/cartoes' \
  -H 'Authorization: Bearer xpto' \
  -H 'Content-Type: application/json' \
  -d '{
    "numeroCartao": "1234512345",
    "senha": "1234",
    "cvv": "001",
    "dataExpiracao": "01/21",
    "nomeDono": "Maria da Silva",
    "saldo": 700
}'
```

Este endpoint tenta criar um novo cartão com os dados informados, sendo que apenas o número do cartão e a senha são
obrigatórios. Caso o saldo não venha preenchido, será setado o valor padrão de
500 reais no cartão. Se a requisição der sucesso, será retornado o status 201, mas caso o cartão já exista, será
retornado o
status 422.

## Obter Saldo do Cartão

Exemplo de cURL:

```shell
curl -X 'GET' 'http://localhost:8080/cartoes/1234512345' -H 'Authorization: Bearer xpto'
```

Este endpoint realiza a busca de saldo do cartão informado na URL. Caso o cartão exista, será retornado o status 200
junto com o
saldo, caso não exista, será retornado o status 404 com o corpo vazio.

## Criação de Transação

Exemplo de cURL:

```shell
curl -X 'POST' 'http://localhost:8080/transacoes' \
  -H 'Authorization: Bearer xpto' \
  -H 'Content-Type: application/json' \
  -d '{
    "numeroCartao": "1234512345",
    "senhaCartao": "1234",
    "valor": 12.00
}'
```

Este endpoint realiza uma transação baseada nos dados informados. Se der sucesso, será retornado status 200 com a
mensagem `OK`, se der algum erro, será retornado status 422 com algumas destas
mensagens: `SALDO_INSUFICIENTE`, `SENHA_INVALIDA`, `CARTAO_INEXISTENTE` e `ERRO_INESPERADO`.

# Arquitetura

Este projeto utiliza a arquitetura `Hexagonal` (também nomeada como **Ports and Adapters**). Essa arquitetura é
conhecida
por organizar o
código em camadas independentes para facilitar a manutenção, testabilidade e flexibilidade do sistema. Este projeto foi
separado em 3 camadas, sendo elas:

A camada `domain`: que contém as entidades (que são trafegadas entre as demais camadas), os agregados, os objetos de
valor e as interfaces das portas de
entrada/saída da aplicação.

A camada `application`: contendo as regras de negócio da aplicação por meio de casos de uso (adaptadores das portas de
entrada);

A camada `infrastructure`: contendo classes que realizam a comunicação de fora pra dentro (inbound) e de dentro pra
fora (outbound, ou seja, os adaptadores das portas de saída), também responsável por inicializar a aplicação e tratar
de todos os detalhes do framework utilizado.

# Créditos

Desenvolvido por Kenneth Gottschalk de Azevedo.
