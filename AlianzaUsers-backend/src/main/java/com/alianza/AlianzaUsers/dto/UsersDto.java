package com.alianza.AlianzaUsers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsersDto {

    private String sharedKey;
    private String businessID;
    private String email;
    private String phone;
    private Date dateAdded;
    private Date startDate;
    private Date endDate;

}
