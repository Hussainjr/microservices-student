# Microservices Student Management System - API Documentation

## Overview
This microservices system consists of four main services:
- **Eureka Service Registry** (Port 8761) - Service discovery and registry management
- **Student Service** (Port 8081) - Student management operations
- **Course Service** (Port 8082) - Course management and enrollment operations
- **API Gateway** (Port 8080) - Centralized API gateway with integration endpoints

## Service URLs
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Student Service: http://localhost:8081
- Course Service: http://localhost:8082

## API Endpoints

### 1. Eureka Service Registry APIs

#### Service Registry Management
- `GET /api/services` - Get all registered services
- `GET /api/services/active` - Get active services
- `GET /api/services/{id}` - Get service by ID
- `GET /api/services/name/{serviceName}` - Get service by name
- `GET /api/services/environment/{environment}` - Get services by environment
- `GET /api/services/version/{version}` - Get services by version
- `GET /api/services/search?name={name}` - Search services by name
- `GET /api/services/stale?minutes={minutes}` - Get stale services
- `GET /api/services/count` - Get active services count
- `POST /api/services` - Register new service
- `PUT /api/services/{id}` - Update service
- `DELETE /api/services/{id}` - Delete service
- `POST /api/services/{serviceName}/heartbeat` - Update service heartbeat
- `PUT /api/services/{id}/activate` - Activate service
- `PUT /api/services/{id}/deactivate` - Deactivate service

#### Health & Status
- `GET /api/services/health` - Service health check
- `GET /api/services/status` - Detailed service status

### 2. Student Service APIs

#### Student Management
- `GET /api/students` - Get all students
- `GET /api/students/ordered` - Get students ordered by name
- `GET /api/students/count` - Get total students count
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students/email/{email}` - Get student by email
- `GET /api/students/search?name={name}` - Search students by name
- `GET /api/students/age-range?minAge={min}&maxAge={max}` - Get students by age range
- `GET /api/students/birth-date-range?startDate={start}&endDate={end}` - Get students by birth date range
- `GET /api/students/first-name/{firstName}` - Get students by first name
- `GET /api/students/last-name/{lastName}` - Get students by last name
- `POST /api/students` - Create new student
- `POST /api/students/legacy` - Create student (legacy endpoint)
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

#### Health & Status
- `GET /api/students/health` - Service health check
- `GET /api/students/status` - Detailed service status

### 3. Course Service APIs

#### Course Management
- `GET /api/courses` - Get all courses
- `GET /api/courses/ordered` - Get courses ordered by name
- `GET /api/courses/ordered-by-price` - Get courses ordered by price
- `GET /api/courses/active` - Get active courses
- `GET /api/courses/available` - Get available courses
- `GET /api/courses/active-available` - Get active and available courses
- `GET /api/courses/full` - Get full courses
- `GET /api/courses/count` - Get active courses count
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/{id}/with-students` - Get course with enrolled students
- `GET /api/courses/search?name={name}` - Search courses by name
- `GET /api/courses/instructor/{instructor}` - Get courses by instructor
- `GET /api/courses/price-range?minPrice={min}&maxPrice={max}` - Get courses by price range
- `GET /api/courses/max-price/{maxPrice}` - Get active courses by max price
- `GET /api/courses/duration-range?minDuration={min}&maxDuration={max}` - Get courses by duration range
- `GET /api/courses/start-date-range?startDate={start}&endDate={end}` - Get courses by start date range
- `POST /api/courses` - Create new course
- `POST /api/courses/legacy` - Create course (legacy endpoint)
- `PUT /api/courses/{id}` - Update course
- `PUT /api/courses/{id}/activate` - Activate course
- `PUT /api/courses/{id}/deactivate` - Deactivate course
- `DELETE /api/courses/{id}` - Delete course

#### Enrollment Management
- `POST /api/courses/{courseId}/enroll/{studentId}` - Enroll student in course
- `DELETE /api/courses/{courseId}/unenroll/{studentId}` - Unenroll student from course
- `GET /api/courses/{courseId}/enrollments` - Get course enrollments
- `GET /api/courses/student/{studentId}/enrollments` - Get student enrollments

#### Health & Status
- `GET /api/courses/health` - Service health check
- `GET /api/courses/status` - Detailed service status

### 4. API Gateway Integration APIs

#### System Overview & Health
- `GET /api/integration/overview` - Get system overview
- `GET /api/integration/health` - Get service health status
- `GET /api/integration/status` - Get detailed service status
- `GET /api/integration/statistics` - Get system statistics
- `GET /api/integration/dashboard` - Get dashboard data
- `GET /api/integration/health-check` - Gateway health check

#### Service Registry Integration
- `GET /api/integration/services` - Get all registered services
- `GET /api/integration/services/active` - Get active registered services

#### Cross-Service Integration
- `GET /api/integration/student/{studentId}/courses` - Get student with available courses
- `GET /api/integration/course/{courseId}/students` - Get course with all students
- `GET /api/integration/search?term={term}` - Search across all services

## Data Models

### Student
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "dateOfBirth": "1995-01-01",
  "age": 29,
  "phoneNumber": "+1234567890",
  "address": "123 Main St, City, State",
  "fullName": "John Doe"
}
```

### Course
```json
{
  "id": 1,
  "name": "Java Programming",
  "description": "Learn Java programming fundamentals",
  "instructor": "Dr. Smith",
  "durationWeeks": 12,
  "price": 299.99,
  "startDate": "2024-01-15",
  "endDate": "2024-04-15",
  "maxStudents": 30,
  "currentStudents": 15,
  "isActive": true,
  "isFull": false,
  "isAvailable": true
}
```

### Service Registry
```json
{
  "id": 1,
  "serviceName": "student-service",
  "serviceUrl": "http://localhost:8081",
  "description": "Student management service",
  "port": 8081,
  "healthCheckUrl": "http://localhost:8081/api/students/health",
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastHeartbeat": "2024-01-01T10:05:00",
  "version": "1.0.0",
  "environment": "development"
}
```

## Example API Calls

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "dateOfBirth": "1995-01-01",
    "phoneNumber": "+1234567890",
    "address": "123 Main St, City, State"
  }'
```

### Create a Course
```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Java Programming",
    "description": "Learn Java programming fundamentals",
    "instructor": "Dr. Smith",
    "durationWeeks": 12,
    "price": 299.99,
    "startDate": "2024-01-15",
    "endDate": "2024-04-15",
    "maxStudents": 30
  }'
```

### Enroll Student in Course
```bash
curl -X POST http://localhost:8080/api/courses/1/enroll/1
```

### Get System Overview
```bash
curl http://localhost:8080/api/integration/overview
```

### Search Across Services
```bash
curl "http://localhost:8080/api/integration/search?term=java"
```

## Testing the System

### 1. Start All Services
Run the test script:
- Windows: `test-microservices.bat`
- Linux/Mac: `./test-microservices.sh`

### 2. Verify Services are Running
- Eureka Dashboard: http://localhost:8761
- Check that all services are registered and UP

### 3. Test Integration Endpoints
- System Overview: http://localhost:8080/api/integration/overview
- Health Status: http://localhost:8080/api/integration/health
- Dashboard: http://localhost:8080/api/integration/dashboard

### 4. Test CRUD Operations
1. Create a student
2. Create a course
3. Enroll student in course
4. Verify enrollment
5. Test search functionality

## Error Handling
All services return appropriate HTTP status codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

Error responses include error messages and details for debugging.

## Security
- CORS is enabled for all services
- Input validation is implemented using Bean Validation
- Service-to-service communication uses service discovery

## Monitoring
- Actuator endpoints are enabled for health checks and metrics
- Eureka provides service discovery and health monitoring
- API Gateway provides centralized logging and monitoring
