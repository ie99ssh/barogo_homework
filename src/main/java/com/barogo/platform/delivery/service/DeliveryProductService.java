package com.barogo.platform.delivery.service;

import com.barogo.platform.common.exception.Errors;
import com.barogo.platform.common.exception.GlobalBadRequestException;
import com.barogo.platform.delivery.dao.DeliveryProductRepository;
import com.barogo.platform.delivery.dao.DeliveryProductRepositorySupport;
import com.barogo.platform.delivery.dto.DeliveryProductDto.DeliveryProductListRes;
import com.barogo.platform.delivery.dto.DeliveryProductDto.InsertDeliveryProductReq;
import com.barogo.platform.delivery.entity.DeliveryProduct;
import com.barogo.platform.product.dto.ProductDto.ProductRes;
import com.barogo.platform.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryProductService {

    private final DeliveryProductRepository repository;
    private final DeliveryProductRepositorySupport repositorySupport;
    private final ProductService productService;


    public DeliveryProductListRes getDeliveryProductList(Long deliverySeq) {
        return repositorySupport.findProductList(deliverySeq);
    }

    public DeliveryProduct insertProductDelivery(Long deliverySeq, InsertDeliveryProductReq insertDeliveryProductReq, String regId) {

        Long productSeq = insertDeliveryProductReq.getProductSeq();
        ProductRes product = productService.getProduct(productSeq);

        if (ObjectUtils.isEmpty(product)) {
            throw new GlobalBadRequestException(Errors.PRODUCT_NOT_EXIST);
        }

        DeliveryProduct deliveryProduct = new DeliveryProduct();
        BeanUtils.copyProperties(insertDeliveryProductReq, deliveryProduct);
        deliveryProduct.setDeliverySeq(deliverySeq);
        deliveryProduct.setAmount(product.getAmount() * insertDeliveryProductReq.getCount());
        deliveryProduct.setRegId(regId);

        return repository.save(deliveryProduct);
    }

    public void deleteDeliveryProduct(Long deliverySeq) {
        repository.deleteByDeliverySeq(deliverySeq);
    }

}
