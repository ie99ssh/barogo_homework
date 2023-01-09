package com.barogo.platform.product.dao;

import com.barogo.platform.product.dto.ProductDto.ProductListRes;
import com.barogo.platform.product.dto.ProductDto.ProductReq;
import com.barogo.platform.product.dto.ProductDto.ProductRes;
import com.barogo.platform.product.entity.Product;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import static com.barogo.platform.product.entity.QProduct.product;

@Repository
public class ProductRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Product.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public ProductListRes findAll(ProductReq productReq) {

        ProductListRes productListRes = new ProductListRes();

        QueryResults<ProductRes> resultList =
                jpaQueryFactory.select(Projections.fields(ProductRes.class,
                        product.seq
                        , product.name
                        , product.amount
                        , product.regDate
                        , product.regId
                        , product.updDate
                        , product.updId
                ))
                        .from(product)
                        .where(nameLike(productReq.getName())
                        ).orderBy(product.seq.desc())
                        .fetchResults();

        productListRes.setProductList(resultList.getResults());
        return productListRes;
    }

    private BooleanExpression nameLike(String name) {
        return !ObjectUtils.isEmpty(name) ? product.name.like("%" + name + "%") : null;
    }
}
