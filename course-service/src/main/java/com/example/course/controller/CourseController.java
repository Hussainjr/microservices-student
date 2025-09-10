package com.example.course.controller;

import com.example.course.dto.CourseCreateDTO;
import com.example.course.dto.CourseResponseDTO;
import com.example.course.dto.CourseWithStudentsDTO;
import com.example.course.model.Course;
import com.example.course.model.CourseEnrollment;
import com.example.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<CourseResponseDTO>> getAllCoursesOrderByName() {
        List<CourseResponseDTO> courses = courseService.getAllCoursesOrderByName();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/ordered-by-price")
    public ResponseEntity<List<CourseResponseDTO>> getAllCoursesOrderByPrice() {
        List<CourseResponseDTO> courses = courseService.getAllCoursesOrderByPrice();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CourseResponseDTO>> getActiveCourses() {
        List<CourseResponseDTO> courses = courseService.getActiveCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CourseResponseDTO>> getAvailableCourses() {
        List<CourseResponseDTO> courses = courseService.getAvailableCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/active-available")
    public ResponseEntity<List<CourseResponseDTO>> getActiveAvailableCourses() {
        List<CourseResponseDTO> courses = courseService.getActiveAvailableCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/full")
    public ResponseEntity<List<CourseResponseDTO>> getFullCourses() {
        List<CourseResponseDTO> courses = courseService.getFullCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getActiveCoursesCount() {
        long count = courseService.getActiveCoursesCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseResponseDTO> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-students")
    public ResponseEntity<CourseWithStudentsDTO> getCourseWithStudents(@PathVariable Long id) {
        try {
            CourseWithStudentsDTO courseWithStudents = courseService.getCourseWithStudents(id);
            return ResponseEntity.ok(courseWithStudents);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseResponseDTO>> searchCoursesByName(@RequestParam String name) {
        List<CourseResponseDTO> courses = courseService.searchCoursesByName(name);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/instructor/{instructor}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByInstructor(@PathVariable String instructor) {
        List<CourseResponseDTO> courses = courseService.getCoursesByInstructor(instructor);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByPriceRange(
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice) {
        List<CourseResponseDTO> courses = courseService.getCoursesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/max-price/{maxPrice}")
    public ResponseEntity<List<CourseResponseDTO>> getActiveCoursesByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<CourseResponseDTO> courses = courseService.getActiveCoursesByMaxPrice(maxPrice);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/duration-range")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByDurationRange(
            @RequestParam Integer minDuration, 
            @RequestParam Integer maxDuration) {
        List<CourseResponseDTO> courses = courseService.getCoursesByDurationRange(minDuration, maxDuration);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/start-date-range")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByStartDateRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        List<CourseResponseDTO> courses = courseService.getCoursesByStartDateRange(startDate, endDate);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseCreateDTO courseCreateDTO) {
        try {
            CourseResponseDTO createdCourse = courseService.createCourse(courseCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/legacy")
    public ResponseEntity<Course> createCourseLegacy(@Valid @RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourseLegacy(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseCreateDTO courseDetails) {
        try {
            CourseResponseDTO updatedCourse = courseService.updateCourse(id, courseDetails);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<CourseResponseDTO> activateCourse(@PathVariable Long id) {
        try {
            CourseResponseDTO course = courseService.activateCourse(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<CourseResponseDTO> deactivateCourse(@PathVariable Long id) {
        try {
            CourseResponseDTO course = courseService.deactivateCourse(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<CourseEnrollment> enrollStudentInCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            CourseEnrollment enrollment = courseService.enrollStudentInCourse(courseId, studentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{courseId}/unenroll/{studentId}")
    public ResponseEntity<Void> unenrollStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            courseService.unenrollStudentFromCourse(courseId, studentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{courseId}/enrollments")
    public ResponseEntity<List<CourseEnrollment>> getCourseEnrollments(@PathVariable Long courseId) {
        List<CourseEnrollment> enrollments = courseService.getCourseEnrollments(courseId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}/enrollments")
    public ResponseEntity<List<CourseEnrollment>> getStudentEnrollments(@PathVariable Long studentId) {
        List<CourseEnrollment> enrollments = courseService.getStudentEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Course Service is running!");
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getServiceStatus() {
        long activeCourses = courseService.getActiveCoursesCount();
        
        Map<String, Object> status = new HashMap<>();
        status.put("activeCourses", activeCourses);
        status.put("status", "Course Service is operational");
        status.put("timestamp", LocalDate.now().toString());
        return ResponseEntity.ok(status);
    }
}
