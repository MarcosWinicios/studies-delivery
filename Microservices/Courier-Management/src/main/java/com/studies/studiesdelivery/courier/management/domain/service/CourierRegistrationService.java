package com.studies.studiesdelivery.courier.management.domain.service;

import com.studies.studiesdelivery.courier.management.api.model.CourierInput;
import com.studies.studiesdelivery.courier.management.domain.exception.DomainException;
import com.studies.studiesdelivery.courier.management.domain.model.Courier;
import com.studies.studiesdelivery.courier.management.domain.repository.CourierRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CourierRegistrationService {

    private final CourierRepository courierRepository;

    public Courier create(@Valid CourierInput input){
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());
        return this.courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, @Valid CourierInput input){
        Courier courier = this.courierRepository.findById(courierId)
                .orElseThrow(() -> new DomainException("Courier not found"));

        courier.setName(input.getName());
        courier.setPhone(input.getPhone());

        return this.courierRepository.saveAndFlush(courier);
    }
}
