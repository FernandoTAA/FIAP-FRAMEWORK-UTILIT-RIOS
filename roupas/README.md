# FRAMEWORKs UTILITÁRIOS

## 	FIAP Roupas

#### Configuração do MongoDB e ActiveMQ

Para configurar o MongoDB e o ActiveMQ, alterar o arquivo localizado em: `/src/main/resources/application.properties`

#### Build

O projeto FIAP Roupas está utilizado como gerenciador de dependências e build, o MAVEN.
Sendo assim, a partir do comando MAVEN, especificado a baixo, é possível construir o JAR para a execução da aplicação.

```sh
mvn clean install
```

#### Execução

Esse é um projeto Spring Boot.
Sendo assim, a partir do comando a baixo, é possível iniciar o projeto.

```sh
java -jar roupas-0.0.1-SNAPSHOT.jar
```

### Postman

Para facilitar a execução dos teste, foi criado um arquivo, especificado a baixo, para ser importado no Postman com todos os enpoints disponível na API.

`/invoice.postman_collection.json`

Obs: O Postman foi configurado tendo como host `localhost`. Se não utilizar o `localhost` como host, alterar o arquivo antes da execução.

### Stress test

O stress test foi feito em em JMETER.
O arquivo a baixo tem o teste para ser aberto no JMETER.

`/stress-test.jmx`

Obs: O stress test foi realizado tendo como host `localhost`. Se não utilizar o `localhost` como host, alterar o arquivo antes da execução.
