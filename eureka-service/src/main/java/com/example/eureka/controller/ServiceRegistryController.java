package com.example.eureka.controller;

import com.example.eureka.model.ServiceRegistry;
import com.example.eureka.service.ServiceRegistryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceRegistryController {

    @Autowired
    private ServiceRegistryService serviceRegistryService;

    @GetMapping
    public ResponseEntity<List<ServiceRegistry>> getAllServices() {
        List<ServiceRegistry> services = serviceRegistryService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ServiceRegistry>> getActiveServices() {
        List<ServiceRegistry> services = serviceRegistryService.getActiveServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getActiveServicesCount() {
        long count = serviceRegistryService.getActiveServicesCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRegistry> getServiceById(@PathVariable Long id) {
        Optional<ServiceRegistry> service = serviceRegistryService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{serviceName}")
    public ResponseEntity<ServiceRegistry> getServiceByName(@PathVariable String serviceName) {
        Optional<ServiceRegistry> service = serviceRegistryService.getServiceByName(serviceName);
        return service.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/environment/{environment}")
    public ResponseEntity<List<ServiceRegistry>> getServicesByEnvironment(@PathVariable String environment) {
        List<ServiceRegistry> services = serviceRegistryService.getServicesByEnvironment(environment);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/version/{version}")
    public ResponseEntity<List<ServiceRegistry>> getServicesByVersion(@PathVariable String version) {
        List<ServiceRegistry> services = serviceRegistryService.getServicesByVersion(version);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceRegistry>> searchServicesByName(@RequestParam String name) {
        List<ServiceRegistry> services = serviceRegistryService.searchServicesByName(name);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/stale")
    public ResponseEntity<List<ServiceRegistry>> getStaleServices(@RequestParam(defaultValue = "5") int minutes) {
        List<ServiceRegistry> services = serviceRegistryService.getStaleServices(minutes);
        return ResponseEntity.ok(services);
    }

    @PostMapping
    public ResponseEntity<ServiceRegistry> createService(@Valid @RequestBody ServiceRegistry serviceRegistry) {
        try {
            ServiceRegistry createdService = serviceRegistryService.createService(serviceRegistry);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRegistry> updateService(@PathVariable Long id, @Valid @RequestBody ServiceRegistry serviceDetails) {
        try {
            ServiceRegistry updatedService = serviceRegistryService.updateService(id, serviceDetails);
            return ResponseEntity.ok(updatedService);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            serviceRegistryService.deleteService(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{serviceName}/heartbeat")
    public ResponseEntity<ServiceRegistry> updateHeartbeat(@PathVariable String serviceName) {
        try {
            ServiceRegistry service = serviceRegistryService.updateHeartbeat(serviceName);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ServiceRegistry> deactivateService(@PathVariable Long id) {
        try {
            ServiceRegistry service = serviceRegistryService.deactivateService(id);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ServiceRegistry> activateService(@PathVariable Long id) {
        try {
            ServiceRegistry service = serviceRegistryService.activateService(id);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Eureka Service Registry is running!");
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getServiceStatus() {
        long activeCount = serviceRegistryService.getActiveServicesCount();
        List<ServiceRegistry> allServices = serviceRegistryService.getAllServices();
        
        return ResponseEntity.ok(new Object() {
            public final long totalServices = allServices.size();
            public final long activeServices = activeCount;
            public final long inactiveServices = totalServices - activeCount;
            public final String status = "Eureka Service Registry is operational";
        });
    }
}
