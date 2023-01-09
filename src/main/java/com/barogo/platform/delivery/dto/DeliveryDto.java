package com.barogo.platform.delivery.dto;

import com.barogo.platform.delivery.entity.Delivery;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeliveryDto {

    @Data
    public static class DeliveryReq {

        @ApiModelProperty(notes = "기간 시작일자 (YYYY-MM-DD)", required = true)
        @NotEmpty(message = "기간 시작일자는 필수")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String fromDate;

        @ApiModelProperty(notes = "기간 종료일자 (YYYY-MM-DD)", required = true)
        @NotEmpty(message = "기간 종료일자는 필수")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private String toDate;
    }

    @Data
    public static class DeliveryRes {

        @ApiModelProperty(notes = "배달 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "주문 일시")
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        private LocalDateTime requestDate;

        @ApiModelProperty(notes = "목적지 주소")
        private String destAddress;

        @ApiModelProperty(notes = "배달 상태")
        @Enumerated(EnumType.STRING)
        private Delivery.State state;

        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
        @ApiModelProperty(notes = "배달 완료 일시")
        private LocalDateTime completeDate;

        @ApiModelProperty(notes = "배달 상품 총 금액")
        private Integer totalAmount;

        @ApiModelProperty(notes = "배달 수수료")
        private Integer fees;

        @ApiModelProperty(notes = "상품 정보")
        private DeliveryProductDto.DeliveryProductListRes productList;
    }

    @Data
    public static class DeliveryListRes {
        private List<DeliveryRes> deliveryList;
    }

    @Data
    public static class InsertDeliveryReq {

        @ApiModelProperty(notes = "목적지 주소", required = true)
        @NotEmpty(message = "목적지 주소는 필수")
        private String destAddress;

        @ApiModelProperty(notes = "상품 정보", required = true)
        @Size(min = 1)
        @Valid
        private List<DeliveryProductDto.InsertDeliveryProductReq> productList;


        @ApiModelProperty(notes = "등록자 ID", required = true)
        @NotEmpty(message = "등록자 ID는 필수")
        private String regId;
    }

    @Data
    public static class UpdateDeliveryReq {

        @ApiModelProperty(notes = "배달 일련번호", required = true)
        @NotNull(message = "배달 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "목적지 주소", required = true)
        @NotEmpty(message = "목적지 주소는 필수")
        private String destAddress;

        @ApiModelProperty(notes = "상품 정보", required = true)
        @Size(min = 1)
        @Valid
        private List<DeliveryProductDto.InsertDeliveryProductReq> productList;

        @ApiModelProperty(notes = "수정자 ID", required = true)
        @NotEmpty(message = "수정자 ID는 필수")
        private String updId;
    }

    @Data
    public static class UpdateDeliveryAddressReq {

        @ApiModelProperty(notes = "배달 일련번호", required = true)
        @NotNull(message = "배달 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "목적지 주소", required = true)
        @NotEmpty(message = "목적지 주소는 필수")
        private String destAddress;

        @ApiModelProperty(notes = "수정자 ID", required = true)
        @NotEmpty(message = "수정자 ID는 필수")
        private String updId;
    }

    @Data
    public static class UpdateDeliveryStateReq {

        @ApiModelProperty(notes = "배달 일련번호", required = true)
        @NotNull(message = "배달 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "배달 상태", required = true)
        @NotNull(message = "배달 상태는 필수")
        @Enumerated(EnumType.STRING)
        private Delivery.State state;

        @ApiModelProperty(notes = "수정자 ID", required = true)
        @NotEmpty(message = "수정자 ID는 필수")
        private String updId;
    }

}
