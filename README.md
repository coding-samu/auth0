# auth0
Spring Boot and Angular 19 Auth0 implementation

## Configuration

### Backend

#### Auth0

Firstly you need to create an Auth0 account and create a new application, if you have installed Auth0 CLI you can use the following command:

```bash
auth0 apps create --name "Auth0 Angular" --description "Spring Boot + Angular" --type regular --callbacks http://localhost:8080/login/oauth2/code/okta,http://localhost:4200/login/oauth2/code/okta --logout-urls http://localhost:8080,http://localhost:4200 --reveal-secrets
```

You need to create an `.okta.env` file in the root of the project with the following content:

```properties
export OKTA_OAUTH2_ISSUER=https://<your-auth0-domain>/
export OKTA_OAUTH2_CLIENT_ID=<your-client-id>
export OKTA_OAUTH2_CLIENT_SECRET=<your-client-secret>
```

Or in windows you can create a `.okta.env.bat` file in the root of the project with the following content:

```properties
set OKTA_OAUTH2_ISSUER=https://<your-auth0-domain>/
set OKTA_OAUTH2_CLIENT_ID=<your-client-id>
set OKTA_OAUTH2_CLIENT_SECRET=<your-client-secret>
```

#### Database

You need to create a PostgreSQL database (also you can configure another database on your own), you can modify the `application.properties` file in the `src/main/resources` folder with the following content:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/DATABASE_NAME
spring.datasource.username=POSTGRES_USER
spring.datasource.password=POSTGRES_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA settings
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create
```

## Running the project

### Backend

You can run the following command:

```bash
source .okta.env
./mvnw spring-boot:run
```

Or in windows you can run the following command:

```bash
source .okta.env.bat
mvnw spring-boot:run
```

### Frontend

You can run the Angular CLI Server from your IDE using Intellij.


## Source

This project was created following the steps from the following article: [Build a Beautiful CRUD App with Spring Boot and Angular](https://auth0.com/blog/spring-boot-angular-crud/#Secure-Spring-Boot-with-OpenID-Connect-and-OAuth)
