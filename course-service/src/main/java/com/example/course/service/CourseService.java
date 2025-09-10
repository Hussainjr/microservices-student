package com.example.course.service;

import com.example.course.client.StudentServiceClient;
import com.example.course.dto.CourseCreateDTO;
import com.example.course.dto.CourseResponseDTO;
import com.example.course.dto.CourseWithStudentsDTO;
import com.example.course.dto.StudentDTO;
import com.example.course.model.Course;
import com.example.course.model.CourseEnrollment;
import com.example.course.repository.CourseEnrollmentRepository;
import com.example.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentServiceClient studentServiceClient;

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getAllCoursesOrderByName() {
        return courseRepository.findAllOrderByName().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getAllCoursesOrderByPrice() {
        return courseRepository.findAllOrderByPrice().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getActiveCourses() {
        return courseRepository.findByIsActiveTrue().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getAvailableCourses() {
        return courseRepository.findAvailableCourses().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getActiveAvailableCourses() {
        return courseRepository.findActiveAvailableCourses().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getFullCourses() {
        return courseRepository.findFullCourses().stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CourseResponseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(CourseResponseDTO::new);
    }

    public CourseResponseDTO createCourse(CourseCreateDTO courseCreateDTO) {
        Course course = new Course();
        course.setName(courseCreateDTO.getName());
        course.setDescription(courseCreateDTO.getDescription());
        course.setInstructor(courseCreateDTO.getInstructor());
        course.setDurationWeeks(courseCreateDTO.getDurationWeeks());
        course.setPrice(courseCreateDTO.getPrice());
        course.setStartDate(courseCreateDTO.getStartDate());
        course.setEndDate(courseCreateDTO.getEndDate());
        course.setMaxStudents(courseCreateDTO.getMaxStudents());
        course.setCurrentStudents(0);
        course.setIsActive(true);

        Course savedCourse = courseRepository.save(course);
        return new CourseResponseDTO(savedCourse);
    }

    public CourseResponseDTO updateCourse(Long id, CourseCreateDTO courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Update fields
        course.setName(courseDetails.getName());
        course.setDescription(courseDetails.getDescription());
        course.setInstructor(courseDetails.getInstructor());
        course.setDurationWeeks(courseDetails.getDurationWeeks());
        course.setPrice(courseDetails.getPrice());
        course.setStartDate(courseDetails.getStartDate());
        course.setEndDate(courseDetails.getEndDate());
        course.setMaxStudents(courseDetails.getMaxStudents());
        
        Course updatedCourse = courseRepository.save(course);
        return new CourseResponseDTO(updatedCourse);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    public CourseResponseDTO activateCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        course.setIsActive(true);
        Course updatedCourse = courseRepository.save(course);
        return new CourseResponseDTO(updatedCourse);
    }

    public CourseResponseDTO deactivateCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        course.setIsActive(false);
        Course updatedCourse = courseRepository.save(course);
        return new CourseResponseDTO(updatedCourse);
    }

    public List<CourseResponseDTO> searchCoursesByName(String name) {
        return courseRepository.findByNameContaining(name).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesByInstructor(String instructor) {
        return courseRepository.findByInstructorContaining(instructor).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return courseRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getActiveCoursesByMaxPrice(BigDecimal maxPrice) {
        return courseRepository.findActiveCoursesByMaxPrice(maxPrice).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesByDurationRange(Integer minDuration, Integer maxDuration) {
        return courseRepository.findByDurationBetween(minDuration, maxDuration).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesByStartDateRange(LocalDate startDate, LocalDate endDate) {
        return courseRepository.findByStartDateBetween(startDate, endDate).stream()
                .map(CourseResponseDTO::new)
                .collect(Collectors.toList());
    }

    public long getActiveCoursesCount() {
        return courseRepository.countActiveCourses();
    }

    public CourseWithStudentsDTO getCourseWithStudents(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        
        CourseWithStudentsDTO courseWithStudents = new CourseWithStudentsDTO(
            course.getId(), course.getName(), course.getDescription(), course.getInstructor(),
            course.getDurationWeeks(), course.getPrice(), course.getMaxStudents(),
            course.getCurrentStudents(), course.getIsActive()
        );
        courseWithStudents.setStartDate(course.getStartDate());
        courseWithStudents.setEndDate(course.getEndDate());
        
        // Get enrolled students
        List<CourseEnrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        List<StudentDTO> students = enrollments.stream()
            .map(enrollment -> studentServiceClient.getStudentById(enrollment.getStudentId()))
            .collect(Collectors.toList());
        
        courseWithStudents.setEnrolledStudents(students);
        return courseWithStudents;
    }

    public CourseEnrollment enrollStudentInCourse(Long courseId, Long studentId) {
        // Check if course exists and is available
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        
        if (!course.getIsActive()) {
            throw new RuntimeException("Course is not active");
        }
        
        if (course.getMaxStudents() != null && course.getCurrentStudents() >= course.getMaxStudents()) {
            throw new RuntimeException("Course is full");
        }
        
        // Check if student exists (using Feign Client with circuit breaker)
        try {
            StudentDTO student = studentServiceClient.getStudentById(studentId);
            if (student.getId() == null || student.getId() == 0) {
                throw new RuntimeException("Student not found with id: " + studentId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to verify student: " + e.getMessage());
        }
        
        // Check if already enrolled
        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        
        // Create enrollment
        CourseEnrollment enrollment = new CourseEnrollment(courseId, studentId);
        CourseEnrollment savedEnrollment = enrollmentRepository.save(enrollment);
        
        // Update course current students count
        course.setCurrentStudents(course.getCurrentStudents() + 1);
        courseRepository.save(course);
        
        return savedEnrollment;
    }

    public void unenrollStudentFromCourse(Long courseId, Long studentId) {
        CourseEnrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId);
        if (enrollment == null) {
            throw new RuntimeException("Enrollment not found");
        }
        enrollmentRepository.delete(enrollment);
        
        // Update course current students count
        Course course = courseRepository.findById(courseId).get();
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
        course.setCurrentStudents(Math.max(0, course.getCurrentStudents() - 1));
        courseRepository.save(course);
    }

    public List<CourseEnrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<CourseEnrollment> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    // Legacy method for backward compatibility
    public Course createCourseLegacy(Course course) {
        return courseRepository.save(course);
    }
}
