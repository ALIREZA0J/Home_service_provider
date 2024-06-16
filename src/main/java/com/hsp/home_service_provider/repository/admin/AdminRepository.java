package com.hsp.home_service_provider.repository.admin;



import com.hsp.home_service_provider.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {


}
