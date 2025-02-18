# Blackjack API with Spring Boot

## Project Overview
This project involves developing a **Blackjack game API** using **Java and Spring Boot**. The API is designed to connect and manage data in two different databases: **MongoDB** and **MySQL**. The Blackjack game will include all the necessary features, such as player management, card handling, and game rules.

The application must be thoroughly tested and documented, using **README.md, Swagger,** and other documentation tools.

---

## Level 1: Basic Implementation
### Reactive Application Development
The project is developed as a **purely reactive application** using **Spring WebFlux**. This involves choosing a reactive approach, configuring **reactive MongoDB**, and implementing reactive controllers and services.

### Global Exception Handling
A **GlobalExceptionHandler** is implemented to manage exceptions globally within the application.

### Database Configuration
The application is configured to use **two database schemas**:
- **MySQL** for relational data storage
- **MongoDB** for non-relational data storage

### Controller and Service Testing
At least one controller and one service must be tested using **JUnit and Mockito** to ensure functionality.

### API Documentation with Swagger
Swagger is used to generate automatic documentation for the API.

---

## Steps to Follow
1. **API Design:** Define the different endpoints needed to manage a Blackjack game, such as creating games, making plays, etc.
2. **Database Connection:** Configure the connection to MongoDB and MySQL. Create the necessary Java entities to represent game data.
3. **Unit Testing:** Write unit tests for each endpoint and major API functions using **JUnit and Mockito**. Ensure correct API functionality and database operations.

---

## API Endpoints

### Create a New Game
- **Method:** `POST`
- **Endpoint:** `/game/new`
- **Description:** Creates a new Blackjack game.
- **Request Body:** New player name.
- **Response:** `201 Created` with game information.

### Get Game Details
- **Method:** `GET`
- **Endpoint:** `/game/{id}`
- **Description:** Retrieves details of a specific Blackjack game.
- **Request Parameters:** Game ID (`id`).
- **Response:** `200 OK` with game details.

### Make a Move
- **Method:** `POST`
- **Endpoint:** `/game/{id}/play`
- **Description:** Makes a move in an existing Blackjack game.
- **Request Parameters:** Game ID (`id`), move details (type of move, bet amount).
- **Response:** `200 OK` with move results and current game status.

### Delete a Game
- **Method:** `DELETE`
- **Endpoint:** `/game/{id}/delete`
- **Description:** Deletes an existing Blackjack game.
- **Request Parameters:** Game ID (`id`).
- **Response:** `204 No Content` if successfully deleted.

### Get Player Ranking
- **Method:** `GET`
- **Endpoint:** `/ranking`
- **Description:** Retrieves the player ranking based on performance in Blackjack games.
- **Response:** `200 OK` with the ordered player list and scores.

### Change Player Name
- **Method:** `PUT`
- **Endpoint:** `/player/{playerId}`
- **Description:** Changes the name of a player in an existing game.
- **Request Parameters:** Player ID (`playerId`).
- **Request Body:** New player name.
- **Response:** `200 OK` with updated player information.

---

## Objectives
- Learn to program a **reactive API in Java** using **Spring Boot**, with connections to both **relational (MySQL)** and **non-relational (MongoDB)** databases.
- Apply **documentation techniques** and **unit testing implementation**.
- Gain knowledge in **Dockerizing a Spring Boot application** and deploying it to a web server.

---

## Technologies Used
- **Java & Spring Boot** (Reactive programming with WebFlux)
- **MongoDB & MySQL** (Database management)
- **Spring Data & JPA** (Database interaction)
- **JUnit & Mockito** (Testing)
- **Swagger** (API documentation)
- **Docker** (Containerization)

---

## Installation and Setup
1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-repo/blackjack-api.git
   cd blackjack-api
   ```
2. **Configure the databases**:
   - Set up **MongoDB** and **MySQL**.
   - Update `application.properties` or `application.yml` with database credentials.
3. **Run the application**:
   ```sh
   mvn spring-boot:run
   ```
4. **Access the API documentation** at:
   ```
http://localhost:8080/swagger-ui.html
```

5. **Run tests**:
   ```sh
   mvn test
   ```
6. **Build and run Docker container**:
   ```sh
   docker build -t blackjack-api .
   docker run -p 8080:8080 blackjack-api
   ```

---

## License
This project is licensed under the MIT License - see the LICENSE file for details.


