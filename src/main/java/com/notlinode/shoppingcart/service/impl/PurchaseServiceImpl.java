package com.notlinode.shoppingcart.service.impl;

import com.notlinode.shoppingcart.dto.PurchaseDto;
import com.notlinode.shoppingcart.model.Customer;
import com.notlinode.shoppingcart.model.Purchase;
import com.notlinode.shoppingcart.model.PurchaseStatus;
import com.notlinode.shoppingcart.repository.CustomerRepository;
import com.notlinode.shoppingcart.repository.PurchaseRepository;
import com.notlinode.shoppingcart.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepo;
    private final CustomerRepository customerRepo;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepo, CustomerRepository customerRepo) {
        this.purchaseRepo = purchaseRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Optional<PurchaseDto> findById(Long id) {
        Optional<Purchase> purchaseOptional = purchaseRepo.findById(id);
        return purchaseOptional.map(this::mapEntityToDto);
    }

    @Override
    @Transactional
    public Optional<PurchaseDto> save(PurchaseDto purchaseDto) {
        Optional<Purchase> purchaseOptional = mapDtoToEntity(purchaseDto);

        if (purchaseOptional.isEmpty()) {
            return Optional.empty();
        }

        Purchase purchaseEntity = purchaseRepo.save(purchaseOptional.get());
        purchaseDto.setId(purchaseEntity.getId());
        return Optional.of(purchaseDto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        purchaseRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<PurchaseDto> changePurchaseStatus(Long id, PurchaseStatus status) {
        Optional<Purchase> purchaseOptional = purchaseRepo.findById(id);

        if (purchaseOptional.isEmpty()) {
            return Optional.empty();
        }

        Purchase purchase = purchaseOptional.get();
        purchase.setPurchaseStatus(status);
        purchase = purchaseRepo.save(purchase);
        return Optional.of(mapEntityToDto(purchase));
    }

    private PurchaseDto mapEntityToDto(Purchase entity) {
        return PurchaseDto.builder()
                .id(entity.getId())
                .purchaseDate(entity.getPurchaseDate())
                .purchaseStatus(entity.getPurchaseStatus())
                .paymentMethod(entity.getPaymentMethod())
                .customerId(entity.getCustomer().getId())
                .build();
    }

    private Optional<Purchase> mapDtoToEntity(PurchaseDto dto) {
        Optional<Customer> customerOptional = customerRepo.findById(dto.getCustomerId());
        return customerOptional.map(customer -> Purchase.builder()
                .id(dto.getId())
                .purchaseDate(dto.getPurchaseDate())
                .purchaseStatus(dto.getPurchaseStatus())
                .paymentMethod(dto.getPaymentMethod())
                .customer(customer)
                .build());

    }

}
