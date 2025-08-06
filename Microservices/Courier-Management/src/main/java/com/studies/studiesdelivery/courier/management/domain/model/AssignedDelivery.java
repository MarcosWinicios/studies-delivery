package com.studies.studiesdelivery.courier.management.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AssignedDelivery {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private OffsetDateTime assignedAt;

    @Getter(AccessLevel.PRIVATE)
    @ManyToOne(optional = false)
    private Courier courier;

    static AssignedDelivery pending(UUID deliveryId, Courier courier) {
        AssignedDelivery assignedDelivery = new AssignedDelivery();
        assignedDelivery.setId(deliveryId);
        assignedDelivery.setAssignedAt(OffsetDateTime.now());
        assignedDelivery.setCourier(courier);
        return assignedDelivery;
    }

}
