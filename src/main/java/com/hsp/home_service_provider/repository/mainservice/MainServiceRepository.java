package com.hsp.home_service_provider.repository.mainservice;



import com.hsp.home_service_provider.model.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MainServiceRepository extends JpaRepository<MainService,Long> {

    Optional<MainService> findMainServiceByServiceName(String serviceName);
}
