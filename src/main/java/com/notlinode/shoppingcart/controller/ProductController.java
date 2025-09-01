package com.notlinode.shoppingcart.controller;

import com.notlinode.shoppingcart.model.Product;
import com.notlinode.shoppingcart.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getPaginated(Pageable pageRequest) {
        return ResponseEntity.ok(productRepo.findAll(pageRequest));
    }

}
