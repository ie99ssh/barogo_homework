package com.barogo.platform.delivery.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime requestDate;

    private String destAddress;

    @Enumerated(EnumType.STRING)
    private State state;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime completeDate;

    private Integer totalAmount;
    private Integer fees;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime regDate;

    private String regId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updDate;

    private String updId;

    public enum State {
        READY("접수"),
        IN_DELIVERY("배달중"),
        COMPLETE("배달완료");

        private final String value;

        State(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

}
