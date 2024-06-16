package com.hsp.home_service_provider.service.admin;

import com.hsp.home_service_provider.exception.AdminException;
import com.hsp.home_service_provider.exception.MismatchException;
import com.hsp.home_service_provider.model.Admin;
import com.hsp.home_service_provider.repository.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin logIn(String gmail , String password){
        return adminRepository.findAdminByGmailAndPassword(gmail, password)
                .orElseThrow(() -> new AdminException("Gmail or Password is wrong."));
    }

    public void changePassword(Admin admin,String password1,String password2){
        if (!password1.equals(password2))
            throw new MismatchException("Password and repeat password do not match");
        admin.setPassword(password1);
        adminRepository.save(admin);
    }

}
