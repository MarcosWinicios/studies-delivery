package com.studies.studiesdelivery.delivery.tracking.domain.repository;

import com.studies.studiesdelivery.delivery.tracking.domain.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID>{
}
