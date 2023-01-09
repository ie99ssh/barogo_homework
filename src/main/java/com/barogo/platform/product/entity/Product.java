package com.barogo.platform.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    private String name;
    private Integer amount;


    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;

    private String regId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updDate;

    private String updId;

}
