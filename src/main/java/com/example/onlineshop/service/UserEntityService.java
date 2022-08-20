package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.*;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.dto.user.UserAccountDto;
import com.example.onlineshop.model.dto.user.UserChangePasswordDto;
import com.example.onlineshop.model.dto.user.UserRegisterDto;
import com.example.onlineshop.model.dto.user.UserUpdateInformationDto;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.model.enums.ERole;

import java.util.List;

public interface UserEntityService {

    void registerUser(UserRegisterDto userRegisterDto, ERole role);

    boolean isNotExistByEmail(String email);

    UserEntity getUserById(Long userId);

    void addProductToWishlist(Long userId, WishlistAddDto wishlistAddDto);

    List<ProductDto> getWishlistProducts(Long id);

    UserAccountDto getUserAccountDto(Long id);

    void updateInformation(UserUpdateInformationDto userUpdateInformationDto, Long id);

    boolean changePassword(UserChangePasswordDto userChangePasswordDto, Long id);

    void clearCart(Long id);

    boolean isExistUser(String email, String password);
}
