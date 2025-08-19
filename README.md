# Spring Boot Microservices Demo

A comprehensive Spring Boot microservices project demonstrating service discovery, API gateway, circuit breaker pattern, and inter-service communication.

## Architecture

The project consists of four microservices:

1. **Eureka Service Registry** (Port: 8761)
   - Service discovery and registration
   - Central registry for all microservices

2. **Student Service** (Port: 8081)
   - CRUD operations for Students
   - Spring Data JPA with H2 database
   - RESTful API endpoints

3. **Course Service** (Port: 8082)
   - CRUD operations for Courses
   - Uses Feign Client to communicate with Student Service
   - Implements Circuit Breaker pattern with Resilience4j
   - Course enrollment functionality

4. **API Gateway** (Port: 8080)
   - Spring Cloud Gateway
   - Routes requests to appropriate services
   - Load balancing with Eureka

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Data JPA**
- **H2 Database**
- **Eureka Service Discovery**
- **Spring Cloud Gateway**
- **OpenFeign Client**
- **Resilience4j Circuit Breaker**

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Build the Project

```bash
mvn clean install
```

### 2. Start Services

Start the services in the following order:

#### Start Eureka Service Registry
```bash
cd eureka-service
mvn spring-boot:run
```
Access Eureka Dashboard: http://localhost:8761

#### Start Student Service
```bash
cd student-service
mvn spring-boot:run
```

#### Start Course Service
```bash
cd course-service
mvn spring-boot:run
```

#### Start API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

### 3. Access Services

- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **Student Service**: http://localhost:8081
- **Course Service**: http://localhost:8082
- **H2 Console (Student)**: http://localhost:8081/h2-console
- **H2 Console (Course)**: http://localhost:8082/h2-console

## API Documentation

### Student Service Endpoints

#### Via API Gateway (Recommended)
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students/email/{email}` - Get student by email
- `POST /api/students` - Create new student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student
- `GET /api/students/health` - Health check

#### Direct Access
- `GET http://localhost:8081/api/students` - Get all students
- `GET http://localhost:8081/api/students/{id}` - Get student by ID
- `POST http://localhost:8081/api/students` - Create new student
- `PUT http://localhost:8081/api/students/{id}` - Update student
- `DELETE http://localhost:8081/api/students/{id}` - Delete student

### Course Service Endpoints

#### Via API Gateway (Recommended)
- `GET /api/courses` - Get all courses
- `GET /api/courses/active` - Get active courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/{id}/with-students` - Get course with enrolled students
- `POST /api/courses` - Create new course
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course
- `POST /api/courses/{courseId}/enroll/{studentId}` - Enroll student in course
- `GET /api/courses/{courseId}/enrollments` - Get course enrollments
- `GET /api/courses/student/{studentId}/enrollments` - Get student enrollments
- `GET /api/courses/health` - Health check

#### Direct Access
- `GET http://localhost:8082/api/courses` - Get all courses
- `GET http://localhost:8082/api/courses/{id}` - Get course by ID
- `POST http://localhost:8082/api/courses` - Create new course
- `PUT http://localhost:8082/api/courses/{id}` - Update course
- `DELETE http://localhost:8082/api/courses/{id}` - Delete course

## Sample API Requests

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1995-05-15",
    "phoneNumber": "+1234567890",
    "address": "123 Main St, City, State"
  }'
```

### Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Microservices",
    "description": "Learn to build microservices with Spring Boot",
    "instructor": "Dr. Smith",
    "durationWeeks": 12,
    "price": 999.99,
    "maxStudents": 30,
    "startDate": "2024-01-15",
    "endDate": "2024-04-15"
  }'
```

### Enroll Student in Course
```bash
curl -X POST http://localhost:8080/api/courses/1/enroll/1
```

### Get Course with Students
```bash
curl -X GET http://localhost:8080/api/courses/1/with-students
```

## Circuit Breaker Configuration

The Course Service uses Resilience4j circuit breaker for calls to the Student Service:

- **Sliding Window Size**: 10 calls
- **Minimum Calls**: 5 calls before circuit breaker activates
- **Failure Rate Threshold**: 50%
- **Wait Duration**: 5 seconds in open state
- **Permitted Calls in Half-Open**: 3 calls

## Database Configuration

Both services use H2 in-memory databases:
- **Student Service**: `jdbc:h2:mem:studentdb`
- **Course Service**: `jdbc:h2:mem:coursedb`

Access H2 Console:
- Student Service: http://localhost:8081/h2-console
- Course Service: http://localhost:8082/h2-console

## Testing

### Using Postman

1. Import the following collection or create requests manually
2. Use the base URL: `http://localhost:8080` (API Gateway)

### Using Swagger/OpenAPI

The services expose REST APIs that can be tested using any REST client.

### Health Checks

- API Gateway: `GET http://localhost:8080/actuator/health`
- Student Service: `GET http://localhost:8081/api/students/health`
- Course Service: `GET http://localhost:8082/api/courses/health`

## Monitoring

- **Eureka Dashboard**: http://localhost:8761 - View registered services
- **Circuit Breaker Metrics**: Available through Resilience4j metrics

## Troubleshooting

1. **Service Not Found**: Ensure Eureka Service Registry is running first
2. **Circuit Breaker**: Check if Student Service is running for Course Service calls
3. **Database Issues**: Check H2 console for data verification
4. **Port Conflicts**: Ensure no other services are using the required ports

## Project Structure

```
microservices-demo/
├── eureka-service/          # Service Registry
├── student-service/         # Student Management
├── course-service/          # Course Management with Circuit Breaker
├── api-gateway/            # API Gateway
└── pom.xml                 # Parent POM
```

## Features

- ✅ Service Discovery with Eureka
- ✅ API Gateway with Spring Cloud Gateway
- ✅ Circuit Breaker with Resilience4j
- ✅ Feign Client for inter-service communication
- ✅ CRUD operations for both services
- ✅ Course enrollment functionality
- ✅ H2 in-memory database
- ✅ RESTful APIs
- ✅ Health checks
- ✅ Load balancing
