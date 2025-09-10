package com.example.eureka.repository;

import com.example.eureka.model.ServiceRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRegistryRepository extends JpaRepository<ServiceRegistry, Long> {

    Optional<ServiceRegistry> findByServiceName(String serviceName);

    List<ServiceRegistry> findByIsActiveTrue();

    List<ServiceRegistry> findByEnvironment(String environment);

    List<ServiceRegistry> findByVersion(String version);

    @Query("SELECT s FROM ServiceRegistry s WHERE s.lastHeartbeat < :threshold")
    List<ServiceRegistry> findStaleServices(@Param("threshold") LocalDateTime threshold);

    @Query("SELECT s FROM ServiceRegistry s WHERE s.serviceName LIKE %:name%")
    List<ServiceRegistry> findByServiceNameContaining(@Param("name") String name);

    @Query("SELECT s FROM ServiceRegistry s WHERE s.isActive = true AND s.environment = :environment")
    List<ServiceRegistry> findActiveServicesByEnvironment(@Param("environment") String environment);

    boolean existsByServiceName(String serviceName);

    @Query("SELECT COUNT(s) FROM ServiceRegistry s WHERE s.isActive = true")
    long countActiveServices();
}
