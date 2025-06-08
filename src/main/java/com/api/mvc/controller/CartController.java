package com.api.mvc.controller;

import com.api.mvc.Repository.CartItemRepository;
import com.api.mvc.Repository.ProductRepository;
import com.api.mvc.Repository.UsersRepository;
import com.api.mvc.entity.CartItemEntity;
import com.api.mvc.entity.ProductEntity;
import com.api.mvc.entity.UsersEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartItemRepository cartItemRepository;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;

    public CartController(CartItemRepository cartItemRepository, UsersRepository usersRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemEntity>> list(@PathVariable Long userId) {
        return usersRepository.findById(userId)
                .map(user -> ResponseEntity.ok(cartItemRepository.findByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CartItemEntity> add(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        UsersEntity user = usersRepository.findById(userId).orElse(null);
        ProductEntity product = productRepository.findById(productId).orElse(null);
        if (user == null || product == null) {
            return ResponseEntity.notFound().build();
        }
        CartItemEntity item = new CartItemEntity();
        item.setUser(user);
        item.setProduct(product);
        item.setQuantity(quantity);
        return ResponseEntity.ok(cartItemRepository.save(item));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clear(@PathVariable Long userId) {
        return usersRepository.findById(userId)
                .map(user -> {
                    cartItemRepository.deleteByUser(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
