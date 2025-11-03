package com.studies.studiesdelivery.courier.management.api.controller;

import com.studies.studiesdelivery.courier.management.api.model.CourierInput;
import com.studies.studiesdelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.studies.studiesdelivery.courier.management.api.model.CourierPayoutResultModel;
import com.studies.studiesdelivery.courier.management.domain.model.Courier;
import com.studies.studiesdelivery.courier.management.domain.repository.CourierRepository;
import com.studies.studiesdelivery.courier.management.domain.service.CourierPayoutService;
import com.studies.studiesdelivery.courier.management.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
@Slf4j
public class CourierController {

    private final CourierRegistrationService courierRegistrationService;
    private final CourierRepository courierRepository;
    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput input) {
        return courierRegistrationService.create(input);
    }

    @PutMapping("/{courierId}")
    public Courier update(@PathVariable UUID courierId,
                          @Valid @RequestBody CourierInput input) {
        return courierRegistrationService.update(courierId, input);
    }

    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable) {
        log.info("FindAll Requested");
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId) {
        return this.courierRepository.findById(courierId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @SneakyThrows
    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input) {

        log.info("Calculating...");

        if(Math.random() < 0.1){
            throw new RuntimeException();
        }

        int millis = new Random().nextInt(450);
        Thread.sleep(millis);

        BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }


}
