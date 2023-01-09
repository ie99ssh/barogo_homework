package com.barogo.platform.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(unique = true)
    private String userId;

    private String userPwd;
    private String userName;
    private String phoneNo;
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastLoginDate;
    private String lastLoginIP;
    private Boolean useFlag = true;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;

    private String regId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updDate;
    private String updId;

}
