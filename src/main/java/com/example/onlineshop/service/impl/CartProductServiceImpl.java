package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.CartProductRepository;
import com.example.onlineshop.service.CartProductService;
import com.example.onlineshop.service.UserEntityService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;
    private final UserEntityService userEntityService;

    public CartProductServiceImpl(CartProductRepository cartProductRepository,
                                  UserEntityService userEntityService) {
        this.cartProductRepository = cartProductRepository;
        this.userEntityService = userEntityService;
    }

    @Override
    public CartProduct createCartProduct(Product product, String size) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(1);
        cartProduct.setSize(size);

        return this.cartProductRepository.save(cartProduct);
    }

    @Override
    public void increaseCartProductQuantity(Long id, int number) {
        CartProduct cartProduct = this.cartProductRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("CartProduct with id " + id + " is not found!"));
        cartProduct.increaseQuantity(number);

        this.cartProductRepository.save(cartProduct);
    }

    @Override
    public void removeCartProductByProductId(Long productId, Long userId) {
        List<CartProduct> cartProducts = getCartProductsByUserId(userId);

        CartProduct cartProduct = getCartProductByProductId(productId, userId);

        cartProducts.remove(cartProduct);
        this.cartProductRepository.delete(cartProduct);
    }

    @Override
    public void changeProductQuantity(CartProductUpdateDto cartProductUpdateDto, Long productId, Long userId) {
        CartProduct cartProduct = getCartProductByProductId(productId, userId);
        cartProduct.setQuantity(cartProductUpdateDto.getQuantity());

        this.cartProductRepository.save(cartProduct);
    }

    private List<CartProduct> getCartProductsByUserId(Long userId){
        return this.userEntityService.getUserById(userId).getCart().getCartProducts();
    }

    private CartProduct getCartProductByProductId(Long productId,Long userId){
        return getCartProductsByUserId(userId).stream()
                .filter(c -> c.getId().equals(productId)).findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("CartProduct with id " + productId + " is not found!"));
    }
}
