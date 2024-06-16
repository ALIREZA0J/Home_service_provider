package com.hsp.home_service_provider.service.offer;

import com.hsp.home_service_provider.repository.offer.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
}
