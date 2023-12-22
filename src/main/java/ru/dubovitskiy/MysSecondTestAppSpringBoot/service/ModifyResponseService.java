package ru.dubovitskiy.MysSecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.dubovitskiy.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
