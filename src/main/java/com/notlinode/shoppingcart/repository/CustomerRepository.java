package com.notlinode.shoppingcart.repository;

import com.notlinode.shoppingcart.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
