package com.hsp.home_service_provider.repository.customer;



import com.hsp.home_service_provider.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findCustomerByGmail(String gmail);

    Optional<Customer> findCustomerByGmailAndPassword(String gmail , String password);

}
