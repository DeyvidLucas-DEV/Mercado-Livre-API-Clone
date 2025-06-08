package com.api.mvc.controller;

import com.api.mvc.Repository.CartItemRepository;
import com.api.mvc.Repository.OrderRepository;
import com.api.mvc.Repository.UsersRepository;
import com.api.mvc.entity.CartItemEntity;
import com.api.mvc.entity.OrderEntity;
import com.api.mvc.entity.UsersEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UsersRepository usersRepository;

    public OrderController(OrderRepository orderRepository, CartItemRepository cartItemRepository, UsersRepository usersRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderEntity>> list(@PathVariable Long userId) {
        return usersRepository.findById(userId)
                .map(user -> ResponseEntity.ok(orderRepository.findByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<List<OrderEntity>> checkout(@PathVariable Long userId) {
        UsersEntity user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<CartItemEntity> items = cartItemRepository.findByUser(user);
        if (items.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<OrderEntity> orders = items.stream().map(item -> {
            OrderEntity order = new OrderEntity();
            order.setUser(user);
            order.setProduct(item.getProduct());
            order.setQuantity(item.getQuantity());
            return orderRepository.save(order);
        }).toList();
        cartItemRepository.deleteByUser(user);
        return ResponseEntity.ok(orders);
    }
}
