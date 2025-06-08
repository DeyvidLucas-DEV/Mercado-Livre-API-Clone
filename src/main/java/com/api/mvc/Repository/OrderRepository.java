package com.api.mvc.Repository;

import com.api.mvc.entity.OrderEntity;
import com.api.mvc.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser(UsersEntity user);
}
