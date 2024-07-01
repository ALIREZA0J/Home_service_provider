package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.customer.CustomerResponse;
import com.hsp.home_service_provider.dto.customer.CustomerSaveRequest;
import com.hsp.home_service_provider.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer customerSaveRequestToModel(CustomerSaveRequest request);

    CustomerResponse customerModelToCustomerResponse(Customer customer);
}
