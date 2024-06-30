package com.alianza.AlianzaUsers.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class UsersEntity {

    @Id
    @Column(name = "sharedkey")
    private String sharedKey;
    private String businessID;
    private String email;
    private String phone;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "date_added")
    private Date dateAdded;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "end_date")
    private Date endDate;

}
