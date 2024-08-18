 A scalable backend application using microservices architecture designed to offer interactive vocabulary challenges for language learners.
The Language Learning Application is built using microservices to provide a scalable and maintainable solution. Each microservice handles a specific domain within the application, allowing independent development, testing, and deployment.

### Service Registry

The Service Registry, based on Eureka, allows microservices to register themselves and discover each other. This is essential in a microservices architecture, where services need to communicate with each other dynamically.

- **Technologies**: Spring Cloud Netflix Eureka

### API Gateway
The API Gateway handles all incoming requests and routes them to the appropriate microservice. It also provides cross-cutting concerns such as security and logging.

Technologies: Spring Cloud Gateway

### Question Service
The Question Service is responsible for managing the vocabulary questions within the application. It provides CRUD operations for questions and interacts with the Quiz Service.

Technologies: Spring Boot, MySQL, Spring Data JPA

### Quiz Service
The Quiz Service manages the creation, submission, and scoring of quizzes. It interacts with the Question Service to fetch questions for quizzes.

Technologies: Spring Boot, MySQL, Spring Data JPA
