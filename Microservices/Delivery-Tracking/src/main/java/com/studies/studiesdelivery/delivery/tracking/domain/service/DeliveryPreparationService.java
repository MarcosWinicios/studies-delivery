package com.studies.studiesdelivery.delivery.tracking.domain.service;

import com.studies.studiesdelivery.delivery.tracking.api.model.ContactPointInput;
import com.studies.studiesdelivery.delivery.tracking.api.model.DeliveryInput;
import com.studies.studiesdelivery.delivery.tracking.api.model.ItemInput;
import com.studies.studiesdelivery.delivery.tracking.domain.exception.DomainException;
import com.studies.studiesdelivery.delivery.tracking.domain.model.ContactPoint;
import com.studies.studiesdelivery.delivery.tracking.domain.model.Delivery;
import com.studies.studiesdelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public Delivery draft(DeliveryInput input){
        Delivery delivery = Delivery.draft();

        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException("Delivery not found"));

        delivery.removeItems();

        handlePreparation(input, delivery);

        return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {

        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .phone(senderInput.getPhone())
                .name(senderInput.getName())
                .complement(senderInput.getComplement())
                .number(senderInput.getNumber())
                .zipCode(senderInput.getZipCode())
                .street(senderInput.getStreet())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .phone(recipientInput.getPhone())
                .name(recipientInput.getName())
                .complement(recipientInput.getComplement())
                .number(recipientInput.getNumber())
                .zipCode(recipientInput.getZipCode())
                .street(recipientInput.getStreet())
                .build();

        // Em um cenário reaal, deveria ser calculado com base na distância entre o remetente e o destinatário
        Duration expectedDeliveryTime = Duration.ofHours(3);
        BigDecimal distanceFee = new BigDecimal("10");

        BigDecimal payout = new BigDecimal("10");

        var preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(expectedDeliveryTime)
                .courierPayout(payout)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        for (ItemInput item : input.getItems()) {
            delivery.addItem(item.getName(), item.getQuantity());
        }
    }


}
