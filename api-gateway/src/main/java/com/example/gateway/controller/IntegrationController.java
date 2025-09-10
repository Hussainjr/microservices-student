package com.example.gateway.controller;

import com.example.gateway.dto.CourseDTO;
import com.example.gateway.dto.ServiceRegistryDTO;
import com.example.gateway.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/integration")
@CrossOrigin(origins = "*")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getSystemOverview() {
        Map<String, Object> overview = integrationService.getSystemOverview();
        return ResponseEntity.ok(overview);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getServiceHealthStatus() {
        Map<String, Object> healthStatus = integrationService.getServiceHealthStatus();
        return ResponseEntity.ok(healthStatus);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getDetailedServiceStatus() {
        Map<String, Object> detailedStatus = integrationService.getDetailedServiceStatus();
        return ResponseEntity.ok(detailedStatus);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getSystemStatistics() {
        Map<String, Object> statistics = integrationService.getSystemStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceRegistryDTO>> getAllRegisteredServices() {
        List<ServiceRegistryDTO> services = integrationService.getAllRegisteredServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/services/active")
    public ResponseEntity<List<ServiceRegistryDTO>> getActiveRegisteredServices() {
        List<ServiceRegistryDTO> services = integrationService.getActiveRegisteredServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/student/{studentId}/courses")
    public ResponseEntity<Map<String, Object>> getStudentCourseIntegration(@PathVariable Long studentId) {
        Map<String, Object> integration = integrationService.getStudentCourseIntegration(studentId);
        return ResponseEntity.ok(integration);
    }

    @GetMapping("/course/{courseId}/students")
    public ResponseEntity<Map<String, Object>> getCourseStudentIntegration(@PathVariable Long courseId) {
        Map<String, Object> integration = integrationService.getCourseStudentIntegration(courseId);
        return ResponseEntity.ok(integration);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchAcrossServices(@RequestParam String term) {
        Map<String, Object> searchResults = integrationService.searchAcrossServices(term);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboard = integrationService.getSystemOverview();
        
        // Add additional dashboard-specific data
        try {
            Map<String, Object> statistics = integrationService.getSystemStatistics();
            dashboard.putAll(statistics);
            
            Map<String, Object> healthStatus = integrationService.getServiceHealthStatus();
            dashboard.put("healthStatus", healthStatus);
            
        } catch (Exception e) {
            dashboard.put("dashboardError", e.getMessage());
        }
        
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API Gateway Integration Service is running!");
    }
}
