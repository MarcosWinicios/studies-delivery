package com.studies.studiesdelivery.delivery.tracking.api.controller;

import com.studies.studiesdelivery.delivery.tracking.api.model.CourierIdInput;
import com.studies.studiesdelivery.delivery.tracking.api.model.DeliveryInput;
import com.studies.studiesdelivery.delivery.tracking.domain.model.Delivery;
import com.studies.studiesdelivery.delivery.tracking.domain.repository.DeliveryRepository;
import com.studies.studiesdelivery.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.studies.studiesdelivery.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryCheckpointService deliveryCheckpointService;
    private final DeliveryRepository deliveryRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput deliveryInput) {
        return deliveryPreparationService.draft(deliveryInput);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput deliveryInput) {
        return deliveryPreparationService.edit(deliveryId, deliveryInput);
    }

    @SneakyThrows
    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable) {

        if (Math.random() < 0.7) {
            throw new RuntimeException();
        }

        int millis = new Random().nextInt(400);
        Thread.sleep(millis);
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{deliveryId}/placement")
    public void place(@PathVariable UUID deliveryId){
        this.deliveryCheckpointService.place(deliveryId);
    }

    @PostMapping("/{deliveryId}/pickups")
    public void pickups(@PathVariable UUID deliveryId,
                        @Valid @RequestBody CourierIdInput input){
        this.deliveryCheckpointService.pickup(deliveryId, input.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete(@PathVariable UUID deliveryId){
        this.deliveryCheckpointService.complete(deliveryId);

    }
}
