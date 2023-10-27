package com.practise.csvTask.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CsvData {

    private String DeliveryAddressType;
    private String DeliveryAddressTypeCode;

    @JsonProperty("Ident Method")
    private String IdentMethod;
    private String StandardDeliveryIndicator;
    private String CarrierID;
    private String CarrierName;
    private String Currency;
    private String DeliveryServiceDescription;
    private String DeliveryServiceID;
    private String ShippingCondition;
    private String ShippingConditionDescription;
    private String ShippingGrossFee;
    private String ShippingGrossFeeFormat;
    private String ShippingNetFee;
    private String ShippingNetFeeFormat;

}
