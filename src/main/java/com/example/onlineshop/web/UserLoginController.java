package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.user.UserLoginDto;
import com.example.onlineshop.web.jwt.JwtResponse;
import com.example.onlineshop.web.jwt.JwtUtils;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import com.example.onlineshop.service.UserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserLoginController {

    private final UserEntityService userEntityService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserLoginController(UserEntityService userEntityService,
                               AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userEntityService = userEntityService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login (@RequestBody @Valid UserLoginDto userLoginDto,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( authentication1);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                jwt,
                roles));
    }
}
