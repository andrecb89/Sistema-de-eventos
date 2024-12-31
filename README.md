# Sistema de eventos

O sistema conta com dois microsserviços: Event e Ticket<br>
<br>
A documentação de cada serviço está em:<br>
Event: localhost:8080/swagger-ui/index.html<br>
Ticket: localhost:8081/swagger-ui/index.html<br>
<br>
Cada microsserviço conta com um banco de dados Mongodb:<br>
Event: db_event<br>
disponível na URI: mongodb://localhost:27017/db_event<br>
Ticket: db_ticket<br>
disponível na URI: mongodb://localhost:27017/db_ticket<br>
<br>
Ambos tem como configuração:<br>
usuário: admin<br>
senha: admin<br>
