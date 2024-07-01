package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.order.OrderResponse;
import com.hsp.home_service_provider.dto.order.OrderSaveRequest;
import com.hsp.home_service_provider.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderSaveRequestToModel(OrderSaveRequest request);

    OrderResponse modelToOrderResponse(Order order);
}
