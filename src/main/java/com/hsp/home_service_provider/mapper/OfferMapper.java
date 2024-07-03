package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.offer.OfferResponse;
import com.hsp.home_service_provider.dto.offer.OfferSaveRequest;
import com.hsp.home_service_provider.model.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferMapper {
    OfferMapper INSTANCE = Mappers.getMapper(OfferMapper.class);
    Offer offerSaveRequestToModel(OfferSaveRequest request);
    OfferResponse offerModelToOfferResponse(Offer offer);
}
