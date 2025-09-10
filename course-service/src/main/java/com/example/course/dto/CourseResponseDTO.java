package com.example.course.dto;

import com.example.course.model.Course;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CourseResponseDTO {
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
    private Boolean isFull;
    private Boolean isAvailable;

    public CourseResponseDTO() {}

    public CourseResponseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.instructor = course.getInstructor();
        this.durationWeeks = course.getDurationWeeks();
        this.price = course.getPrice();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.maxStudents = course.getMaxStudents();
        this.currentStudents = course.getCurrentStudents();
        this.isActive = course.getIsActive();
        this.isFull = course.getMaxStudents() != null && course.getCurrentStudents() >= course.getMaxStudents();
        this.isAvailable = course.getIsActive() && !this.isFull;
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

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
