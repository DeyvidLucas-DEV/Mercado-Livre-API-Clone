package com.api.mvc.Repository;

import com.api.mvc.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByUsuario(String usuario);


}
