package com.notlinode.shoppingcart.dto;

import com.notlinode.shoppingcart.model.PaymentMethod;
import com.notlinode.shoppingcart.model.PurchaseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PurchaseDto {

    private Long id;

    @NotNull
    private Instant purchaseDate;

    @NotNull
    private PurchaseStatus purchaseStatus;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private Long customerId;


}
