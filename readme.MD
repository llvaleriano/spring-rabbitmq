#Exemplo de aplicação usando Spring e RabbitMQ

## 1. Subir o RabbitMQ via docker

No diretório `src/main/docker/` existe um arquivo chamado `rabbitmq.yml`.
Inicie o container a partir desse arquivo utilizando o comando abaixo:

`docker-compose -f src/main/docker/rabbitmq.yml up -d`

## 2. Estrutura do projeto

O projeto é caso muito simples, apenas para exemplificar o uso das ferramentas de forma integrada.

### 2.1. Configuração da fila no projeto
A classe `br.com.leovaleriano.springrabbitmq.config.RabbitmqConfig` define as configurações do rabbitmq dentro da aplicação.
Nela é criada uma fila não durável que será usada no restante da aplicação.

Outras configurações são feitas de forma automática através do arquivo de configuração `application.yml`.

### 2.2. Rest

O ponto de entrada da aplicação é um webservice rest que recebe uma requisição via POST. 
Os dados são passados através de um arquivo Json que é transformado em um objeto java representado pela classe 
`br.com.leovaleriano.springrabbitmq.domain.Mensagem`.

O objeto mensagem é passado para a camada de serviço onde a mensagem será enviada para a fila.

### 2.3. Serviço - Produtor da mensagem

A camada de serviço - `br.com.leovaleriano.springrabbitmq.service.LogService` - é responsável por produzir a mensagem 
que será enviada para a fila do RabbitMQ. Para isso, a classe foi anotada como um serviço `@Service` e injetamos um
objeto do tipo `RabbitTemplate`.  O nome da fila para onde enviaremos a mensagem também é definido a partir da leitura
do arquivo de configuração `application.yml`.

### 2.4. Consumidor da mensagem

A classe `br.com.leovaleriano.springrabbitmq.service.LogConsumer` é responsável por consumir as mensagens enviadas para
a fila. Nela temos um método anotado com `@RabbitListener` que irá receber a mensagem e fazer o seu processamento.

## 3. Executar a aplicação
Com o docker do rabbitmq iniciado (ver item 1), inicie a' aplicação via maven:

```mvn spring-boot:run```

Obs.: O projeto foi definiu que será usado java 11, portanto certifique-se que vc tem um JDK 11 configurado no seu 
ambiente através da variável de ambiente JAVA_HOME.

Envie uma requisição via curl:

``` 
curl --header "Content-Type: application/json"   --request POST   --data '{"texto":"exemplo de envio de mensagem via rest"}'   http://localhost:8080/api/logs
```

Observe que no log da aplicação aparecerá as informações indicando que a mensagem foi recebida e consumida:

```
 b.c.l.s.web.rest.LogController           : Enviando mensagem para a fila
 b.c.l.s.service.LogConsumer              : Consumindo a mensagem: exemplo de envio de mensagem via rest
```

