package com.notlinode.shoppingcart.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseDetailDto {

    private Long id;
    private Long purchaseId;

    @NotNull
    private Long productId;

    private Double price;

    @NotNull
    @Min(1)
    @Max(99)
    private Integer quantity;

}
