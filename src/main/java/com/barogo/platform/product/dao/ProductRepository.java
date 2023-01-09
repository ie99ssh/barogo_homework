package com.barogo.platform.product.dao;

import com.barogo.platform.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySeq(Long seq);
}
