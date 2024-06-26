package com.hsp.home_service_provider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE =Mappers.getMapper(AdminMapper.class);
}
