#!/bin/bash

echo "Starting Spring Boot Microservices Demo..."
echo

echo "Starting Eureka Service Registry..."
cd eureka-service && mvn spring-boot:run &
EUREKA_PID=$!
sleep 10

echo "Starting Student Service..."
cd ../student-service && mvn spring-boot:run &
STUDENT_PID=$!
sleep 10

echo "Starting Course Service..."
cd ../course-service && mvn spring-boot:run &
COURSE_PID=$!
sleep 10

echo "Starting API Gateway..."
cd ../api-gateway && mvn spring-boot:run &
GATEWAY_PID=$!
sleep 10

echo
echo "All services are starting..."
echo
echo "Access URLs:"
echo "- API Gateway: http://localhost:8080"
echo "- Eureka Dashboard: http://localhost:8761"
echo "- Student Service: http://localhost:8081"
echo "- Course Service: http://localhost:8082"
echo "- H2 Console (Student): http://localhost:8081/h2-console"
echo "- H2 Console (Course): http://localhost:8082/h2-console"
echo
echo "Press Ctrl+C to stop all services..."

# Function to cleanup processes on exit
cleanup() {
    echo "Stopping all services..."
    kill $EUREKA_PID $STUDENT_PID $COURSE_PID $GATEWAY_PID 2>/dev/null
    exit 0
}

# Set trap to cleanup on script exit
trap cleanup SIGINT SIGTERM

# Wait for user to stop
wait
