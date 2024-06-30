package com.hsp.home_service_provider.service.admin;

import com.hsp.home_service_provider.exception.AdminException;
import com.hsp.home_service_provider.model.Admin;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.repository.admin.AdminRepository;
import com.hsp.home_service_provider.service.mainservice.MainServiceService;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import com.hsp.home_service_provider.service.subservice.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final MainServiceService mainServiceService;
    private final SubServiceService subServiceService;
    private final SpecialistService specialistService;


    public Admin logIn(String gmail , String password){
        return adminRepository.findAdminByGmailAndPassword(gmail, password)
                .orElseThrow(() -> new AdminException("Gmail or Password is wrong."));
    }


    public MainService registerMainService(MainService mainService){
        return mainServiceService.register(mainService);
    }

    @Transactional
    public void deleteMainService(Long mainServiceId){
        mainServiceService.delete(mainServiceId);
    }

    public List<MainService> displayAllMainService(){
        return mainServiceService.showAll();
    }


    public SubService registerSubService(SubService subService, String mainServiceName){
        MainService mainService = mainServiceService.findByName(mainServiceName);
        subService.setMainService(mainService);
        return subServiceService.register(subService);
    }

    public void deleteSubService(String subServiceName){
        subServiceService.delete(subServiceName);
    }

    public SubService updateSubService(SubService subService){
        return subServiceService.update(subService);
    }


    public List<SubService> displaySubServiceOfMainService(String mainServiceName){
        return subServiceService.findSubServicesOfMainService(mainServiceName);
    }

    public void addSpecialistToSubService(String subServiceName, String specialistGmail){
        SubService subService = subServiceService.findByName(subServiceName);
        Specialist specialist = specialistService.findByGmail(specialistGmail);
        subServiceService.addSpecialistToSubService(subService,specialist);
    }

    public void removeSpecialistFromSubService(String subServiceName, String specialistGmail){
        SubService subService = subServiceService.findByName(subServiceName);
        Specialist specialist = specialistService.findByGmail(specialistGmail);
        subServiceService.removeSpecialistFromSubService(subService,specialist);
    }
}
