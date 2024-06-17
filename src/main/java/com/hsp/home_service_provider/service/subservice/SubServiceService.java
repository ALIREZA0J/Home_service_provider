package com.hsp.home_service_provider.service.subservice;

import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.subservice.SubServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;



    public SubService register(SubService subService){
        if (subServiceRepository.findSubServiceByName(subService.getName()).isPresent())
            throw new DuplicateException("Sub-Service with name: " + subService.getName() + " is already exist.");
        return subServiceRepository.save(subService);
    }

    @Transactional
    public void delete(String name){
        SubService subService = findByName(name);
        subServiceRepository.delete(subService);
    }

    public SubService findByName(String name){
        return subServiceRepository.findSubServiceByName(name)
                .orElseThrow(() -> new NotFoundException("Sub-Service with (name:" + name + ") not found."));
    }

    public SubService update(SubService subService){
        SubService subServiceFind = findByName(subService.getName());
        if (!subServiceFind.getDescription().equals(subService.getDescription())){
            if (subService.getDescription() != null){
                if (!subService.getDescription().isBlank())
                    subServiceFind.setDescription(subService.getDescription());
            }
        }
        if (!subServiceFind.getBasePrice().equals(subService.getBasePrice())){
            if (subService.getBasePrice() != null)
                subServiceFind.setBasePrice(subService.getBasePrice());
        }
        return subServiceRepository.save(subServiceFind);
    }
}
