package com.example.student.service;

import com.example.student.dto.StudentCreateDTO;
import com.example.student.dto.StudentResponseDTO;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getAllStudentsOrderByName() {
        return studentRepository.findAllOrderByName().stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<StudentResponseDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(StudentResponseDTO::new);
    }

    public StudentResponseDTO createStudent(StudentCreateDTO studentCreateDTO) {
        Student student = new Student();
        student.setFirstName(studentCreateDTO.getFirstName());
        student.setLastName(studentCreateDTO.getLastName());
        student.setEmail(studentCreateDTO.getEmail());
        student.setDateOfBirth(studentCreateDTO.getDateOfBirth());
        student.setPhoneNumber(studentCreateDTO.getPhoneNumber());
        student.setAddress(studentCreateDTO.getAddress());

        // Calculate age from date of birth
        if (student.getDateOfBirth() != null) {
            int age = Period.between(student.getDateOfBirth(), LocalDate.now()).getYears();
            student.setAge(age);
        }
        
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with email " + student.getEmail() + " already exists");
        }
        
        Student savedStudent = studentRepository.save(student);
        return new StudentResponseDTO(savedStudent);
    }

    public StudentResponseDTO updateStudent(Long id, StudentCreateDTO studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (!student.getEmail().equals(studentDetails.getEmail()) &&
            studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new RuntimeException("Student with email " + studentDetails.getEmail() + " already exists");
        }
        
        // Update fields
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        student.setAddress(studentDetails.getAddress());
        
        // Recalculate age
        if (student.getDateOfBirth() != null) {
            int age = Period.between(student.getDateOfBirth(), LocalDate.now()).getYears();
            student.setAge(age);
        }
        
        Student updatedStudent = studentRepository.save(student);
        return new StudentResponseDTO(updatedStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public Optional<StudentResponseDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(StudentResponseDTO::new);
    }

    public List<StudentResponseDTO> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name).stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByAgeRange(Integer minAge, Integer maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge).stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        return studentRepository.findByDateOfBirthBetween(startDate, endDate).stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public long getTotalStudentsCount() {
        return studentRepository.countAllStudents();
    }

    public List<StudentResponseDTO> getStudentsByFirstName(String firstName) {
        return studentRepository.findByFirstNameContainingIgnoreCase(firstName).stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastNameContainingIgnoreCase(lastName).stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Legacy method for backward compatibility
    public Student createStudentLegacy(Student student) {
        // Calculate age from date of birth
        if (student.getDateOfBirth() != null) {
            int age = Period.between(student.getDateOfBirth(), LocalDate.now()).getYears();
            student.setAge(age);
        }
        
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with email " + student.getEmail() + " already exists");
        }
        
        return studentRepository.save(student);
    }
}
