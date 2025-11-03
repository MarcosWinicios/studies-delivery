package com.studies.studiesdelivery.delivery.tracking.infrastructure.http.client;

import com.studies.studiesdelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierApiClient courierApiClient; //O Spring injeta um proxy

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try {
            return courierApiClient
                    .payoutCalculation(new CourierPayoutCalculationInput(distanceInKm))
                    .getPayoutFee();
        }catch (ResourceAccessException e){
            throw new GatewayTimeoutException(e);
        }catch (HttpServerErrorException | CallNotPermittedException | IllegalArgumentException e){
            throw new BadGatewayException(e);
        }


    }
}
