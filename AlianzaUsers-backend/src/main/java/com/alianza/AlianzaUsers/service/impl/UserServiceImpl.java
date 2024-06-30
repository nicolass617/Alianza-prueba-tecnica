package com.alianza.AlianzaUsers.service.impl;

import com.alianza.AlianzaUsers.dto.UsersDto;
import com.alianza.AlianzaUsers.entities.UsersEntity;
import com.alianza.AlianzaUsers.repositories.IUsersRepository;
import com.alianza.AlianzaUsers.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class UserServiceImpl implements IUserService {

    private final IUsersRepository usersRepository;

    public UserServiceImpl(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UsersEntity save(UsersDto userDto) {
        log.info("---------- UserServiceImpl saveUser " + userDto);
        UsersEntity findUser = usersRepository.findById(userDto.getEmail().split("@")[0]).orElse(null);
        UsersEntity findBusinnesID = usersRepository.getUserByBusinessID(userDto.getBusinessID()).orElse(null);
        if (findUser == null && findBusinnesID == null) {
            log.info("--------- UserServiceImpl nuevo usuario " + userDto);
            UsersEntity usersEntity = UsersEntity.builder()
                    .phone(userDto.getPhone())
                    .sharedKey(userDto.getEmail().split("@")[0])
                    .email(userDto.getEmail())
                    .businessID(userDto.getBusinessID())
                    .dateAdded(new Date())
                    .endDate(userDto.getEndDate())
                    .startDate(userDto.getStartDate())
                    .build();
            log.info("-------- UserServiceImpl saveUser " + usersEntity);
            usersEntity = usersRepository.save(usersEntity);
            return usersEntity;
        } else {
            log.info("--------- UserServiceImpl usuario ya registrado " + userDto);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario ya registrado",null);
        }
    }

    @Override
    public List<UsersEntity> findAll() {
        log.info("--------- UserServiceImpl findAllUsers");
        return usersRepository.findAll();
    }

    @Override
    public UsersEntity findBySharedKey(String sharedKey) {
        log.info("--------- UserServiceImpl findBySharedKey" + sharedKey);
        return usersRepository.findById(sharedKey).orElse(null);
    }

}
