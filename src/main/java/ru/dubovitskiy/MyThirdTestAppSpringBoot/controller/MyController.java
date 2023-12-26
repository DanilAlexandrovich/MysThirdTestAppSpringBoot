package ru.dubovitskiy.MysThirdTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.exception.ValidationFailedException;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.model.*;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.service.ModifyResponseService;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.service.ValidationService;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.util.DifferenceTime;

import javax.validation.Valid;
import java.text.ParseException;


@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) throws ParseException {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime("Transmission time=" +
                        DifferenceTime.differenceCalculate(request))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        log.info("response: {}", response);

        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("ValidationFailedException: {} {}", response, bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_CODE_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UID123);
            log.error("UnsupportedCodeException: {} {} ", response, bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.error("Exception: {} {}", response, bindingResult.getFieldError().getDefaultMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponseService.modify(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}