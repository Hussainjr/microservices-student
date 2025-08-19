@echo off
echo Starting Spring Boot Microservices Demo...
echo.

echo Starting Eureka Service Registry...
start "Eureka Service" cmd /k "cd eureka-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting Student Service...
start "Student Service" cmd /k "cd student-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting Course Service...
start "Course Service" cmd /k "cd course-service && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo Starting API Gateway...
start "API Gateway" cmd /k "cd api-gateway && mvn spring-boot:run"
timeout /t 10 /nobreak >nul

echo.
echo All services are starting...
echo.
echo Access URLs:
echo - API Gateway: http://localhost:8080
echo - Eureka Dashboard: http://localhost:8761
echo - Student Service: http://localhost:8081
echo - Course Service: http://localhost:8082
echo - H2 Console (Student): http://localhost:8081/h2-console
echo - H2 Console (Course): http://localhost:8082/h2-console
echo.
echo Press any key to exit...
pause >nul
