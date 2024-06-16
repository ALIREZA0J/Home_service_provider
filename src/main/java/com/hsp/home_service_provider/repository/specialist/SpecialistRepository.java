package com.hsp.home_service_provider.repository.specialist;


import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface SpecialistRepository extends JpaRepository<Specialist,Long> {

    Optional<Specialist> findSpecialistByGmail(String gmail);

    Optional<Specialist> findSpecialistByGmailAndPassword(String gmail , String password);

    List<Specialist> findSpecialistsBySpecialistStatus(SpecialistStatus specialistStatus);
}
