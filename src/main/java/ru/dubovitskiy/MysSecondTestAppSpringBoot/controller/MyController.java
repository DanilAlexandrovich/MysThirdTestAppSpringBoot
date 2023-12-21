package ru.dubovitskiy.MysSecondTestAppSpringBoot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dubovitskiy.MySecondTestAppSpringBoot.model.Request;
import ru.dubovitskiy.MySecondTestAppSpringBoot.model.Response;
import ru.dubovitskiy.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;

import java.text.SimpleDateFormat;


@RestController
public class MyController {

    private final ValidationService validationService;

    @Autowired
    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


        Response response = Response.builder()
                .uid(request.getUid())
                .opperationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code("SUCCESS")
                .errorCode("")
                .errorMessage("")
                .build();

        try {
            // Проверяем, равен ли uid 123
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("Unsupported uid");
            }
            validationService.isValid(bindingResult);
            log.info("Validations passed successfully");
        } catch (UnsupportedCodeException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPROT_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSAPPORTED);
            log.error("Unsupported code exception occurred: {}", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (ValidationFailedException e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.error("Validation failed: {}", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCode(Codes.FAILED);
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Пройзошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
