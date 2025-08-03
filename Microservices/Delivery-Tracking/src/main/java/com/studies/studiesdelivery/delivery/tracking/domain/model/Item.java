package com.studies.studiesdelivery.delivery.tracking.domain.model;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private Integer quantity;

}
