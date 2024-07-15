package com.hsp.home_service_provider.controller;

import com.hsp.home_service_provider.dto.payment.PaymentDTO;
import com.hsp.home_service_provider.model.Offer;
import com.hsp.home_service_provider.service.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.hsp.home_service_provider.utility.CaptchaGenerator.generateCaptchaCode;
import static com.hsp.home_service_provider.utility.CaptchaGenerator.generateCaptchaImage;

@Controller
@ResponseBody
@RequiredArgsConstructor
@Validated
public class PaymentController {

    private final CustomerService customerService;


    @GetMapping("/payment")
    public ModelAndView getPaymentForm(@RequestParam Long orderId ) {
        PaymentDTO paymentDTO = new PaymentDTO();
        ModelAndView mav = new ModelAndView("payment");
        mav.addObject("paymentDTO", paymentDTO);
        paymentDTO.setOrderId(orderId);
        Offer offer = customerService.displayOfferAcceptForOrder(orderId);
        String totalAmountStr = String.format("%d", offer.getOfferPrice());
        mav.addObject("totalAmount",totalAmountStr);
        return mav;
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String captchaCode = generateCaptchaCode();
        HttpSession session = request.getSession();
        session.setAttribute("captchaCode", captchaCode);
        BufferedImage image = generateCaptchaImage(captchaCode);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@ModelAttribute("paymentDTO")
                                     @Valid PaymentDTO paymentDTO, BindingResult result, HttpSession session) {

        String captchaCode = (String) session.getAttribute("captchaCode");
        if (!paymentDTO.getCaptcha().equals(captchaCode)) {
            return new ResponseEntity<>("Invalid CAPTCHA code", HttpStatus.NOT_ACCEPTABLE);
        }
        if (result.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < result.getFieldErrors().size(); i++) {
                stringBuilder.append(result.getFieldErrors().get(i));
                stringBuilder.append(", ");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>("payment",HttpStatus.OK);

    }
}
