#!/bin/bash

echo "========================================"
echo "Microservices Integration Test Script"
echo "========================================"

echo ""
echo "Starting all microservices..."
echo ""

echo "1. Starting Eureka Service Registry..."
gnome-terminal -- bash -c "cd eureka-service && mvn spring-boot:run; exec bash" &

echo "Waiting for Eureka to start..."
sleep 30

echo ""
echo "2. Starting Student Service..."
gnome-terminal -- bash -c "cd student-service && mvn spring-boot:run; exec bash" &

echo "Waiting for Student Service to start..."
sleep 20

echo ""
echo "3. Starting Course Service..."
gnome-terminal -- bash -c "cd course-service && mvn spring-boot:run; exec bash" &

echo "Waiting for Course Service to start..."
sleep 20

echo ""
echo "4. Starting API Gateway..."
gnome-terminal -- bash -c "cd api-gateway && mvn spring-boot:run; exec bash" &

echo "Waiting for API Gateway to start..."
sleep 20

echo ""
echo "========================================"
echo "All services started!"
echo "========================================"
echo ""
echo "Service URLs:"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- Student Service: http://localhost:8081"
echo "- Course Service: http://localhost:8082"
echo ""
echo "Integration API Endpoints:"
echo "- System Overview: http://localhost:8080/api/integration/overview"
echo "- Health Status: http://localhost:8080/api/integration/health"
echo "- Dashboard: http://localhost:8080/api/integration/dashboard"
echo "- Search: http://localhost:8080/api/integration/search?term=java"
echo ""
echo "Press Enter to run integration tests..."
read

echo ""
echo "========================================"
echo "Running Integration Tests"
echo "========================================"

echo ""
echo "Testing Eureka Service Registry..."
curl -s http://localhost:8761/api/services/health
echo ""

echo ""
echo "Testing Student Service..."
curl -s http://localhost:8081/api/students/health
echo ""

echo ""
echo "Testing Course Service..."
curl -s http://localhost:8082/api/courses/health
echo ""

echo ""
echo "Testing API Gateway Integration..."
curl -s http://localhost:8080/api/integration/health-check
echo ""

echo ""
echo "Testing System Overview..."
curl -s http://localhost:8080/api/integration/overview
echo ""

echo ""
echo "Testing Service Health Status..."
curl -s http://localhost:8080/api/integration/health
echo ""

echo ""
echo "========================================"
echo "Integration Tests Complete!"
echo "========================================"
echo ""
echo "You can now test the following endpoints:"
echo ""
echo "1. Create a student:"
echo "   POST http://localhost:8080/api/students"
echo "   Body: {\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"dateOfBirth\":\"1995-01-01\"}"
echo ""
echo "2. Create a course:"
echo "   POST http://localhost:8080/api/courses"
echo "   Body: {\"name\":\"Java Programming\",\"description\":\"Learn Java\",\"instructor\":\"Dr. Smith\",\"durationWeeks\":12,\"price\":299.99}"
echo ""
echo "3. Enroll student in course:"
echo "   POST http://localhost:8080/api/courses/1/enroll/1"
echo ""
echo "4. Get system overview:"
echo "   GET http://localhost:8080/api/integration/overview"
echo ""
echo "5. Search across services:"
echo "   GET http://localhost:8080/api/integration/search?term=java"
echo ""
echo "Press Enter to exit..."
read
