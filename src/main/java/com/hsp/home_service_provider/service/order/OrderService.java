package com.hsp.home_service_provider.service.order;

import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.OrderException;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.enums.OrderStatus;
import com.hsp.home_service_provider.repository.order.OrderRepository;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final Validation validation;

    public Order register(Order order){
        if (validation.checkProposedDateNotBeforeToday(order.getDateOfWork()))
            throw new OrderException("Date of work most be in future.");
        validation.checkDescriptionNotBlank(order.getDescription());
        validation.checkDescriptionPattern(order.getDescription());
        order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST);
        return orderRepository.save(order);
    }

    public Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with (id:"+id+") not found."));
    }

    public void changeStatusOfOrderToWaitingForSpecialistSelection(Long orderId){
        Order order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_THE_SUGGESTION_OF_SPECIALIST)) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
            orderRepository.save(order);
        }
    }
    public void changeStatusOfOrderToWaitingForSpecialistGoToCustomerPlace(Long orderId){
        Order order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_SPECIALIST_SELECTION)) {
            order.setOrderStatus(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE);
            orderRepository.save(order);
        }
    }
    public void changeStatusOfOrderToStart(Long orderId){
        Order order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME_TO_YOUR_PLACE)) {
            order.setOrderStatus(OrderStatus.STARTED);
            orderRepository.save(order);
        }
    }
    public void changeStatusOfOrderToDone(Long orderId){
        Order order = findById(orderId);
        if (order.getOrderStatus().equals(OrderStatus.STARTED)) {
            order.setOrderStatus(OrderStatus.DONE);
            orderRepository.save(order);
        }
    }


}
