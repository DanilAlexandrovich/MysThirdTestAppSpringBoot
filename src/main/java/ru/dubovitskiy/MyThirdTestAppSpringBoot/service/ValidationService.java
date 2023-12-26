package ru.dubovitskiy.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.dubovitskiy.MyThirdTestAppSpringBoot.exception.ValidationFailedException;

@Service
public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException, UnsupportedCodeException;
}
