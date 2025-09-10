package com.example.eureka.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_registry")
public class ServiceRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Service name is required")
    @Column(name = "service_name", nullable = false, unique = true)
    private String serviceName;

    @NotBlank(message = "Service URL is required")
    @Column(name = "service_url", nullable = false)
    private String serviceUrl;

    @NotBlank(message = "Service description is required")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Port is required")
    @Positive(message = "Port must be positive")
    @Column(name = "port", nullable = false)
    private Integer port;

    @Column(name = "health_check_url")
    private String healthCheckUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    @Column(name = "version")
    private String version = "1.0.0";

    @Column(name = "environment")
    private String environment = "development";

    // Default constructor
    public ServiceRegistry() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastHeartbeat = LocalDateTime.now();
    }

    // Constructor with fields
    public ServiceRegistry(String serviceName, String serviceUrl, String description, Integer port) {
        this();
        this.serviceName = serviceName;
        this.serviceUrl = serviceUrl;
        this.description = description;
        this.port = port;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    public void setHealthCheckUrl(String healthCheckUrl) {
        this.healthCheckUrl = healthCheckUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "ServiceRegistry{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", description='" + description + '\'' +
                ", port=" + port +
                ", healthCheckUrl='" + healthCheckUrl + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastHeartbeat=" + lastHeartbeat +
                ", version='" + version + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
