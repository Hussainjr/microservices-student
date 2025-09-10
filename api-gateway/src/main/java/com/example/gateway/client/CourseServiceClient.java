package com.example.gateway.client;

import com.example.gateway.dto.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "course-service")
public interface CourseServiceClient {

    @GetMapping("/api/courses")
    List<CourseDTO> getAllCourses();

    @GetMapping("/api/courses/{id}")
    CourseDTO getCourseById(@PathVariable("id") Long id);

    @GetMapping("/api/courses/active")
    List<CourseDTO> getActiveCourses();

    @GetMapping("/api/courses/available")
    List<CourseDTO> getAvailableCourses();

    @GetMapping("/api/courses/active-available")
    List<CourseDTO> getActiveAvailableCourses();

    @GetMapping("/api/courses/full")
    List<CourseDTO> getFullCourses();

    @GetMapping("/api/courses/count")
    Long getActiveCoursesCount();

    @GetMapping("/api/courses/search")
    List<CourseDTO> searchCoursesByName(@RequestParam("name") String name);

    @GetMapping("/api/courses/instructor/{instructor}")
    List<CourseDTO> getCoursesByInstructor(@PathVariable("instructor") String instructor);

    @GetMapping("/api/courses/price-range")
    List<CourseDTO> getCoursesByPriceRange(
            @RequestParam("minPrice") BigDecimal minPrice, 
            @RequestParam("maxPrice") BigDecimal maxPrice);

    @GetMapping("/api/courses/max-price/{maxPrice}")
    List<CourseDTO> getActiveCoursesByMaxPrice(@PathVariable("maxPrice") BigDecimal maxPrice);

    @GetMapping("/api/courses/duration-range")
    List<CourseDTO> getCoursesByDurationRange(
            @RequestParam("minDuration") Integer minDuration, 
            @RequestParam("maxDuration") Integer maxDuration);

    @GetMapping("/api/courses/start-date-range")
    List<CourseDTO> getCoursesByStartDateRange(
            @RequestParam("startDate") LocalDate startDate, 
            @RequestParam("endDate") LocalDate endDate);

    @GetMapping("/api/courses/health")
    String health();

    @GetMapping("/api/courses/status")
    Object getServiceStatus();
}
