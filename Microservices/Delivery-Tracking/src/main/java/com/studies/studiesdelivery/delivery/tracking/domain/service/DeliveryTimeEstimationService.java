package com.studies.studiesdelivery.delivery.tracking.domain.service;

import com.studies.studiesdelivery.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);
}
