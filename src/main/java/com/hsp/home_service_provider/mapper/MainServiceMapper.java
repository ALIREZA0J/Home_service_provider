package com.hsp.home_service_provider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MainServiceMapper {
    MainServiceMapper INSTANCE = Mappers.getMapper(MainServiceMapper.class);
}
