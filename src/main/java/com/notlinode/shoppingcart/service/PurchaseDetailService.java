package com.notlinode.shoppingcart.service;

import com.notlinode.shoppingcart.dto.PurchaseDetailDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseDetailService {

    List<PurchaseDetailDto> getDetails(Long purchaseId);
    Optional<PurchaseDetailDto> addProduct(PurchaseDetailDto detailDto);
    void removeProduct(Long purchaseId, Long productId);

}
