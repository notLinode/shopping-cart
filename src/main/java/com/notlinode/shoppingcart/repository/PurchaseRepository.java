package com.notlinode.shoppingcart.repository;

import com.notlinode.shoppingcart.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>  {
}
