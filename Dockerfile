FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY Clientes-0.0.1-SNAPSHOT.jar clientes.jar
EXPOSE 8082  # <--- CORRIGIDO PARA REFLETIR A PORTA DA APLICAÇÃO
ENTRYPOINT ["java", "-jar", "clientes.jar"]
