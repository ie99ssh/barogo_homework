package com.barogo.platform.delivery.dao;

import com.barogo.platform.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findBySeq(Long seq);

    List<Delivery> findByRequestDateBetween(LocalDateTime beforDate, LocalDateTime afterDate);
}
