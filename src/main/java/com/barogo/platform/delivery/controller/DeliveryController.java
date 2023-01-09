package com.barogo.platform.delivery.controller;

import com.barogo.platform.common.constants.GlobalConstants;
import com.barogo.platform.common.domain.BaseModel;
import com.barogo.platform.common.exception.GlobalException;
import com.barogo.platform.delivery.dto.DeliveryDto.*;
import com.barogo.platform.delivery.service.DeliveryService;
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
@Api(tags = "[C - 1] 배달 관리")
public class DeliveryController {

    private final DeliveryService deliveryService;

    /**
     * --------------------------------------------------------------------
     * ■배달 목록 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 목록 조회", notes = "배달 목록 조회")
    @ResponseBody
    @RequestMapping(value = "/deliveries", method = RequestMethod.GET)
    public ResponseEntity<BaseModel> getDeliveryList(@Valid @RequestBody DeliveryReq deliveryReq)
            throws GlobalException {

        BaseModel baseModel = new BaseModel();
        DeliveryListRes deliveryListRes
                = deliveryService.getDeliveryList(deliveryReq);

        baseModel.setData(deliveryListRes);

        return new ResponseEntity<>(baseModel, HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■배달 상세 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 조회", notes = "배달 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deliverySeq", value = "배달 일련번호", required = true, dataType = "long", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/deliveries/{deliverySeq}", method = RequestMethod.GET)
    public ResponseEntity<BaseModel> getDelivery(@PathVariable Long deliverySeq)
            throws GlobalException {

        BaseModel baseModel = new BaseModel();
        DeliveryRes deliveryRes
                = deliveryService.getDelivery(deliverySeq);

        baseModel.setData(deliveryRes);

        return new ResponseEntity<>(baseModel, HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■배달 등록 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 등록", notes = "배달 등록")
    @ResponseBody
    @RequestMapping(value = "/deliveries", method = RequestMethod.POST)
    public ResponseEntity<BaseModel> insertDelivery(@Valid @RequestBody InsertDeliveryReq insertDeliveryReq) {
        deliveryService.insertDelivery(insertDeliveryReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.CREATED);
    }

    /**
     * --------------------------------------------------------------------
     * ■배달 수정 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 수정", notes = "배달 수정")
    @ResponseBody
    @RequestMapping(value = "/deliveries", method = RequestMethod.PUT)
    public ResponseEntity<BaseModel> updateDelivery(@Valid @RequestBody UpdateDeliveryReq updateDeliveryReq) {
        deliveryService.updateDelivery(updateDeliveryReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■배달 배송지 수정 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 배송지 수정", notes = "배달 배송지 수정")
    @ResponseBody
    @RequestMapping(value = "/deliveries/address", method = RequestMethod.PUT)
    public ResponseEntity<BaseModel> updateDelivery(@Valid @RequestBody UpdateDeliveryAddressReq updateDeliveryAddressReq) {
        deliveryService.updateDeliveryAddress(updateDeliveryAddressReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.OK);
    }

    /**
     * --------------------------------------------------------------------
     * ■배달 수정 API ■sangheon
     * --------------------------------------------------------------------
     **/
    @ApiOperation(value = "배달 상태 변경", notes = "배달 상태 변경")
    @ResponseBody
    @RequestMapping(value = "/deliveries/state", method = RequestMethod.PUT)
    public ResponseEntity<BaseModel> updateDeliveryState(@Valid @RequestBody UpdateDeliveryStateReq updateDeliveryStateReq) {
        deliveryService.updateDeliveryState(updateDeliveryStateReq);
        return new ResponseEntity<>(new BaseModel(), HttpStatus.OK);
    }

}
