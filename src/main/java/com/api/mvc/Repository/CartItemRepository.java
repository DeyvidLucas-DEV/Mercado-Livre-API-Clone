package com.api.mvc.Repository;

import com.api.mvc.entity.CartItemEntity;
import com.api.mvc.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUser(UsersEntity user);
    void deleteByUser(UsersEntity user);
}
