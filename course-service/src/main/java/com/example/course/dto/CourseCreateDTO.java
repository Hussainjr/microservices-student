package com.example.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CourseCreateDTO {
    @NotBlank(message = "Course name is required")
    private String name;

    private String description;

    @NotBlank(message = "Instructor name is required")
    private String instructor;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationWeeks;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxStudents;

    // Default constructor
    public CourseCreateDTO() {}

    // Constructor with fields
    public CourseCreateDTO(String name, String description, String instructor, Integer durationWeeks, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.instructor = instructor;
        this.durationWeeks = durationWeeks;
        this.price = price;
    }

    // Getters and Setters
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
}
