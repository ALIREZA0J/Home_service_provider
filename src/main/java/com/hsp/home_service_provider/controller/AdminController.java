package com.hsp.home_service_provider.controller;


import com.hsp.home_service_provider.dto.main_service.MainServiceResponse;
import com.hsp.home_service_provider.dto.main_service.MainServiceSaveRequest;
import com.hsp.home_service_provider.dto.specialist.SpecialistResponse;
import com.hsp.home_service_provider.dto.sub_service.SubServiceResponse;
import com.hsp.home_service_provider.dto.sub_service.SubServiceSaveRequest;
import com.hsp.home_service_provider.dto.sub_service.SubServiceUpdateRequest;
import com.hsp.home_service_provider.mapper.MainServiceMapper;
import com.hsp.home_service_provider.mapper.SpecialistMapper;
import com.hsp.home_service_provider.mapper.SubServiceMapper;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.SubService;
import com.hsp.home_service_provider.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register-Main_service")
    public ResponseEntity<MainServiceResponse> registerMainService(@RequestBody MainServiceSaveRequest request){
        MainService mainService = MainServiceMapper.INSTANCE.mainServiceSaveRequestToModel(request);
        MainService registerMainService = adminService.registerMainService(mainService);
        return new ResponseEntity<>
                (MainServiceMapper.INSTANCE.mainServiceModelToResponse(registerMainService), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-Main_Service")
    public String deleteMainService(@RequestParam Long mainServiceId){
        adminService.deleteMainService(mainServiceId);
        return "Message: { main-service with (id="+mainServiceId+") deleted successfully.}";
    }

    @GetMapping("/display-Main-Services")
    public ResponseEntity<List<MainServiceResponse>> displayAllMainService(){
        List<MainService> mainServices = adminService.displayAllMainService();
        List<MainServiceResponse> mainServiceResponses = new ArrayList<>();
        for (MainService mainService : mainServices) {
            mainServiceResponses.add(MainServiceMapper.INSTANCE.mainServiceModelToResponse(mainService));
        }
        return new ResponseEntity<>(mainServiceResponses,HttpStatus.OK);
    }

    @PostMapping("/register-Sub_service")
    public ResponseEntity<SubServiceResponse> registerSubService(@RequestBody SubServiceSaveRequest request){
        SubService subService = SubServiceMapper.INSTANCE.subServiceSaveRequestToModel(request);
        SubService registerSubService = adminService
                .registerSubService(subService, subService.getMainService().getServiceName());
        return new ResponseEntity<>(SubServiceMapper.INSTANCE.subServiceModelToSubServiceResponse(registerSubService),HttpStatus.CREATED);
    }

    @PutMapping("/update-Sub-Service")
    public ResponseEntity<SubServiceResponse> updateSubService(@RequestBody SubServiceUpdateRequest request){
        SubService subService = SubServiceMapper.INSTANCE.subServiceUpdateRequestToModel(request);
        SubService updateSubService = adminService.updateSubService(subService);
        return new ResponseEntity<>(SubServiceMapper.INSTANCE.subServiceModelToSubServiceResponse(updateSubService), HttpStatus.OK);
    }

    @DeleteMapping("/delete-Sub_service")
    public String deleteSubService(@RequestParam String subServiceName){
        adminService.deleteSubService(subServiceName);
        return "Message: { Sub-Service ("+subServiceName+") deleted successfully. }";
    }

    @GetMapping("/display-SubServices-of-MainService")
    public ResponseEntity<List<SubServiceResponse>> displaySubServiceOfMainService(@RequestParam String mainServiceName){
        List<SubService> subServices = adminService.displaySubServiceOfMainService(mainServiceName);
        List<SubServiceResponse> subServiceResponses = new ArrayList<>();
        for (SubService subService : subServices) {
            subServiceResponses.add(SubServiceMapper.INSTANCE.subServiceModelToSubServiceResponse(subService));
        }
        return new ResponseEntity<>(subServiceResponses,HttpStatus.OK);
    }

    @PutMapping("/change-status-of-specialist")
    public ResponseEntity<SpecialistResponse> changeSpecialistStatusToAccept(@RequestParam String gmail){
        Specialist specialist = adminService.changeSpecialistStatusToAccept(gmail);
        return new ResponseEntity<>
                (SpecialistMapper.INSTANCE.specialistModelToSpecialistResponse(specialist),HttpStatus.OK);
    }

    @PostMapping("/add-specialist-to-sub-service")
    public ResponseEntity<String> addSpecialistToSubService(@RequestBody SpecialistSubServiceRequest request){
        adminService.addSpecialistToSubService(request.subServiceName(),request.specialistGmail());
        return new ResponseEntity<>
                ("The specialist with the desired email was added to the sub-service",HttpStatus.OK);
    }

    @DeleteMapping("/remove-specialist-to-sub-service")
    public ResponseEntity<String> removeSpecialistFromSubService(@RequestBody SpecialistSubServiceRequest request){
        adminService.removeSpecialistFromSubService(request.subServiceName(),request.specialistGmail());
        return new ResponseEntity<>
                ("The specialist with the desired email was removed from the sub-service",HttpStatus.OK);
    }
}
