package com.example.gateway.client;

import com.example.gateway.dto.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentServiceClient {

    @GetMapping("/api/students")
    List<StudentDTO> getAllStudents();

    @GetMapping("/api/students/{id}")
    StudentDTO getStudentById(@PathVariable("id") Long id);

    @GetMapping("/api/students/email/{email}")
    StudentDTO getStudentByEmail(@PathVariable("email") String email);

    @GetMapping("/api/students/count")
    Long getTotalStudentsCount();

    @GetMapping("/api/students/search")
    List<StudentDTO> searchStudentsByName(@RequestParam("name") String name);

    @GetMapping("/api/students/age-range")
    List<StudentDTO> getStudentsByAgeRange(
            @RequestParam("minAge") Integer minAge, 
            @RequestParam("maxAge") Integer maxAge);

    @GetMapping("/api/students/health")
    String health();

    @GetMapping("/api/students/status")
    Object getServiceStatus();
}
