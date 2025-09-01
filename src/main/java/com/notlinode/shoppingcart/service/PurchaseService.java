package com.notlinode.shoppingcart.service;

import com.notlinode.shoppingcart.dto.PurchaseDto;
import com.notlinode.shoppingcart.model.PurchaseStatus;

import java.util.Optional;

public interface PurchaseService {

    Optional<PurchaseDto> findById(Long id);
    Optional<PurchaseDto> save(PurchaseDto purchaseDto);
    void deleteById(Long id);
    Optional<PurchaseDto> changePurchaseStatus(Long id, PurchaseStatus status);

}
