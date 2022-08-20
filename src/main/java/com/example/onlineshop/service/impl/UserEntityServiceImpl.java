package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.*;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.dto.user.UserAccountDto;
import com.example.onlineshop.model.dto.user.UserChangePasswordDto;
import com.example.onlineshop.model.dto.user.UserRegisterDto;
import com.example.onlineshop.model.dto.user.UserUpdateInformationDto;
import com.example.onlineshop.model.entity.Cart;

import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.model.enums.ERole;
import com.example.onlineshop.repisotiry.UserEntityRepository;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository,
                                 ProductService productService, ModelMapper modelMapper,
                                 PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto, ERole role) {
        UserEntity newUser = this.modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));
        newUser.setCart(new Cart());
        newUser.setRole(role);

        this.userEntityRepository.save(newUser);
    }

    @Override
    public boolean isNotExistByEmail(String email) {
        Optional<UserEntity> userEntity = this.userEntityRepository.findByEmail(email);

        return userEntity.isEmpty();
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return this.userEntityRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + userId + " is not found!"));
    }

    @Override
    public void addProductToWishlist(Long userId, WishlistAddDto wishlistAddDto) {
        UserEntity userEntity = getUserById(userId);
        Long productId = wishlistAddDto.getProductId();

        boolean isContainsProduct = userEntity.getWishlist().stream()
                .anyMatch(p -> Objects.equals(p.getId(), productId));
        if (isContainsProduct) {
            return;
        }

        Product product = this.productService.getProductById(productId);
        userEntity.getWishlist().add(product);

        this.userEntityRepository.save(userEntity);
    }

    @Override
    public List<ProductDto> getWishlistProducts(Long id) {
        return getUserById(id).getWishlist().stream()
                .map(p -> {
                    ProductDto productDto = this.modelMapper.map(p, ProductDto.class);
                    if(p.getPictures().isEmpty()){
                        productDto.setImgUrl("https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png");
                    } else {
                        productDto.setImgUrl(p.getPictures().get(0).getUrl());
                    }

                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserAccountDto getUserAccountDto(Long id) {
        return this.modelMapper.map(getUserById(id), UserAccountDto.class);
    }

    @Override
    public void updateInformation(UserUpdateInformationDto userUpdateInformationDto, Long id) {
        UserEntity userEntity = getUserById(id);
        userEntity.setFirstName(userUpdateInformationDto.getFirstName());
        userEntity.setLastName(userUpdateInformationDto.getLastName());
        userEntity.setPhoneNumber(userUpdateInformationDto.getPhoneNumber());

        this.userEntityRepository.save(userEntity);
    }

    @Override
    public boolean changePassword(UserChangePasswordDto userChangePasswordDto, Long id) {
        UserEntity userEntity = getUserById(id);

        if (this.passwordEncoder.matches(userChangePasswordDto.getPassword(), userEntity.getPassword())) {
            userEntity.setPassword(this.passwordEncoder.encode(userChangePasswordDto.getNewPassword()));
            this.userEntityRepository.save(userEntity);

            return true;
        }
        return false;
    }

    @Override
    public void clearCart(Long id) {
        UserEntity userEntity = getUserById(id);
        userEntity.getCart().getCartProducts().clear();

      this.userEntityRepository.save(userEntity);
    }

    @Override
    public boolean isExistUser(String email, String password) {
       Optional<UserEntity> userEntity = this.userEntityRepository.findByEmail(email);

        return userEntity.isPresent() && this.passwordEncoder.matches(password, userEntity.get().getPassword());
    }
}
