package com.example.onlineshop.model.validator;

import com.example.onlineshop.service.UserEntityService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailUniqueValidator implements ConstraintValidator<UserEmailUnique, String> {

    private final UserEntityService userEntityService;

    public UserEmailUniqueValidator(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }

        return this.userEntityService.isNotExistByEmail(email);
    }
}
