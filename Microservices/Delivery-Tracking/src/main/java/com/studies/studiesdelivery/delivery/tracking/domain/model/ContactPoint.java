package com.studies.studiesdelivery.delivery.tracking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//Por se tratar de um value object o EqualsAndHashCode deve considerar todos os atributos
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class ContactPoint {

    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String name;
    private String phone;
}
