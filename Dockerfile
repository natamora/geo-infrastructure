# frontend
FROM node:24-alpine AS frontend-builder
WORKDIR /gisapp/frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ ./
RUN npm run build


# backend
FROM eclipse-temurin:21-jdk-alpine AS backend-builder
WORKDIR /gisapp

COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src/ ./src/

# front -> spring boot
COPY --from=frontend-builder /gisapp/frontend/dist ./src/main/resources/static/

# build jar with frontend and backend
RUN ./mvnw clean package -DskipTests


# light image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /gisapp

COPY --from=backend-builder /gisapp/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]