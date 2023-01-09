package com.barogo.platform.delivery.dao;

import com.barogo.platform.delivery.dto.DeliveryDto.DeliveryListRes;
import com.barogo.platform.delivery.dto.DeliveryDto.DeliveryRes;
import com.barogo.platform.delivery.entity.Delivery;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.barogo.platform.delivery.entity.QDelivery.delivery;

@Repository
public class DeliveryRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public DeliveryRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Delivery.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public DeliveryListRes findAll(LocalDateTime fromDateTime, LocalDateTime toDateTime) {

        DeliveryListRes deliveryListRes = new DeliveryListRes();

        QueryResults<DeliveryRes> resultList =
                jpaQueryFactory.select(Projections.fields(DeliveryRes.class,
                        delivery.seq
                        , delivery.requestDate
                        , delivery.completeDate
                        , delivery.totalAmount
                        , delivery.fees
                        , delivery.destAddress
                        , delivery.state
                        , delivery.regDate
                        , delivery.regId
                        , delivery.updDate
                        , delivery.updId
                ))
                        .from(delivery)
                        .where(delivery.requestDate.between(fromDateTime, toDateTime)
                        ).orderBy(delivery.seq.desc())
                        .fetchResults();

        deliveryListRes.setDeliveryList(resultList.getResults());
        return deliveryListRes;
    }
}
