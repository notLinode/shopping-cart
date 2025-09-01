package com.notlinode.shoppingcart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Purchase purchase;

    @ManyToOne
    @NotNull
    private Product product;

    @NotNull
    @Min(1)
    private Double price;

    @NotNull
    @Min(1)
    @Max(99)
    private Integer quantity;

}
