# Microservices com Docker, MongoDB e RabbitMQ 🐋

Este é um projeto que demonstra a arquitetura de microservices usando **Java**, **Docker** (5 containers), **MongoDB** como banco de dados e **RabbitMQ** para mensageria. A aplicação é distribuída em cinco containers Docker que interagem entre si, oferecendo escalabilidade e eficiência.


O sistema conta com dois microsserviços: Event e Ticket<br>
<br>
URLs dos microsserviços na AWS:
**Event**: http://ec2-34-205-53-37.compute-1.amazonaws.com:8080<br>
**Ticket**: http://ec2-34-205-53-37.compute-1.amazonaws.com:8081<br>
<br>
A documentação de cada serviço está em:<br><br>
[![Event](https://img.shields.io/badge/Event-API-orange?style=flat&logo=swagger&logoColor=white)](http://ec2-34-205-53-37.compute-1.amazonaws.com:8080/swagger-ui/index.html)<br>
[![Ticket](https://img.shields.io/badge/Ticket-API-blue?style=flat&logo=swagger&logoColor=white)](http://ec2-34-205-53-37.compute-1.amazonaws.com:8081/swagger-ui/index.html)<br>
Na AWS estão cinco containers em uma instância:<br>
Microsserviço Event: **ms-event-manager**<br>
Microsserviço Ticket: **ms-ticket-manager**<br>
Banco de dados: **db_event**<br>
Banco de dados: **db_ticket**<br>
Mensageria: **rabbitmq**<br>
<br>
As Collections das requisições no Postman dos serviços estão na pasta principal do projeto.
<br>
A versão que foi enviada para a AWS está na branch 'DeployAWS' e a versão da main é para ser testada localmente sem containers.
Se for rodado localmente cada microsserviço conta com um banco de dados Mongodb:<br>
Event: db_event<br>
disponível na URI: mongodb://localhost:27017/db_event<br>
Ticket: db_ticket<br>
disponível na URI: mongodb://localhost:27017/db_ticket<br>
<br>
Ambos tem como configuração:<br>
usuário: admin<br>
senha: admin<br>
