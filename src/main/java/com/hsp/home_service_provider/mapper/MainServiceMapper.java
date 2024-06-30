package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.main_service.MainServiceResponse;
import com.hsp.home_service_provider.dto.main_service.MainServiceSaveRequest;
import com.hsp.home_service_provider.model.MainService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MainServiceMapper {
    MainServiceMapper INSTANCE = Mappers.getMapper(MainServiceMapper.class);

    MainService mainServiceSaveRequestToModel(MainServiceSaveRequest request);

    MainServiceResponse mainServiceModelToResponse(MainService mainService);
}
