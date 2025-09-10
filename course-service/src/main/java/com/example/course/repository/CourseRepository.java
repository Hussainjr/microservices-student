package com.example.course.repository;

import com.example.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    List<Course> findByIsActiveTrue();
    
    List<Course> findByInstructor(String instructor);
    
    List<Course> findByPriceLessThanEqual(BigDecimal price);
    
    List<Course> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT c FROM Course c WHERE c.name LIKE %:name%")
    List<Course> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Course c WHERE c.instructor LIKE %:instructor%")
    List<Course> findByInstructorContaining(@Param("instructor") String instructor);
    
    @Query("SELECT c FROM Course c WHERE c.durationWeeks >= :minDuration AND c.durationWeeks <= :maxDuration")
    List<Course> findByDurationBetween(@Param("minDuration") Integer minDuration, @Param("maxDuration") Integer maxDuration);
    
    @Query("SELECT c FROM Course c WHERE c.startDate >= :startDate AND c.startDate <= :endDate")
    List<Course> findByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c FROM Course c WHERE c.currentStudents < c.maxStudents")
    List<Course> findAvailableCourses();
    
    @Query("SELECT c FROM Course c WHERE c.currentStudents >= c.maxStudents")
    List<Course> findFullCourses();
    
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND c.currentStudents < c.maxStudents")
    List<Course> findActiveAvailableCourses();
    
    @Query("SELECT COUNT(c) FROM Course c WHERE c.isActive = true")
    long countActiveCourses();
    
    @Query("SELECT c FROM Course c ORDER BY c.name ASC")
    List<Course> findAllOrderByName();
    
    @Query("SELECT c FROM Course c ORDER BY c.price ASC")
    List<Course> findAllOrderByPrice();
    
    @Query("SELECT c FROM Course c WHERE c.price <= :maxPrice AND c.isActive = true")
    List<Course> findActiveCoursesByMaxPrice(@Param("maxPrice") BigDecimal maxPrice);
}
