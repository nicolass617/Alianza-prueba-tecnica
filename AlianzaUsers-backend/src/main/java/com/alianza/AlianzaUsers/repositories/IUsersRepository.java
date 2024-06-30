package com.alianza.AlianzaUsers.repositories;

import com.alianza.AlianzaUsers.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsersRepository extends JpaRepository<UsersEntity, String> {

    Optional<UsersEntity> getUserByBusinessID(String businessID);

}
