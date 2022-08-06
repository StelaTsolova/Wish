package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.user.UserRegisterDto;
import com.example.onlineshop.model.enums.ERole;
import com.example.onlineshop.service.UserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserRegisterController {

    private final UserEntityService userEntityService;

    public UserRegisterController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDto userRegisterDto,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        this.userEntityService.registerUser(userRegisterDto, ERole.USER);

        return ResponseEntity.ok("User registered successfully!");
    }

    public static Map<String, String> getErrorMessages(List<ObjectError> allErrors) {
        Map<String, String> errorMessages = new HashMap<>();

        for (Object object : allErrors) {
            if (object instanceof FieldError fieldError) {
                errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        return errorMessages;
    }
}
