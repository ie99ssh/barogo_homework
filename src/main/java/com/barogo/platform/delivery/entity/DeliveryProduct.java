package com.barogo.platform.delivery.entity;

import com.barogo.platform.delivery.entity.key.DeliveryProductKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;

@Data
@Entity
@IdClass(DeliveryProductKey.class)
public class DeliveryProduct {

    @Id
    private Long deliverySeq;

    @Id
    private Long productSeq;

    private Integer count;
    private Integer amount;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;

    private String regId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updDate;
    private String updId;

}
