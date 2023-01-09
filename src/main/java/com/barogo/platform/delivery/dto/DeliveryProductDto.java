package com.barogo.platform.delivery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DeliveryProductDto {

    @Data
    public static class DeliveryProductListRes {
        private List<DeliveryProductRes> deliveryProductList;
    }

    @Data
    public static class DeliveryProductRes {

        @ApiModelProperty(notes = "배달 일련번호")
        private Long deliverySeq;

        @ApiModelProperty(notes = "상품 일련번호")
        private Long productSeq;

        @ApiModelProperty(notes = "상품명")
        private String name;

        @ApiModelProperty(notes = "상품 주문 개수")
        private Integer count;

        @ApiModelProperty(notes = "상품 금액")
        private Integer amount;

    }

    @Data
    public static class InsertDeliveryProductReq {

        @ApiModelProperty(notes = "상품 일련번호", required = true)
        @NotNull(message = "상품 일련번호는 필수")
        private Long productSeq;

        @ApiModelProperty(notes = "상품 주문 개수", required = true)
        @NotNull(message = "상품 주문 개수는 필수입니다.")
        private Integer count;
    }
}
