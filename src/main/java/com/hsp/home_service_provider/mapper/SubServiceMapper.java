package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.sub_service.SubServiceResponse;
import com.hsp.home_service_provider.dto.sub_service.SubServiceSaveRequest;
import com.hsp.home_service_provider.dto.sub_service.SubServiceUpdateRequest;
import com.hsp.home_service_provider.model.SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubServiceMapper {
    SubServiceMapper INSTANCE = Mappers.getMapper(SubServiceMapper.class);
    SubService subServiceSaveRequestToModel(SubServiceSaveRequest request);
    SubServiceResponse subServiceModelToSubServiceResponse(SubService subService);
    SubService subServiceUpdateRequestToModel(SubServiceUpdateRequest request);
}
