package com.notlinode.shoppingcart.controller;

import com.notlinode.shoppingcart.dto.PurchaseDetailDto;
import com.notlinode.shoppingcart.service.PurchaseDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase/{purchaseId}/detail")
public class PurchaseDetailController {

    private final PurchaseDetailService detailService;

    public PurchaseDetailController(PurchaseDetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDetailDto>> getDetails(@PathVariable Long purchaseId) {
        var details = detailService.getDetails(purchaseId);
        return new ResponseEntity<>(
                details,
                details.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<PurchaseDetailDto> addProduct(@PathVariable Long purchaseId, @Valid @RequestBody PurchaseDetailDto detailDto) {
        detailDto.setPurchaseId(purchaseId);
        var detailDtoOptional = detailService.addProduct(detailDto);
        return detailDtoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping
    public ResponseEntity<String> removeProduct(@PathVariable Long purchaseId, @RequestBody Long productId) {
        detailService.removeProduct(purchaseId, productId);
        return ResponseEntity.ok("Product removed");
    }

}
