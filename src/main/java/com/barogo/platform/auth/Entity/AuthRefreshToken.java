package com.barogo.platform.auth.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class AuthRefreshToken {

    @Id
    private String userId;

    private String refreshToken;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;

    private String regId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updDate;
    private String updId;

}
