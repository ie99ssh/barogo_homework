package com.barogo.platform.delivery.service;

import com.barogo.platform.common.constants.GlobalConstants;
import com.barogo.platform.common.exception.Errors;
import com.barogo.platform.common.exception.GlobalBadRequestException;
import com.barogo.platform.common.exception.GlobalException;
import com.barogo.platform.common.exception.GlobalNotFoundException;
import com.barogo.platform.common.util.CommonUtil;
import com.barogo.platform.delivery.dao.DeliveryRepository;
import com.barogo.platform.delivery.dao.DeliveryRepositorySupport;
import com.barogo.platform.delivery.dto.DeliveryDto.*;
import com.barogo.platform.delivery.dto.DeliveryProductDto.DeliveryProductListRes;
import com.barogo.platform.delivery.dto.DeliveryProductDto.InsertDeliveryProductReq;
import com.barogo.platform.delivery.entity.Delivery;
import com.barogo.platform.product.dto.ProductDto.ProductRes;
import com.barogo.platform.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository repository;
    private final DeliveryRepositorySupport repositorySupport;
    private final DeliveryProductService deliveryProductService;
    private final ProductService productService;
    private final CommonUtil commonUtil;

    public DeliveryRes getDelivery(Long deliverySeq) {

        Delivery delivery = repository.findBySeq(deliverySeq);

        DeliveryRes deliveryRes = new DeliveryRes();
        BeanUtils.copyProperties(delivery, deliveryRes);

        return deliveryRes;
    }

    public DeliveryListRes getDeliveryList(DeliveryReq deliveryReq) {

        LocalDateTime fromDateTime = commonUtil.getFromDate(deliveryReq.getFromDate());
        LocalDateTime toDateTime = commonUtil.getToDate(deliveryReq.getToDate());

        int betweenDays = (int) Duration.between(fromDateTime, toDateTime).toDays();

        if (betweenDays > GlobalConstants.DELIVERY_MAX_PERIOD) {
            throw new GlobalBadRequestException(Errors.DELIVERY_MAX_PERIOD_EXCEED);
        }

        if (fromDateTime.isAfter(toDateTime)) {
            throw new GlobalBadRequestException(Errors.DELIVERY_DATE_INVALID);
        }

        DeliveryListRes deliveryListRes = repositorySupport.findAll(fromDateTime, toDateTime);

        for (DeliveryRes deliveryRes : deliveryListRes.getDeliveryList()) {
            DeliveryProductListRes deliveryProductListRes
                    = deliveryProductService.getDeliveryProductList(deliveryRes.getSeq());
            deliveryRes.setProductList(deliveryProductListRes);
        }

        return deliveryListRes;
    }

    public Delivery insertDelivery(InsertDeliveryReq insertDeliveryReq) {

        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(insertDeliveryReq, delivery);
        List<InsertDeliveryProductReq> productList = insertDeliveryReq.getProductList();
        int totalProductAmount = this.getTotalProductAmount(productList);

        delivery.setRequestDate(LocalDateTime.now());
        delivery.setTotalAmount(totalProductAmount);

        //총 금액의 10%로 우선 처리하는 것으로 한다. (소수점 반올림)
        delivery.setFees((int) Math.round(totalProductAmount * 0.1));
        delivery.setState(Delivery.State.READY);

        Delivery retDelivery = repository.save(delivery);

        for (InsertDeliveryProductReq insertDeliveryProductReq : productList) {
            String regId = insertDeliveryReq.getRegId();
            deliveryProductService.insertProductDelivery(retDelivery.getSeq(), insertDeliveryProductReq, regId);
        }

        return retDelivery;
    }

    public Delivery updateDelivery(UpdateDeliveryReq updateDeliveryReq) {

        Long seq = updateDeliveryReq.getSeq();
        Delivery delivery = repository.findBySeq(seq);

        if (ObjectUtils.isEmpty(delivery)) {
            throw new GlobalNotFoundException(Errors.DELIVERY_NOT_EXIST);
        } else if (delivery.getState() != Delivery.State.READY) {
            throw new GlobalException(Errors.DELIVERY_UPDATE_NOT_POSSIBLE);
        }

        BeanUtils.copyProperties(updateDeliveryReq, delivery);

        List<InsertDeliveryProductReq> productList = updateDeliveryReq.getProductList();
        int totalProductAmount = this.getTotalProductAmount(productList);

        delivery.setTotalAmount(totalProductAmount);
        delivery.setFees((int) Math.round(totalProductAmount * 0.1));

        deliveryProductService.deleteDeliveryProduct(seq);

        for (InsertDeliveryProductReq insertDeliveryProductReq : updateDeliveryReq.getProductList()) {
            String regId = updateDeliveryReq.getUpdId();
            deliveryProductService.insertProductDelivery(updateDeliveryReq.getSeq(), insertDeliveryProductReq, regId);
        }

        return delivery;
    }

    public Delivery updateDeliveryAddress(UpdateDeliveryAddressReq updateDeliveryAddressReq) {

        Delivery delivery = repository.findBySeq(updateDeliveryAddressReq.getSeq());

        if (ObjectUtils.isEmpty(delivery)) {
            throw new GlobalBadRequestException(Errors.DELIVERY_NOT_EXIST);
        } else if (delivery.getState() != Delivery.State.READY) {
            throw new GlobalException(Errors.DELIVERY_UPDATE_DEST_ADDRESS_NOT_POSSIBLE);
        }

        delivery.setDestAddress(updateDeliveryAddressReq.getDestAddress());
        delivery.setUpdId(updateDeliveryAddressReq.getUpdId());

        return delivery;
    }

    public Delivery updateDeliveryState(UpdateDeliveryStateReq updateDeliveryStateReq) {

        Delivery delivery = repository.findBySeq(updateDeliveryStateReq.getSeq());
        Delivery.State state = updateDeliveryStateReq.getState();

        if (ObjectUtils.isEmpty(delivery)) {
            throw new GlobalBadRequestException(Errors.DELIVERY_NOT_EXIST);
        } else if (delivery.getState() == Delivery.State.COMPLETE) {
            throw new GlobalException(Errors.DELIVERY_ALREADY_COMPLETE);
        }

        if (state == Delivery.State.COMPLETE) {
            delivery.setCompleteDate(LocalDateTime.now());
        }

        delivery.setState(updateDeliveryStateReq.getState());
        delivery.setUpdId(updateDeliveryStateReq.getUpdId());

        return delivery;
    }

    private int getTotalProductAmount(List<InsertDeliveryProductReq> productList) {

        int amount = 0;

        for (InsertDeliveryProductReq insertDeliveryProductReq : productList) {
            Long productSeq = insertDeliveryProductReq.getProductSeq();
            ProductRes product = productService.getProduct(productSeq);
            Integer productCount = insertDeliveryProductReq.getCount();

            amount += product.getAmount() * productCount;
        }
        return amount;
    }
}
