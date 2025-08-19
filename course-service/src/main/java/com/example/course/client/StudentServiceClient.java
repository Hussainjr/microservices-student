package com.example.course.client;

import com.example.course.dto.StudentDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentServiceClient {

    @GetMapping("/api/students")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getAllStudentsFallback")
    List<StudentDTO> getAllStudents();

    @GetMapping("/api/students/{id}")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getStudentByIdFallback")
    StudentDTO getStudentById(@PathVariable Long id);

    @GetMapping("/api/students/email/{email}")
    @CircuitBreaker(name = "studentService", fallbackMethod = "getStudentByEmailFallback")
    StudentDTO getStudentByEmail(@PathVariable String email);

    // Fallback methods
    default List<StudentDTO> getAllStudentsFallback(Throwable throwable) {
        System.out.println("Fallback: Unable to get all students. Error: " + throwable.getMessage());
        return List.of();
    }

    default StudentDTO getStudentByIdFallback(Long id, Throwable throwable) {
        System.out.println("Fallback: Unable to get student with id " + id + ". Error: " + throwable.getMessage());
        return new StudentDTO(id, "Unknown", "Student", "unknown@example.com", null, 0);
    }

    default StudentDTO getStudentByEmailFallback(String email, Throwable throwable) {
        System.out.println("Fallback: Unable to get student with email " + email + ". Error: " + throwable.getMessage());
        return new StudentDTO(0L, "Unknown", "Student", email, null, 0);
    }
}
