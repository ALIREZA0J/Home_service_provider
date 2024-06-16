package com.hsp.home_service_provider.repository.order;




import com.hsp.home_service_provider.model.Customer;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findOrdersByCustomer(Customer customer);

    List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, OrderStatus orderStatus);

    List<Order> findOrdersBySubServiceAndOrderStatus(SubService subService, OrderStatus orderStatus);


}
