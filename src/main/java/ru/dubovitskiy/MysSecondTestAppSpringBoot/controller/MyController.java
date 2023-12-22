package ru.dubovitskiy.MysSecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dubovitskiy.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.dubovitskiy.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.dubovitskiy.MySecondTestAppSpringBoot.model.*;
import ru.dubovitskiy.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.dubovitskiy.MySecondTestAppSpringBoot.service.ValidationService;
import ru.dubovitskiy.MySecondTestAppSpringBoot.util.DataTimeUtil;

import javax.validation.Valid;
import java.util.Date;


@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;


    @Autowired
    public MyController(ValidationService validationService,
                     {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;

    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request: {}", request);
        Response response = createResponse(request);

        log.info("response: {}", response);
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
        } catch (UnsupportedCodeException e) {
        } catch (Exception e) {

            modifyRequestService.sendTime(response, request);
            return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
        }

        private Response createResponse (Request request){
            return Response.builder()
                    .uid(request.getUid())
                    .operationUid(request.getOperationUid())
                    .systemTime(DataTimeUtil.getCustomFormat().format(new Date()))
                    .code(Codes.SUCCESS)
                    .errorCode(ErrorCodes.EMPTY)
                    .errorMessage(ErrorMessages.EMPTY)
                    .build();
        }


    }
}
