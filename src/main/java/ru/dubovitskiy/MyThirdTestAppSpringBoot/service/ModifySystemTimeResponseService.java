package ru.dubovitskiy.MyThirdTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.dubovitskiy.MySecondTestAppSpringBoot.model.Response;
import ru.dubovitskiy.MySecondTestAppSpringBoot.util.DataTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {
    @Override
    public Response modify(Response response) {
        response.setSystemTime(DataTimeUtil.getCustomFormat().format(new Date()));
        return response;
    }
}
