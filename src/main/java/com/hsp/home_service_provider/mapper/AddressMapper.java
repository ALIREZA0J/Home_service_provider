package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.address.AddressResponse;
import com.hsp.home_service_provider.dto.address.AddressSaveRequest;
import com.hsp.home_service_provider.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressSaveRequestToModel(AddressSaveRequest request);

    AddressResponse addressModelToAddressResponse(Address address);
}
