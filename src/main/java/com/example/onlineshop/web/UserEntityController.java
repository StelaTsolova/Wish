package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.*;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.dto.user.UserChangePasswordDto;
import com.example.onlineshop.model.dto.user.UserCreateDto;
import com.example.onlineshop.model.dto.user.UserRegisterDto;
import com.example.onlineshop.model.dto.user.UserUpdateInformationDto;
import com.example.onlineshop.model.enums.ERole;
import com.example.onlineshop.service.UserEntityService;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.example.onlineshop.web.UserRegisterController.getErrorMessages;

@RestController
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final ModelMapper modelMapper;

    public UserEntityController(UserEntityService userEntityService, ModelMapper modelMapper) {
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(this.userEntityService.getUserAccountDto(userDetails.getId()));
    }

    @GetMapping("/users/wishlist")
    public ResponseEntity<List<ProductDto>> products(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(this.userEntityService.getWishlistProducts(userDetails.getId()));
    }

    @PostMapping("/users/wishlist")
    public ResponseEntity<?> addProduct(@RequestBody WishlistAddDto wishlistAddDto,
                                        Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        this.userEntityService.addProductToWishlist(userDetails.getId(), wishlistAddDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateInformation(@RequestBody @Valid UserUpdateInformationDto userUpdateInformationDto,
                                               BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        this.userEntityService.updateInformation(userUpdateInformationDto, userDetails.getId());

        return ResponseEntity.ok().body(Map.of("name", userUpdateInformationDto.getFirstName()));
    }

    @PutMapping(value = "/users/change")
    public ResponseEntity<?> changePassword(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                            BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("massage", "New password should be between 8 and 40 symbols."));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!this.userEntityService.changePassword(userChangePasswordDto, userDetails.getId())) {
            return ResponseEntity.badRequest().body(Map.of("massage", "Wrong password."));
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto userCreateDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        UserRegisterDto userRegisterDto = this.modelMapper.map(userCreateDto, UserRegisterDto.class);
        this.userEntityService.registerUser(userRegisterDto, ERole.valueOf(userCreateDto.getRole()));

        return ResponseEntity.ok().build();
    }
}
