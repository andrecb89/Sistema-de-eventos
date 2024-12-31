# Sistema de eventos

O sistema conta com dois microsserviços: Event e Ticket

A documentação de cada serviço está em:
Event: localhost:8080/swagger-ui/index.html
Ticket: localhost:8081/swagger-ui/index.html

Cada microsserviço conta com um banco de dados Mongodb:
Event: db_event
disponível na URI: mongodb://localhost:27017/db_event
Ticket: db_ticket
disponível na URI: mongodb://localhost:27017/db_ticket

Ambos tem como configuração:
usuário: admin
senha: admin
