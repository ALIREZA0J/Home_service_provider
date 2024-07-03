package com.hsp.home_service_provider.service.specialist;

import com.hsp.home_service_provider.exception.MismatchException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.SpecialistException;
import com.hsp.home_service_provider.model.Avatar;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;
import com.hsp.home_service_provider.repository.specialist.SpecialistRepository;
import com.hsp.home_service_provider.utility.AvatarUtil;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final Validation validation;

    @Transactional
    public Specialist register(Specialist specialist, String photoPath)  {
        validation.validate(specialist);
        if (specialistRepository.findSpecialistByGmail(specialist.getGmail()).isPresent())
            throw new SpecialistException("Specialist with (gmail: "+specialist.getGmail()+") is already exist");
        Avatar avatar = AvatarUtil.checkPhotoFileAndMakeAvatarForSpecialist(photoPath, specialist);
        specialist.setAvatar(avatar);
        specialist.setRegistrationDate(LocalDate.now());
        specialist.setSpecialistStatus(SpecialistStatus.NEW);
        specialist.setScore(0.0);
        specialist.setCredit(0L);
        return specialistRepository.save(specialist);
    }

    public Specialist findById(Long id){
        return specialistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Specialist with (id:"+id+") not found."));
    }

    @Transactional
    public void changePassword(String gmail, String password1,String password2){
        Specialist specialist = findByGmail(gmail);
        validation.checkSpecialistStatusIfItWasOtherThanAcceptedThrowException(specialist.getSpecialistStatus());
        if (!password1.equals(password2))
            throw new MismatchException("Password and repeat password do not match");
        specialist.setPassword(password2);
        validation.validate(specialist);
        specialistRepository.save(specialist);
    }

    @Transactional
    public Specialist findByGmail(String gmail){
        return specialistRepository.findSpecialistByGmail(gmail)
                .orElseThrow(() -> new NotFoundException("Specialist with (gmail:" + gmail + ") not found."));
    }

    @Transactional
    public Specialist changeSpecialistStatusToAccept(String gmail){
        Specialist specialist = findByGmail(gmail);
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        return specialistRepository.save(specialist);
    }


}
