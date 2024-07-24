package com.hsp.home_service_provider.service.customer;

import com.hsp.home_service_provider.dto.customer.CustomerFilter;
import com.hsp.home_service_provider.exception.*;
import com.hsp.home_service_provider.model.*;
import com.hsp.home_service_provider.repository.customer.CustomerRepository;
import com.hsp.home_service_provider.service.address.AddressService;
import com.hsp.home_service_provider.service.comment.CommentService;
import com.hsp.home_service_provider.service.confirmation_token.ConfirmationTokenService;
import com.hsp.home_service_provider.service.email.EmailSender;
import com.hsp.home_service_provider.service.offer.OfferService;
import com.hsp.home_service_provider.service.order.OrderService;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import com.hsp.home_service_provider.service.subservice.SubServiceService;
import com.hsp.home_service_provider.specification.CustomerSpecification;
import com.hsp.home_service_provider.utility.EmailUtil;
import com.hsp.home_service_provider.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final AddressService addressService;
    private final OfferService offerService;
    private final SubServiceService subServiceService;
    private final Validation validation;
    private final SpecialistService specialistService;
    private final CommentService commentService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    public Customer register(Customer customer){
        validation.validate(customer);
        if (customerRepository.findCustomerByGmail(customer.getGmail()).isPresent())
            throw new DuplicateException("A customer with this gmail is already exist.");
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);
        ConfirmationToken confirmationToken = confirmationTokenService
                .saveConfirmationToken(ConfirmationToken.builder().setCustomer(savedCustomer).build());
        emailSender.send(savedCustomer.getGmail(),
                EmailUtil.buildEmail(savedCustomer.getFirstName()+" "+savedCustomer.getLastName(),
                        "http://localhost:8080/customer/confirm?token="+confirmationToken.getToken()));
        return savedCustomer;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt() != null)
            throw new ConfirmationException("email already confirmed");
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ConfirmationException("token expired");
        confirmationTokenService.setConfirmedAt(token);
        makeActiveCustomer(confirmationToken.getCustomer());
        return "confirmed";
    }

    @Transactional
    public Customer changePassword(String gmail, String oldPassword, String newPassword, String confirmNewPassword){
        Customer customer = findByGmail(gmail);
        if (!passwordEncoder.matches(oldPassword, customer.getPassword()))
            throw new MismatchException("Wrong old password");
        validation.checkPassword(newPassword);
        if (!newPassword.equals(confirmNewPassword))
            throw new MismatchException("new password and repeat password do not match");
        customer.setPassword(passwordEncoder.encode(confirmNewPassword));
        return customerRepository.save(customer);
    }

    public Customer findByGmail(String gmail){
        return customerRepository.findCustomerByGmail(gmail)
                .orElseThrow(() -> new NotFoundException("Customer with (gmail:" + gmail + ") not found."));
    }

    public Address registerNewAddress(Address address, String gmail){
        Customer customer = findByGmail(gmail);
        address.setCustomer(customer);
        return addressService.register(address);
    }

    public Order registerNewOrder(Order order,String gmail){
        SubService subService = subServiceService.findByName(order.getSubService().getName());
        validation.checkProposedPriceNotLessThanSubService(order.getProposedPrice(), subService.getBasePrice());
        if (order.getProposedPrice() == null)
            order.setProposedPrice(subService.getBasePrice());
        order.setCustomer(findByGmail(gmail));
        order.setSubService(subService);
        order.setAddress(addressService.findById(order.getAddress().getId()));
        return orderService.register(order);
    }

    public List<Offer> displayOffersOfOrderInWaitingStatusAndSortByPriceAndScore(Long orderId){
        Order order = orderService.findById(orderId);
        return offerService.displayOffersOfOrderSortByPriceAndScore(order);
    }

    public void acceptOfferForOrder(Long offerId){
        offerService.acceptedOffer(offerId);
    }

    @Transactional
    public List<Order> displayOrdersInWaitingForSpecialistComeToCustomerPlace(String gmail){
        Customer customer = findByGmail(gmail);
        return orderService.findOrdersInWaitingForSpecialistComeToLocation(customer);
    }
    public void registrationOfTheStartOfWork(Long orderId){
        Order order = orderService.findById(orderId);
        if (order.getDateOfWork().isEqual(LocalDate.now()) ){
            if (order.getTimeOfWork().equals(LocalTime.now()) || order.getTimeOfWork().isBefore(LocalTime.now())){
                orderService.changeStatusOfOrderToStart(orderId);
            }
            else throw new OutOfTimeException("Changing the state to start at the present time is not allowed.");
        }
        else throw new OutOfTimeException("Changing the state to start at the present time is not allowed.");

    }
    @Transactional
    public List<Order> displayOrdersStarted(String gmail){
        Customer customer = findByGmail(gmail);
        return orderService.findOrderInStartedStatus(customer);
    }

    public List<Order> displayDoneOrders(String gmail){
        Customer customer = findByGmail(gmail);
        return orderService.findOrderInDoneStatus(customer);
    }
    public void endOfWorkRegistration(Long orderId){
        Order order = orderService.findById(orderId);
        checkForNegativeScore(order);
        orderService.changeStatusOfOrderToDone(orderId);
    }

    public void checkForNegativeScore(Order order){
        Offer offerAccepted = offerService.findOfferOfOrderAccepted(order);
        Integer daysOfWork = offerAccepted.getDaysOfWork();
        int extraDays = (daysOfWork - 1);
        LocalDate dateOfEndWork = order.getDateOfWork().plusDays(extraDays);
        LocalTime durationOfWork = offerAccepted.getDurationOfWork();
        LocalTime plusHours = order.getTimeOfWork().plusHours(durationOfWork.getHour());
        LocalTime timeOfEndWork = plusHours.plusMinutes(durationOfWork.getMinute());

        if (LocalDate.now().isEqual(dateOfEndWork)){
            long hoursDelay = ChronoUnit.HOURS.between(timeOfEndWork,LocalTime.now());
            specialistService
                    .applyNegativeScoreAndCheckIfScoreOfSpecialistLessThanOneChangeStatus
                            (offerAccepted.getSpecialist(), hoursDelay);
        } else if (LocalDate.now().isAfter(dateOfEndWork)){
            LocalDateTime localDateTimeEnd = LocalDateTime.of(dateOfEndWork, timeOfEndWork);
            long hoursDelay = ChronoUnit.HOURS.between(localDateTimeEnd, LocalDateTime.now());
            specialistService
                    .applyNegativeScoreAndCheckIfScoreOfSpecialistLessThanOneChangeStatus
                            (offerAccepted.getSpecialist(), hoursDelay);
        }
    }

    public Comment registerNewComment(Comment comment){
        comment.setCustomer(findByGmail(comment.getCustomer().getGmail()));
        Offer offer = offerService.findById(comment.getOffer().getId());
        if (offer.getComment() != null)
            throw new CommentException("Offer with (id:"+offer.getId()+") had comment.");
        comment.setOffer(offer);
        return commentService.register(comment);
    }

    public Offer displayOfferAcceptForOrder(Long orderId){
        Order order = orderService.findById(orderId);
        return offerService.findOfferOfOrderAccepted(order);
    }
    public List<Customer> filteredCustomer(CustomerFilter customerFilter){
        Specification<Customer> customer = CustomerSpecification.filterSpecialist(customerFilter);
        return customerRepository.findAll(customer);
    }

    @Transactional
    public void payOnline(Long orderId){
        Offer offer = changeOrderStatusAndFindAcceptedOffer(orderId);
        Specialist specialist = offer.getSpecialist();
        long price =(long) (offer.getOfferPrice()* 0.7) ;
        specialist.setCredit(price + specialist.getCredit());
        specialistService.updateCredit(specialist);
    }

    @Transactional
    public void payByCredit(String gmail,Long orderId){
        Customer customer = findByGmail(gmail);
        Offer offer = changeOrderStatusAndFindAcceptedOffer(orderId);
        if (customer.getCredit() >= offer.getOfferPrice() ){
            Specialist specialist = offer.getSpecialist();
            long price =(long) (offer.getOfferPrice()* 0.7) ;
            specialist.setCredit(price + specialist.getCredit());
            specialistService.updateCredit(specialist);
            customer.setCredit(customer.getCredit()-offer.getOfferPrice());
            customerRepository.save(customer);
        }else throw new CustomerException("Credit not enough");
    }

    private Offer changeOrderStatusAndFindAcceptedOffer(Long orderId) {
        orderService.changeStatusOfOrderToPaid(orderId);
        Order order = orderService.findById(orderId);
        return offerService.findOfferOfOrderAccepted(order);
    }

    private void makeActiveCustomer(Customer customer){
        customer.setIsActive(true);
        customerRepository.save(customer);
    }
}
