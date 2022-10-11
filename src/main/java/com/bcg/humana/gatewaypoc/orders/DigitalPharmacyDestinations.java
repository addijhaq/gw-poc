package com.bcg.humana.gatewaypoc.orders;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "dp.api")
public class DigitalPharmacyDestinations {
    @NotNull
    private String prescriptionServiceUrl;

    public String getPrescriptionServiceUrl() {
        return prescriptionServiceUrl;
    }

    public void setPrescriptionServiceUrl(String prescriptionServiceUrl) {
        this.prescriptionServiceUrl = prescriptionServiceUrl;
    }
}
