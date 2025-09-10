package com.example.gateway.service;

import com.example.gateway.client.CourseServiceClient;
import com.example.gateway.client.EurekaServiceClient;
import com.example.gateway.client.StudentServiceClient;
import com.example.gateway.dto.CourseDTO;
import com.example.gateway.dto.ServiceRegistryDTO;
import com.example.gateway.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntegrationService {

    @Autowired
    private StudentServiceClient studentServiceClient;

    @Autowired
    private CourseServiceClient courseServiceClient;

    @Autowired
    private EurekaServiceClient eurekaServiceClient;

    public Map<String, Object> getSystemOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        try {
            // Get student statistics
            Long totalStudents = studentServiceClient.getTotalStudentsCount();
            overview.put("totalStudents", totalStudents);
            
            // Get course statistics
            Long activeCourses = courseServiceClient.getActiveCoursesCount();
            overview.put("activeCourses", activeCourses);
            
            // Get service registry statistics
            Long activeServices = eurekaServiceClient.getActiveServicesCount();
            overview.put("activeServices", activeServices);
            
            // Get service health status
            List<ServiceRegistryDTO> services = eurekaServiceClient.getActiveServices();
            overview.put("registeredServices", services.size());
            
            overview.put("status", "All systems operational");
            overview.put("timestamp", System.currentTimeMillis());
            
        } catch (Exception e) {
            overview.put("status", "Some services may be unavailable");
            overview.put("error", e.getMessage());
            overview.put("timestamp", System.currentTimeMillis());
        }
        
        return overview;
    }

    public Map<String, Object> getServiceHealthStatus() {
        Map<String, Object> healthStatus = new HashMap<>();
        
        try {
            // Check student service health
            String studentHealth = studentServiceClient.health();
            healthStatus.put("studentService", studentHealth);
            
            // Check course service health
            String courseHealth = courseServiceClient.health();
            healthStatus.put("courseService", courseHealth);
            
            // Check eureka service health
            String eurekaHealth = eurekaServiceClient.health();
            healthStatus.put("eurekaService", eurekaHealth);
            
            healthStatus.put("overallStatus", "All services healthy");
            
        } catch (Exception e) {
            healthStatus.put("overallStatus", "Some services unhealthy");
            healthStatus.put("error", e.getMessage());
        }
        
        return healthStatus;
    }

    public Map<String, Object> getDetailedServiceStatus() {
        Map<String, Object> detailedStatus = new HashMap<>();
        
        try {
            // Get detailed status from each service
            Object studentStatus = studentServiceClient.getServiceStatus();
            detailedStatus.put("studentService", studentStatus);
            
            Object courseStatus = courseServiceClient.getServiceStatus();
            detailedStatus.put("courseService", courseStatus);
            
            Object eurekaStatus = eurekaServiceClient.getServiceStatus();
            detailedStatus.put("eurekaService", eurekaStatus);
            
        } catch (Exception e) {
            detailedStatus.put("error", e.getMessage());
        }
        
        return detailedStatus;
    }

    public List<ServiceRegistryDTO> getAllRegisteredServices() {
        return eurekaServiceClient.getAllServices();
    }

    public List<ServiceRegistryDTO> getActiveRegisteredServices() {
        return eurekaServiceClient.getActiveServices();
    }

    public Map<String, Object> getStudentCourseIntegration(Long studentId) {
        Map<String, Object> integration = new HashMap<>();
        
        try {
            // Get student details
            StudentDTO student = studentServiceClient.getStudentById(studentId);
            integration.put("student", student);
            
            // Get all available courses
            List<CourseDTO> availableCourses = courseServiceClient.getActiveAvailableCourses();
            integration.put("availableCourses", availableCourses);
            
            // Get courses by price range (example: courses under $500)
            List<CourseDTO> affordableCourses = courseServiceClient.getActiveCoursesByMaxPrice(java.math.BigDecimal.valueOf(500));
            integration.put("affordableCourses", affordableCourses);
            
        } catch (Exception e) {
            integration.put("error", e.getMessage());
        }
        
        return integration;
    }

    public Map<String, Object> getCourseStudentIntegration(Long courseId) {
        Map<String, Object> integration = new HashMap<>();
        
        try {
            // Get course details
            CourseDTO course = courseServiceClient.getCourseById(courseId);
            integration.put("course", course);
            
            // Get all students
            List<StudentDTO> allStudents = studentServiceClient.getAllStudents();
            integration.put("allStudents", allStudents);
            
            // Get students by age range (example: 18-25)
            List<StudentDTO> youngStudents = studentServiceClient.getStudentsByAgeRange(18, 25);
            integration.put("youngStudents", youngStudents);
            
        } catch (Exception e) {
            integration.put("error", e.getMessage());
        }
        
        return integration;
    }

    public Map<String, Object> searchAcrossServices(String searchTerm) {
        Map<String, Object> searchResults = new HashMap<>();
        
        try {
            // Search students by name
            List<StudentDTO> studentResults = studentServiceClient.searchStudentsByName(searchTerm);
            searchResults.put("students", studentResults);
            
            // Search courses by name
            List<CourseDTO> courseResults = courseServiceClient.searchCoursesByName(searchTerm);
            searchResults.put("courses", courseResults);
            
            // Search services by name
            List<ServiceRegistryDTO> serviceResults = eurekaServiceClient.searchServicesByName(searchTerm);
            searchResults.put("services", serviceResults);
            
            searchResults.put("totalResults", studentResults.size() + courseResults.size() + serviceResults.size());
            
        } catch (Exception e) {
            searchResults.put("error", e.getMessage());
        }
        
        return searchResults;
    }

    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        try {
            // Student statistics
            Long totalStudents = studentServiceClient.getTotalStudentsCount();
            List<StudentDTO> youngStudents = studentServiceClient.getStudentsByAgeRange(18, 25);
            statistics.put("totalStudents", totalStudents);
            statistics.put("youngStudents", youngStudents.size());
            
            // Course statistics
            Long activeCourses = courseServiceClient.getActiveCoursesCount();
            List<CourseDTO> availableCourses = courseServiceClient.getAvailableCourses();
            List<CourseDTO> fullCourses = courseServiceClient.getFullCourses();
            statistics.put("activeCourses", activeCourses);
            statistics.put("availableCourses", availableCourses.size());
            statistics.put("fullCourses", fullCourses.size());
            
            // Service statistics
            Long activeServices = eurekaServiceClient.getActiveServicesCount();
            statistics.put("activeServices", activeServices);
            
            // Calculate utilization
            if (activeCourses > 0) {
                double courseUtilization = (double) fullCourses.size() / activeCourses * 100;
                statistics.put("courseUtilization", Math.round(courseUtilization * 100.0) / 100.0);
            }
            
        } catch (Exception e) {
            statistics.put("error", e.getMessage());
        }
        
        return statistics;
    }
}
