# CadUsu

<p>Aplicação web para cadastro de usuários desenvolvido para o desafio técnico da Softplan</p>

## Instruções para execução

### 1 - Para buildar e rodar os testes do backend:
<pre>cd cadusu</pre>
<pre>mvn clean install</pre>

Obs.: Caso queira apenas gerar o jar para o próximo passo, execute:
<pre>mvn package -DskipTests</pre>

### 2 - Instale o docker engine e docker compose 

### 3 - Execute o seguinte comando no diretório raiz deste projeto:
<pre>sudo docker-compose up --build -d</pre>

Os endereços para acessar a aplicação serão os seguintes:

* API: http://localhost:8081
* Swagger: http://localhost:8081/swagger-ui
* Front: http://localhost:8080

O usuário padrão para acesso à API de usuarios (Basic Auth):

usuário: usuariopf / senha: usu%45$RA

