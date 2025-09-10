package com.example.gateway.client;

import com.example.gateway.dto.ServiceRegistryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "eureka-service")
public interface EurekaServiceClient {

    @GetMapping("/api/services")
    List<ServiceRegistryDTO> getAllServices();

    @GetMapping("/api/services/active")
    List<ServiceRegistryDTO> getActiveServices();

    @GetMapping("/api/services/{id}")
    ServiceRegistryDTO getServiceById(@PathVariable("id") Long id);

    @GetMapping("/api/services/name/{serviceName}")
    ServiceRegistryDTO getServiceByName(@PathVariable("serviceName") String serviceName);

    @GetMapping("/api/services/environment/{environment}")
    List<ServiceRegistryDTO> getServicesByEnvironment(@PathVariable("environment") String environment);

    @GetMapping("/api/services/version/{version}")
    List<ServiceRegistryDTO> getServicesByVersion(@PathVariable("version") String version);

    @GetMapping("/api/services/search")
    List<ServiceRegistryDTO> searchServicesByName(@RequestParam("name") String name);

    @GetMapping("/api/services/stale")
    List<ServiceRegistryDTO> getStaleServices(@RequestParam("minutes") int minutes);

    @GetMapping("/api/services/count")
    Long getActiveServicesCount();

    @GetMapping("/api/services/health")
    String health();

    @GetMapping("/api/services/status")
    Object getServiceStatus();
}
