package com.barogo.platform.delivery.dao;

import com.barogo.platform.delivery.dto.DeliveryProductDto.DeliveryProductListRes;
import com.barogo.platform.delivery.dto.DeliveryProductDto.DeliveryProductRes;
import com.barogo.platform.delivery.entity.DeliveryProduct;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.barogo.platform.delivery.entity.QDeliveryProduct.deliveryProduct;
import static com.barogo.platform.product.entity.QProduct.product;

@Repository
public class DeliveryProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public DeliveryProductRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(DeliveryProduct.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public DeliveryProductListRes findProductList(Long deliverySeq) {

        DeliveryProductListRes deliveryProductListRes = new DeliveryProductListRes();

        QueryResults<DeliveryProductRes> resultList =
                jpaQueryFactory.select(Projections.fields(DeliveryProductRes.class,
                        deliveryProduct.deliverySeq
                        , deliveryProduct.productSeq
                        , product.name
                        , deliveryProduct.amount
                        , deliveryProduct.count
                        , deliveryProduct.regDate
                        , deliveryProduct.regId
                        , deliveryProduct.updDate
                        , deliveryProduct.updId
                ))
                        .from(deliveryProduct)
                        .innerJoin(product).on(deliveryProduct.productSeq.eq(product.seq))
                        .where(deliveryProduct.deliverySeq.eq(deliverySeq)
                        ).fetchResults();

        deliveryProductListRes.setDeliveryProductList(resultList.getResults());
        return deliveryProductListRes;
    }
}
