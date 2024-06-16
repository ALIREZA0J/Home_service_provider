package com.hsp.home_service_provider.repository.address;

import com.hsp.home_service_provider.model.Address;
import com.hsp.home_service_provider.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findAddressesByCustomer(Customer customer);
}
