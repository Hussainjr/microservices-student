package com.example.course.repository;

import com.example.course.model.CourseEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {
    
    List<CourseEnrollment> findByCourseId(Long courseId);
    
    List<CourseEnrollment> findByStudentId(Long studentId);
    
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
