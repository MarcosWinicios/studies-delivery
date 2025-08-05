package com.studies.studiesdelivery.delivery.tracking.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@EqualsAndHashCode //Por se tratar de um value object o EqualsAndHashCode deve considerar todos os atributos
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ContactPoint {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
