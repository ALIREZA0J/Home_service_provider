package com.hsp.home_service_provider.service.subservice;

import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.subservice.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;



    public SubService register(SubService subService){
        if (subServiceRepository.findSubServiceByName(subService.getName()).isPresent())
            throw new DuplicateException("Sub-Service with name: " + subService.getName() + " is already exist.");
        return subServiceRepository.save(subService);
    }
}
