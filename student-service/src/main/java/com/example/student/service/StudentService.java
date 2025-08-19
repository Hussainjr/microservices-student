package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
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

    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            
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
            
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
}
