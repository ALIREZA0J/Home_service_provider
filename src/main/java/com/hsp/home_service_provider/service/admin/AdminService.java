package com.hsp.home_service_provider.service.admin;

import com.hsp.home_service_provider.exception.AdminException;
import com.hsp.home_service_provider.exception.MismatchException;
import com.hsp.home_service_provider.model.Admin;
import com.hsp.home_service_provider.model.MainService;
import com.hsp.home_service_provider.repository.admin.AdminRepository;
import com.hsp.home_service_provider.service.mainservice.MainServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final MainServiceService mainServiceService;

    public Admin logIn(String gmail , String password){
        return adminRepository.findAdminByGmailAndPassword(gmail, password)
                .orElseThrow(() -> new AdminException("Gmail or Password is wrong."));
    }

    public void changePassword(Long id,String password1,String password2){
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminException("Admin with id:" + id + " not found."));
        if (!password1.equals(password2))
            throw new MismatchException("Password and repeat password do not match");
        admin.setPassword(password1);
        adminRepository.save(admin);
    }

    public MainService registerMainService(MainService mainService){
        return mainServiceService.register(mainService);
    }

    @Transactional
    public void deleteMainService(Long id){
        mainServiceService.delete(id);
    }
}
