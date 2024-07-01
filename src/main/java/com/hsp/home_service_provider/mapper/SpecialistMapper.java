package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.specialist.SpecialistResponse;
import com.hsp.home_service_provider.model.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpecialistMapper {
    SpecialistMapper INSTANCE = Mappers.getMapper(SpecialistMapper.class);

    SpecialistResponse specialistModelToSpecialistResponse(Specialist specialist);
}
