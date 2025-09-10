package com.example.student.controller;

import com.example.student.dto.StudentCreateDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudentsOrderByName() {
        List<StudentResponseDTO> students = studentService.getAllStudentsOrderByName();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalStudentsCount() {
        long count = studentService.getTotalStudentsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        Optional<StudentResponseDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDTO> getStudentByEmail(@PathVariable String email) {
        Optional<StudentResponseDTO> student = studentService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDTO>> searchStudentsByName(@RequestParam String name) {
        List<StudentResponseDTO> students = studentService.searchStudentsByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age-range")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByAgeRange(
            @RequestParam Integer minAge, 
            @RequestParam Integer maxAge) {
        List<StudentResponseDTO> students = studentService.getStudentsByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/birth-date-range")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByBirthDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        List<StudentResponseDTO> students = studentService.getStudentsByBirthDateRange(startDate, endDate);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByFirstName(@PathVariable String firstName) {
        List<StudentResponseDTO> students = studentService.getStudentsByFirstName(firstName);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/last-name/{lastName}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByLastName(@PathVariable String lastName) {
        List<StudentResponseDTO> students = studentService.getStudentsByLastName(lastName);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentCreateDTO studentCreateDTO) {
        try {
            StudentResponseDTO createdStudent = studentService.createStudent(studentCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/legacy")
    public ResponseEntity<Student> createStudentLegacy(@Valid @RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudentLegacy(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentCreateDTO studentDetails) {
        try {
            StudentResponseDTO updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Student Service is running!");
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getServiceStatus() {
        long totalStudents = studentService.getTotalStudentsCount();
        
        return ResponseEntity.ok(new Object() {
            public final long totalStudents = StudentController.this.studentService.getTotalStudentsCount();
            public final String status = "Student Service is operational";
            public final String timestamp = LocalDate.now().toString();
        });
    }
}
