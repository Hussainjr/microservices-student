package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Student> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
    
    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Student> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT s FROM Student s WHERE s.age >= :minAge AND s.age <= :maxAge")
    List<Student> findByAgeBetween(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);
    
    @Query("SELECT s FROM Student s WHERE s.dateOfBirth >= :startDate AND s.dateOfBirth <= :endDate")
    List<Student> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();
    
    @Query("SELECT s FROM Student s ORDER BY s.firstName ASC, s.lastName ASC")
    List<Student> findAllOrderByName();
}
