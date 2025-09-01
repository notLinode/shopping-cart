package com.notlinode.shoppingcart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date")
    @NotNull
    private Instant purchaseDate;

    @Column(name = "purchase_status")
    @NotNull
    private PurchaseStatus purchaseStatus;

    @Column(name = "payment_method")
    @NotNull
    private PaymentMethod paymentMethod;

    @ManyToOne(targetEntity = Customer.class, optional = false)
    @NotNull
    private Customer customer;

}
