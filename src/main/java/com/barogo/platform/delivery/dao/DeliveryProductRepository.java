package com.barogo.platform.delivery.dao;

import com.barogo.platform.delivery.entity.DeliveryProduct;
import com.barogo.platform.delivery.entity.key.DeliveryProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryProductRepository extends JpaRepository<DeliveryProduct, DeliveryProductKey> {
    List<DeliveryProduct> findByDeliverySeq(Long deliverySeq);

    void deleteByDeliverySeq(Long deliverySeq);
}
