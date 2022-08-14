package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.user.UserLoginDto;

import com.example.onlineshop.service.UserEntityService;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import com.example.onlineshop.service.impl.UserDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserLoginController {

    private final AuthenticationManager authenticationManager;

    public UserLoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLoginDto,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(),
                        userLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new UserDetailsResponse(
                userDetails.getUsername(),
                userDetails.getFirstName(),
                roles));
    }
}
