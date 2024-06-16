package com.hsp.home_service_provider.service.subservice;

import com.hsp.home_service_provider.repository.subservice.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;
}
