package com.hsp.home_service_provider.repository.subservice;




import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface SubServiceRepository extends JpaRepository<SubService,Long> {


    List<SubService> findSubServicesByMainService(MainService mainService);

    Optional<SubService> findSubServiceByName(String name);

}
