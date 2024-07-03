package com.hsp.home_service_provider.controller;

import com.hsp.home_service_provider.dto.order.OrderResponse;
import com.hsp.home_service_provider.dto.specialist.SpecialistChangePasswordRequest;
import com.hsp.home_service_provider.dto.specialist.SpecialistResponse;
import com.hsp.home_service_provider.dto.specialist.SpecialistSaveRequest;
import com.hsp.home_service_provider.mapper.OrderMapper;
import com.hsp.home_service_provider.mapper.SpecialistMapper;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialist")
public class SpecialistController {
    private final SpecialistService specialistService;

    @PostMapping("/register")
    public ResponseEntity<SpecialistResponse> register(@RequestBody SpecialistSaveRequest request,
                                                       @RequestParam String photoPath){
        Specialist specialist = SpecialistMapper.INSTANCE.specialistSaveRequestToModel(request);
        Specialist registerSpecialist = specialistService.register(specialist, photoPath);
        return new ResponseEntity<>
                (SpecialistMapper.INSTANCE.specialistModelToSpecialistResponse(registerSpecialist), HttpStatus.CREATED);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody SpecialistChangePasswordRequest request){
        specialistService.changePassword(request.gmail(), request.password1(), request.password2());
        return new ResponseEntity<>("Password change successfully.",HttpStatus.OK);
    }

    @GetMapping("/display-Orders-related-to-specialist")
    public ResponseEntity<List<OrderResponse>> displayOrderRelatedToSpecialistSubService(@RequestParam String gmail){
        List<Order> orders = specialistService.displayOrdersRelatedToSpecialist(gmail);
        ArrayList<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(OrderMapper.INSTANCE.modelToOrderResponse(order));
        }
        return new ResponseEntity<>(orderResponses,HttpStatus.OK);
    }

}
