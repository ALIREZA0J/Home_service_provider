package com.hsp.home_service_provider.service.specialist;

import com.hsp.home_service_provider.dto.specialist.SpecialistFilter;
import com.hsp.home_service_provider.exception.ConfirmationException;
import com.hsp.home_service_provider.exception.MismatchException;
import com.hsp.home_service_provider.exception.NotFoundException;
import com.hsp.home_service_provider.exception.SpecialistException;
import com.hsp.home_service_provider.model.*;
import com.hsp.home_service_provider.model.enums.SpecialistStatus;
import com.hsp.home_service_provider.repository.specialist.SpecialistRepository;
import com.hsp.home_service_provider.service.confirmation_token.ConfirmationTokenService;
import com.hsp.home_service_provider.service.email.EmailSender;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.specification.SpecialistSpecification;
import com.hsp.home_service_provider.utility.AvatarUtil;
import com.hsp.home_service_provider.utility.EmailUtil;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final OrderService orderService;
    private final Validation validation;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    @Transactional
    public Specialist register(Specialist specialist, String photoPath)  {
        validation.validate(specialist);
        if (specialistRepository.findSpecialistByGmail(specialist.getGmail()).isPresent())
            throw new SpecialistException("Specialist with (gmail: "+specialist.getGmail()+") is already exist");
        specialist.setAvatar(AvatarUtil.checkPhotoFileAndMakeAvatarForSpecialist(photoPath, specialist));
        specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
        Specialist savedSpecialist = specialistRepository.save(specialist);
        ConfirmationToken confirmationToken = confirmationTokenService
                .saveConfirmationToken(ConfirmationToken.builder().setSpecialist(savedSpecialist).build());
        emailSender.send(savedSpecialist.getGmail(),
                EmailUtil.buildEmail(savedSpecialist.getFirstName()+" "+savedSpecialist.getLastName(),
                        "http://localhost:8080/customer/confirm?token="+confirmationToken.getToken()));
        return savedSpecialist;
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null)
            throw new ConfirmationException("email already confirmed");
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ConfirmationException("token expired");
        confirmationTokenService.setConfirmedAt(token);
        makeActiveSpecialist(confirmationToken.getSpecialist());
        return "confirmed";
    }

    private void makeActiveSpecialist(Specialist specialist) {
        specialist.setIsActive(true);
        specialistRepository.save(specialist);
    }

    @Transactional
    public void changePassword(String gmail, String oldPassword, String newPassword, String confirmNewPassword){
        Specialist specialist = findByGmail(gmail);
        if (!passwordEncoder.matches(oldPassword, specialist.getPassword()))
            throw new MismatchException("Wrong old password");
        validation.checkPassword(newPassword);
        validation.checkSpecialistStatusIfItWasOtherThanAcceptedThrowException(specialist.getSpecialistStatus());
        if (!newPassword.equals(confirmNewPassword))
            throw new MismatchException("Password and repeat oldPassword do not match");
        specialist.setPassword(passwordEncoder.encode(confirmNewPassword));
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

    @Transactional
    public List<Order> displayOrdersRelatedToSpecialist(String gmail){
        Specialist specialist = findByGmail(gmail);
        ArrayList<Order> orders = new ArrayList<>();
        Set<SubService> subServices = specialist.getSubServices();
        for (int i = 0; i < subServices.size(); i++) {
            List<Order> ordersFind = orderService.findOrdersBySubServiceAndOrderStatus(subServices.iterator().next());
            orders.addAll(ordersFind);
        }
        return orders;
    }

    public void applyNegativeScoreAndCheckIfScoreOfSpecialistLessThanOneChangeStatus
            (Specialist specialist, long hoursDelay) {
        if (hoursDelay >= 1){
            specialist.setScore(specialist.getScore() - hoursDelay);
            if (specialist.getScore() < 0)
                specialist.setSpecialistStatus(SpecialistStatus.AWAITING_CONFIRMATION);
            specialistRepository.save(specialist);
        }
    }

    public void applyCommentScore(Specialist specialist,long commentScore){
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.ACCEPTED)){
            specialist.setScore(specialist.getScore()+commentScore);
            specialistRepository.save(specialist);
        }
    }

    public List<Specialist> filteredSpecialist(SpecialistFilter specialistFilter){
        Specification<Specialist> specialist = SpecialistSpecification.filterSpecialist(specialistFilter);
        return specialistRepository.findAll(specialist);
    }

    public List<Specialist> findNewSpecialist(){
        return specialistRepository.findSpecialistsBySpecialistStatus(SpecialistStatus.NEW);
    }

    public List<Specialist> findSuspendedSpecialist(){
        return specialistRepository.findSpecialistsBySpecialistStatus(SpecialistStatus.AWAITING_CONFIRMATION);
    }

    public void updateCredit(Specialist specialist){
        specialistRepository.save(specialist);
    }

}
