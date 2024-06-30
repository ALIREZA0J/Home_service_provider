package com.hsp.home_service_provider.service.subservice;

import com.hsp.home_service_provider.exception.AbsenceException;
import com.hsp.home_service_provider.exception.DuplicateException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.SubServiceException;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.subservice.SubServiceRepository;
import com.hsp.home_service_provider.service.mainservice.MainServiceService;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final MainServiceService mainServiceService;
    private final Validation validation;


    public SubService register(SubService subService){
        if (subServiceRepository.findSubServiceByName(subService.getName()).isPresent())
            throw new DuplicateException("Sub-Service with name: " + subService.getName() + " is already exist.");
        if (subService.getBasePrice()<300_000)
            throw new SubServiceException("The minimum sub-service price is 300,000");
        validation.checkSubServiceNamePattern(subService.getName());
        return subServiceRepository.save(subService);
    }

    @Transactional
    public void delete(String name){
        SubService subService = findByName(name);
        subServiceRepository.delete(subService);
    }

    @Transactional
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

    public List<SubService> findSubServicesOfMainService(String mainServiceName){
        MainService mainService = mainServiceService.findByName(mainServiceName);
        List<SubService> subServicesByMainService = subServiceRepository.findSubServicesByMainService(mainService);
        if (subServicesByMainService.isEmpty())
            throw new SubServiceException("Main-Service ("+mainServiceName+") has no any sub-service.");
        return subServicesByMainService;
    }

    @Transactional
    public void addSpecialistToSubService(SubService subService, Specialist specialist){
        if (subService.getSpecialists().contains(specialist))
            throw new DuplicateException("Specialist for services, it is repetitive.");
        subService.getSpecialists().add(specialist);
        subServiceRepository.save(subService);
    }

    public void removeSpecialistFromSubService(SubService subService, Specialist specialist){
        if (!subService.getSpecialists().contains(specialist))
            throw new AbsenceException("No specialist in the desired service.");
        subService.getSpecialists().remove(specialist);
        subServiceRepository.save(subService);
    }
}
