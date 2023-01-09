package com.barogo.platform.product.controller;

import com.barogo.platform.common.constants.GlobalConstants;
import com.barogo.platform.common.domain.BaseModel;
import com.barogo.platform.common.exception.GlobalException;
import com.barogo.platform.product.dto.ProductDto.*;
import com.barogo.platform.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = GlobalConstants.PATH_APP)
@RequiredArgsConstructor
@Api(tags = "[B - 1] 상품 관리")
public class ProductController {

    private final ProductService productService;

    /**
     * --------------------------------------------------------------------
     * ■배달 목록 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 목록 조회", notes = "배달 목록 조회")
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<BaseModel> getProductList(@Valid @RequestBody ProductReq productReq)
            throws GlobalException {

        BaseModel baseModel = new BaseModel();
        ProductListRes deliveryListRes
                = productService.getProductList(productReq);

        baseModel.setData(deliveryListRes);

        return new ResponseEntity<>(baseModel, HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■상품 상세 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "상품 조회", notes = "상품 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productSeq", value = "상품 일련번호", required = true, dataType = "long", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/products/{productSeq}", method = RequestMethod.GET)
    public ResponseEntity<BaseModel> getProduct(@PathVariable Long productSeq)
            throws GlobalException {

        BaseModel baseModel = new BaseModel();
        ProductRes productRes
                = productService.getProduct(productSeq);
        baseModel.setData(productRes);

        return new ResponseEntity<>(baseModel, HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■상품 등록 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "상품 등록", notes = "상품 등록")
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity<BaseModel> insertProduct(@Valid @RequestBody InsertProductReq insertProductReq) {
        productService.insertProduct(insertProductReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.CREATED);
    }

    /**
     * --------------------------------------------------------------------
     * ■상품 수정 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "상품 수정", notes = "상품 수정")
    @ResponseBody
    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public ResponseEntity<BaseModel> updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        productService.updateProduct(updateProductReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.OK);
    }

}
