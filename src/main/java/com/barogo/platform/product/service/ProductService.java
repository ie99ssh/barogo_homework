package com.barogo.platform.product.service;

import com.barogo.platform.product.dao.ProductRepository;
import com.barogo.platform.product.dao.ProductRepositorySupport;
import com.barogo.platform.product.dto.ProductDto.*;
import com.barogo.platform.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductRepositorySupport repositorySupport;

    public ProductRes getProduct(Long productSeq) {

        Product product = repository.findBySeq(productSeq);

        ProductRes productRes = new ProductRes();
        BeanUtils.copyProperties(product, productRes);

        return productRes;
    }

    public ProductListRes getProductList(ProductReq productReq) {
        return repositorySupport.findAll(productReq);
    }

    public Product insertProduct(InsertProductReq insertProductReq) {

        Product product = new Product();
        BeanUtils.copyProperties(insertProductReq, product);

        return repository.save(product);
    }

    public Product updateProduct(UpdateProductReq updateProductReq) {

        Product product = repository.findBySeq(updateProductReq.getSeq());
        BeanUtils.copyProperties(updateProductReq, product);

        return product;
    }
}
