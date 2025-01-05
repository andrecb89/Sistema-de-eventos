# Estágio de build: construa o .jar
FROM maven:3.8-openjdk-17-slim as builder

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o código-fonte e o arquivo pom.xml para o container
COPY . .

# Instalar dependências do Maven e compilar o código, gerando o .jar
RUN mvn clean package -DskipTests

# Estágio de execução: use uma imagem mais enxuta para rodar o app
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho no container
WORKDIR /app

# Copie o .jar gerado no estágio anterior para o diretório do container
COPY --from=builder /app/target/*.jar app.jar

# Defina o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
