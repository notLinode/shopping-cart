package com.notlinode.shoppingcart.service.impl;

import com.notlinode.shoppingcart.dto.PurchaseDetailDto;
import com.notlinode.shoppingcart.model.Product;
import com.notlinode.shoppingcart.model.Purchase;
import com.notlinode.shoppingcart.model.PurchaseDetail;
import com.notlinode.shoppingcart.repository.ProductRepository;
import com.notlinode.shoppingcart.repository.PurchaseDetailRepository;
import com.notlinode.shoppingcart.repository.PurchaseRepository;
import com.notlinode.shoppingcart.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    private final PurchaseDetailRepository detailRepo;
    private final PurchaseRepository purchaseRepo;
    private final ProductRepository productRepo;

    public PurchaseDetailServiceImpl(PurchaseDetailRepository detailRepo, PurchaseRepository purchaseRepo, ProductRepository productRepo) {
        this.detailRepo = detailRepo;
        this.purchaseRepo = purchaseRepo;
        this.productRepo = productRepo;
    }

    @Override
    @Transactional
    public Optional<PurchaseDetailDto> addProduct(PurchaseDetailDto detailDto) {
        var detailOptional = mapDtoToEntity(detailDto);

        if (detailOptional.isEmpty()) {
            return Optional.empty();
        }

        var detail = detailRepo.save(detailOptional.get());

        return Optional.of(mapEntityToDto(detail));
    }

    @Override
    @Transactional
    public void removeProduct(Long purchaseId, Long productId) {
        detailRepo.deleteAllByPurchaseIdAndProductId(purchaseId, productId);
    }

    @Override
    public List<PurchaseDetailDto> getDetails(Long purchaseId) {
        var details = detailRepo.findAllByPurchaseId(purchaseId);
        return details.stream().map(this::mapEntityToDto).toList();
    }

    private PurchaseDetailDto mapEntityToDto(PurchaseDetail entity) {
        return PurchaseDetailDto.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .purchaseId(entity.getPurchase().getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }

    private Optional<PurchaseDetail> mapDtoToEntity(PurchaseDetailDto dto) {
        var purchaseOptional = purchaseRepo.findById(dto.getPurchaseId());
        var productOptional = productRepo.findById(dto.getProductId());

        if (purchaseOptional.isEmpty() || productOptional.isEmpty()) {
            return Optional.empty();
        }

        Purchase purchase = purchaseOptional.get();
        Product product = productOptional.get();
        Double price = product.getPrice() * dto.getQuantity();

        return Optional.of(PurchaseDetail.builder()
                .id(dto.getId())
                .purchase(purchase)
                .product(product)
                .price(price)
                .quantity(dto.getQuantity())
                .build());
    }

}
