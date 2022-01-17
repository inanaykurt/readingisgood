# Getting Started

### Application can be started with below commands

docker build -t readingisgood-0.0.1 .

docker run -p 8080:8080 readingisgood-0.0.1

### Api documentation

http://localhost:8080/swagger-ui.html

### Tech Stack
Spring Boot, Java SDK 11, Docker, Swagger, H2 DB

### Brief Design
Customer are created to order books from retail application.
Books are added and updated to stock system.
Customer orders books from store.
Statistics can be queried for orders of customers.
Postman collection for sample requests are inside resource/docs.
