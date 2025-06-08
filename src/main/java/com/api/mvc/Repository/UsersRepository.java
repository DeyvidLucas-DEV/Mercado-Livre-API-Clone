package com.api.mvc.Repository;

import com.api.mvc.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    List<UsersEntity> findByName(String name);
    List<UsersEntity> findByEmail(String email);


}
