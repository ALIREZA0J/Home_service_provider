package com.hsp.home_service_provider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AvatarMapper {

    AvatarMapper INSTANCE = Mappers.getMapper(AvatarMapper.class);
}
