package com.hsp.home_service_provider.exception;

import com.hsp.home_service_provider.dto.exception.ExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AbsenceException.class)
    public ResponseEntity<ExceptionDTO> absenceException(AbsenceException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ExceptionDTO> addressException(AddressException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ExceptionDTO> adminException(AdminException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AvatarException.class)
    public ResponseEntity<ExceptionDTO> avatarException(AvatarException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ExceptionDTO> commentException(CommentException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ExceptionDTO> customerException(CustomerException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DescriptionException.class)
    public ResponseEntity<ExceptionDTO> descriptionException(DescriptionException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ExceptionDTO> duplicationException(DuplicateException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionDTO> fileNotFoundException(FileNotFoundException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ImageInputStreamException.class)
    public ResponseEntity<ExceptionDTO> imageInputStreamException(ImageInputStreamException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MainServiceException.class)
    public ResponseEntity<ExceptionDTO> mainServiceException(MainServiceException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<ExceptionDTO> misMatchException(MismatchException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> notFoundException(NotFoundException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<ExceptionDTO> notValidException(NotValidException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(OfferException.class)
    public ResponseEntity<ExceptionDTO> offerException(OfferException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ExceptionDTO> orderException(OrderException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ProposedPriceException.class)
    public ResponseEntity<ExceptionDTO> proposedPriceException(ProposedPriceException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SpecialistException.class)
    public ResponseEntity<ExceptionDTO> specialistException(SpecialistException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SubServiceException.class)
    public ResponseEntity<ExceptionDTO> subServiceException(SubServiceException e){
        log.warn(e.getMessage());
        ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
}
