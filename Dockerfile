
​DOCKERFILE - Construção em Múltiplos Estágios para Aplicação Spring Boot (Java 21)
​
​################################################################################
​1. ESTÁGIO DE BUILD: Compila o projeto usando Maven e JDK 21
​################################################################################
​Usa a imagem oficial do Maven com JDK 21. Nomeamos este estágio como 'builder'.
​FROM maven:3.9.5-eclipse-temurin-21 AS builder
​Define o diretório de trabalho no container
​WORKDIR /app
​Copia o arquivo de configuração de dependências (pom.xml)
​Esta cópia separada otimiza o cache do Docker
​COPY pom.xml .
​Baixa as dependências. Se o pom.xml não mudar, esta etapa é ignorada (cache).
​RUN echo "Baixando dependências do Maven..." && 
mvn dependency:go-offline -B
​Copia todo o código-fonte restante
​COPY src ./src
​Executa o build da aplicação, gerando o arquivo JAR
​RUN echo "Compilando e empacotando o JAR..." && 
mvn package -DskipTests
​################################################################################
​2. ESTÁGIO DE EXECUÇÃO: Imagem final leve para rodar a aplicação
​################################################################################
​Usa uma imagem JRE 21 (Runtime Environment) leve e otimizada (Alpine)
​FROM eclipse-temurin:21-jre-alpine
​Define o diretório de trabalho da aplicação
​WORKDIR /app
​Copia o arquivo JAR compilado do estágio 'builder' para a imagem final
​COPY --from=builder /app/target/*.jar app.jar
​Expõe a porta que o Spring Boot usa (definida em seu application.properties ou padrão)
​EXPOSE 8082
​Comando de entrada: roda a aplicação
​ENTRYPOINT ["java", "-jar", "app.jar"]
