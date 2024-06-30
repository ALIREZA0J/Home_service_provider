package com.hsp.home_service_provider.controller;


import com.hsp.home_service_provider.dto.main_service.MainServiceResponse;
import com.hsp.home_service_provider.dto.main_service.MainServiceSaveRequest;
import com.hsp.home_service_provider.mapper.MainServiceMapper;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
