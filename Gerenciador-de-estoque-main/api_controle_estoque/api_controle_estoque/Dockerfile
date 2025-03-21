# Usa a imagem do Maven com JDK 21 para compilar a aplicação
FROM maven:3.9.7-amazoncorretto-17 AS build

WORKDIR /app

# Copia todos os arquivos necessários, incluindo Maven Wrapper
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .
COPY src ./src

# (Opcional) Verifica a versão do Java
RUN java -version

# Executa a build
RUN mvn clean package -DskipTests

# Usa uma imagem mais leve para rodar a aplicação com JDK 21
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# Copia o JAR gerado na fase de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar o Spring Boot
CMD ["java", "-jar", "app.jar"]
