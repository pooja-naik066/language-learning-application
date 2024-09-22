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
<h5>REST API Endpoints</h5> <br>
POST <code>users/register</code> <br>
Description : Register a user<br>
<code>{
  "username": "string",
  "password": "string"
}
</code>
<hr>
POST <code>/questions</code> <br>
Description : Create a question 
Authentication : user
<code>{
  "question": "string",
  "firstOption": "string",
  "secondOption": "string",
  "answer": "string",
  "difficultyLevel": "string",
  "language": "string"
}
</code>
<hr>
PUT <code>questions/{questionId}</code> <br>
Description : Update an existing question <br> 
Authentication : user <br>
<code>{
  "question": "string",
  "firstOption": "string",
  "secondOption": "string",
  "answer": "string",
  "difficultyLevel": "string",
  "language": "string"
}
</code>
<hr>
GET <code>/questions/{questionId}</code><br>
Description : Retrieve the question with the given id<br>
Authentication : user <br>
<hr>
<GET><code>questions/</code></GET> <br>
Description : Retrieve all questions<br>
Authentication : user <br>
DELETE <code>questions/{questionId}</code> <br>
Description : Delete a question <br>
Authentication : user <br>
<hr>
GET <code>questions/generate</code>
<h6>Query</h6> [difficultyLevel, language, numOfQuestions]<br>
<hr>
POST <code>questions/getQuestions</code><br>

Description : Retrieve the generated questions<br>

Request body <br>

List [questionIds] <br>
<hr>
POST <code>questions/score</code><br>
Description : Calculate the score based on response<br>
<code>[
  {
    "questionId": 0,
    "response": "string"
  }
]</code>
<hr>




### Quiz Service
The Quiz Service manages the creation, submission, and scoring of quizzes. It interacts with the Question Service to fetch questions for quizzes.

Technologies: Spring Boot, MySQL, Spring Data JPA
