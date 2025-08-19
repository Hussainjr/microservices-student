package com.example.course.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CourseWithStudentsDTO {
    private Long id;
    private String name;
    private String description;
    private String instructor;
    private Integer durationWeeks;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxStudents;
    private Integer currentStudents;
    private Boolean isActive;
    private List<StudentDTO> enrolledStudents;

    // Default constructor
    public CourseWithStudentsDTO() {}

    // Constructor with fields
    public CourseWithStudentsDTO(Long id, String name, String description, String instructor, 
                                Integer durationWeeks, BigDecimal price, Integer maxStudents, 
                                Integer currentStudents, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructor = instructor;
        this.durationWeeks = durationWeeks;
        this.price = price;
        this.maxStudents = maxStudents;
        this.currentStudents = currentStudents;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Integer getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(Integer currentStudents) {
        this.currentStudents = currentStudents;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<StudentDTO> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<StudentDTO> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public String toString() {
        return "CourseWithStudentsDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", durationWeeks=" + durationWeeks +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxStudents=" + maxStudents +
                ", currentStudents=" + currentStudents +
                ", isActive=" + isActive +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
