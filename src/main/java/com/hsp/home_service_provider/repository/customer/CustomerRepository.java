package com.hsp.home_service_provider.repository.customer;



import com.hsp.home_service_provider.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> , JpaSpecificationExecutor<Customer> {

    Optional<Customer> findCustomerByGmail(String gmail);

    Optional<Customer> findCustomerByGmailAndPassword(String gmail , String password);

}
