package com.alianza.AlianzaUsers.service;

import com.alianza.AlianzaUsers.dto.UsersDto;
import com.alianza.AlianzaUsers.entities.UsersEntity;

import java.util.List;

public interface IUserService {

    UsersEntity save(UsersDto userDto);

    List<UsersEntity> findAll();

    UsersEntity findBySharedKey(String sharedKey);

}
