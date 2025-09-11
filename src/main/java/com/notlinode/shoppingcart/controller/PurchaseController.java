package com.notlinode.shoppingcart.controller;

import com.notlinode.shoppingcart.dto.PurchaseDto;
import com.notlinode.shoppingcart.model.PurchaseStatus;
import com.notlinode.shoppingcart.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getPurchase(@PathVariable Long id) {
        Optional<PurchaseDto> purchaseOptional = purchaseService.findById(id);

        return purchaseOptional.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @PostMapping("/create")
    public ResponseEntity<PurchaseDto> createPurchase(@Valid @RequestBody PurchaseDto purchase) {
        Optional<PurchaseDto> purchaseOptional = purchaseService.save(purchase);

        return purchaseOptional.map(purchaseDto -> new ResponseEntity<>(purchaseDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long id) {
        purchaseService.deleteById(id);
        return ResponseEntity.ok("Purchase deleted");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseDto> changePurchaseStatus(@PathVariable Long id, @RequestBody PurchaseStatus purchaseStatus) {
        Optional<PurchaseDto> purchaseOptional = purchaseService.changePurchaseStatus(id, purchaseStatus);

        return purchaseOptional.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

}
