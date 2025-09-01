package com.notlinode.shoppingcart.repository;

import com.notlinode.shoppingcart.model.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {

    List<PurchaseDetail> findAllByPurchaseId(Long purchaseId);
    void deleteAllByPurchaseIdAndProductId(Long purchaseId, Long productId);

}
