package com.example.course.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments")
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDateTime enrollmentDate;

    @Column(name = "status")
    private String status = "ENROLLED"; // ENROLLED, COMPLETED, DROPPED

    @Column(name = "grade")
    private String grade;

    // Default constructor
    public CourseEnrollment() {}

    // Constructor with fields
    public CourseEnrollment(Long courseId, Long studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrollmentDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "CourseEnrollment{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", enrollmentDate=" + enrollmentDate +
                ", status='" + status + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
