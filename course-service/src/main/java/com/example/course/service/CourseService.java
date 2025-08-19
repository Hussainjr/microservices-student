package com.example.course.service;

import com.example.course.client.StudentServiceClient;
import com.example.course.dto.CourseWithStudentsDTO;
import com.example.course.dto.StudentDTO;
import com.example.course.model.Course;
import com.example.course.model.CourseEnrollment;
import com.example.course.repository.CourseEnrollmentRepository;
import com.example.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentServiceClient studentServiceClient;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getActiveCourses() {
        return courseRepository.findByIsActiveTrue();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            
            // Update fields
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setInstructor(courseDetails.getInstructor());
            course.setDurationWeeks(courseDetails.getDurationWeeks());
            course.setPrice(courseDetails.getPrice());
            course.setStartDate(courseDetails.getStartDate());
            course.setEndDate(courseDetails.getEndDate());
            course.setMaxStudents(courseDetails.getMaxStudents());
            course.setIsActive(courseDetails.getIsActive());
            
            return courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    public void deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }

    public CourseWithStudentsDTO getCourseWithStudents(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
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
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }

    public CourseEnrollment enrollStudentInCourse(Long courseId, Long studentId) {
        // Check if course exists
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
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
        Course course = courseRepository.findById(courseId).get();
        course.setCurrentStudents(course.getCurrentStudents() + 1);
        courseRepository.save(course);
        
        return savedEnrollment;
    }

    public List<CourseEnrollment> getCourseEnrollments(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public List<CourseEnrollment> getStudentEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
