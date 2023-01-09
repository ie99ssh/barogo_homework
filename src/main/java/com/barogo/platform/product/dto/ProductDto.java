package com.barogo.platform.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductDto {

    @Data
    public static class ProductReq {

        @ApiModelProperty(notes = "상품명")
        private String name;
    }

    @Data
    public static class ProductRes {

        @ApiModelProperty(notes = "상품 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "상품명")
        private String name;

        @ApiModelProperty(notes = "상품 금액")
        private Integer amount;
    }

    @Data
    public static class ProductListRes {
        private List<ProductRes> productList;
    }

    @Data
    public static class InsertProductReq {

        @ApiModelProperty(notes = "상품명", required = true)
        @NotEmpty(message = "상품명은 필수")
        private String name;

        @ApiModelProperty(notes = "가격", required = true)
        @NotNull(message = "가격은 필수")
        private Integer amount;

        @ApiModelProperty(notes = "등록자 ID", required = true)
        @NotEmpty(message = "등록자 ID는 필수")
        private String regId;
    }

    @Data
    public static class UpdateProductReq {

        @ApiModelProperty(notes = "상품 일련번호", required = true)
        @NotNull(message = "상품 일련번호")
        private Long seq;

        @ApiModelProperty(notes = "상품명", required = true)
        @NotEmpty(message = "상품명은 필수")
        private String name;

        @ApiModelProperty(notes = "수정자 ID", required = true)
        @NotEmpty(message = "수정자 ID는 필수")
        private String updId;
    }

}
