package com.example.eureka.service;

import com.example.eureka.model.ServiceRegistry;
import com.example.eureka.repository.ServiceRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceRegistryService {

    @Autowired
    private ServiceRegistryRepository serviceRegistryRepository;

    public List<ServiceRegistry> getAllServices() {
        return serviceRegistryRepository.findAll();
    }

    public List<ServiceRegistry> getActiveServices() {
        return serviceRegistryRepository.findByIsActiveTrue();
    }

    public Optional<ServiceRegistry> getServiceById(Long id) {
        return serviceRegistryRepository.findById(id);
    }

    public Optional<ServiceRegistry> getServiceByName(String serviceName) {
        return serviceRegistryRepository.findByServiceName(serviceName);
    }

    public List<ServiceRegistry> getServicesByEnvironment(String environment) {
        return serviceRegistryRepository.findByEnvironment(environment);
    }

    public List<ServiceRegistry> getServicesByVersion(String version) {
        return serviceRegistryRepository.findByVersion(version);
    }

    public List<ServiceRegistry> searchServicesByName(String name) {
        return serviceRegistryRepository.findByServiceNameContaining(name);
    }

    public ServiceRegistry createService(ServiceRegistry serviceRegistry) {
        if (serviceRegistryRepository.existsByServiceName(serviceRegistry.getServiceName())) {
            throw new RuntimeException("Service with name '" + serviceRegistry.getServiceName() + "' already exists");
        }
        serviceRegistry.setCreatedAt(LocalDateTime.now());
        serviceRegistry.setUpdatedAt(LocalDateTime.now());
        serviceRegistry.setLastHeartbeat(LocalDateTime.now());
        return serviceRegistryRepository.save(serviceRegistry);
    }

    public ServiceRegistry updateService(Long id, ServiceRegistry serviceDetails) {
        ServiceRegistry service = serviceRegistryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));

        // Check if service name is being changed and if it already exists
        if (!service.getServiceName().equals(serviceDetails.getServiceName()) &&
            serviceRegistryRepository.existsByServiceName(serviceDetails.getServiceName())) {
            throw new RuntimeException("Service with name '" + serviceDetails.getServiceName() + "' already exists");
        }

        service.setServiceName(serviceDetails.getServiceName());
        service.setServiceUrl(serviceDetails.getServiceUrl());
        service.setDescription(serviceDetails.getDescription());
        service.setPort(serviceDetails.getPort());
        service.setHealthCheckUrl(serviceDetails.getHealthCheckUrl());
        service.setIsActive(serviceDetails.getIsActive());
        service.setVersion(serviceDetails.getVersion());
        service.setEnvironment(serviceDetails.getEnvironment());
        service.setUpdatedAt(LocalDateTime.now());

        return serviceRegistryRepository.save(service);
    }

    public void deleteService(Long id) {
        ServiceRegistry service = serviceRegistryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        serviceRegistryRepository.delete(service);
    }

    public ServiceRegistry updateHeartbeat(String serviceName) {
        ServiceRegistry service = serviceRegistryRepository.findByServiceName(serviceName)
                .orElseThrow(() -> new RuntimeException("Service not found with name: " + serviceName));
        
        service.setLastHeartbeat(LocalDateTime.now());
        service.setIsActive(true);
        return serviceRegistryRepository.save(service);
    }

    public List<ServiceRegistry> getStaleServices(int minutesThreshold) {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(minutesThreshold);
        return serviceRegistryRepository.findStaleServices(threshold);
    }

    public ServiceRegistry deactivateService(Long id) {
        ServiceRegistry service = serviceRegistryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        
        service.setIsActive(false);
        service.setUpdatedAt(LocalDateTime.now());
        return serviceRegistryRepository.save(service);
    }

    public ServiceRegistry activateService(Long id) {
        ServiceRegistry service = serviceRegistryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        
        service.setIsActive(true);
        service.setUpdatedAt(LocalDateTime.now());
        service.setLastHeartbeat(LocalDateTime.now());
        return serviceRegistryRepository.save(service);
    }

    public long getActiveServicesCount() {
        return serviceRegistryRepository.countActiveServices();
    }

    public List<ServiceRegistry> getActiveServicesByEnvironment(String environment) {
        return serviceRegistryRepository.findActiveServicesByEnvironment(environment);
    }
}
