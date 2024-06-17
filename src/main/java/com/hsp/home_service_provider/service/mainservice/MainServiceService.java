package com.hsp.home_service_provider.service.mainservice;

import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.repository.mainservice.MainServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MainServiceService {

    private final MainServiceRepository mainServiceRepository;



    public MainService register(MainService mainService){
        if (mainServiceRepository.findMainServiceByServiceName(mainService.getServiceName()).isPresent())
            throw new DuplicateException("A MainService with name("+mainService.getServiceName()+") is already exist.");
        return mainServiceRepository.save(mainService);
    }

    @Transactional
    public void delete(Long id){
        MainService mainService = findById(id);
        mainServiceRepository.delete(mainService);
    }

    public MainService findById(Long id){
        return mainServiceRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Main-Service with id:"+id+" not found."));
    }
}
