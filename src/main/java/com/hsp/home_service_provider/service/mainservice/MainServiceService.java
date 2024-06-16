package com.hsp.home_service_provider.service.mainservice;

import com.hsp.home_service_provider.repository.mainservice.MainServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainServiceService {

    private final MainServiceRepository mainServiceRepository;
}
