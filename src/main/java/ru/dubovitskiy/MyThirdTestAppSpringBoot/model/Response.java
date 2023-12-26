package ru.dubovitskiy.MyThirdTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {


    private String uid;

    private String opperationUid;

    private String systemTime;

    private Codes code;

    private ErrorCodes errorCode;

    private ErrorMessages errorMessage;

}
