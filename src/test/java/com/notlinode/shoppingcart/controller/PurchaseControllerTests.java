package com.notlinode.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notlinode.shoppingcart.dto.PurchaseDto;
import com.notlinode.shoppingcart.model.PaymentMethod;
import com.notlinode.shoppingcart.model.PurchaseStatus;
import com.notlinode.shoppingcart.service.impl.PurchaseServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Optional;

@WebMvcTest(controllers = PurchaseController.class)
public class PurchaseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseServiceImpl purchaseService;

    @Autowired
    private ObjectMapper objectMapper;

    private PurchaseDto purchaseDto;
    private String purchaseDtoString;

    @BeforeEach
    public void init() throws Exception {
        var instantNow = Instant.now();

        purchaseDto = PurchaseDto.builder()
                .id(1L)
                .purchaseDate(instantNow)
                .purchaseStatus(PurchaseStatus.SHIPPED)
                .paymentMethod(PaymentMethod.ON_RECEIPT)
                .customerId(1L)
                .build();

        purchaseDtoString = objectMapper.writeValueAsString(purchaseDto);
    }

    @Test
    public void PurchaseController_GetPurchase_ReturnOk() throws Exception {
        Mockito.when(purchaseService.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(purchaseDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchase/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(purchaseDtoString));
    }

    @Test
    public void PurchaseController_GetPurchase_ReturnNotFound() throws Exception {
        Mockito.when(purchaseService.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchase/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void PurchaseController_CreatePurchase_ReturnCreated() throws Exception {
        Mockito.when(purchaseService.save(Mockito.any(PurchaseDto.class)))
                .thenReturn(Optional.of(purchaseDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/create")
                        .content(purchaseDtoString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(purchaseDtoString));
    }

    @Test
    public void PurchaseController_CreatePurchase_ReturnBadRequestOnEmptyResponseFromService() throws Exception {
        Mockito.when(purchaseService.save(Mockito.any(PurchaseDto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/create")
                        .content(purchaseDtoString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void PurchaseController_CreatePurchase_ReturnBadRequestOnValidationError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/create")
                        .content("{\"123\":\"456\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void PurchaseController_DeletePurchase_ReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/purchase/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(purchaseService).deleteById(1L);
    }

    @Test
    public void PurchaseController_ChangePurchaseStatus_ReturnOk() throws Exception {
        purchaseDto.setPurchaseStatus(PurchaseStatus.CANCELLED);
        Mockito.when(purchaseService.changePurchaseStatus(Mockito.anyLong(), Mockito.any(PurchaseStatus.class)))
                .thenReturn(Optional.of(purchaseDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/purchase/1/status")
                        .content(objectMapper.writeValueAsString(PurchaseStatus.CANCELLED))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchaseStatus", CoreMatchers.is(PurchaseStatus.CANCELLED.name())));
    }

    @Test
    public void PurchaseController_ChangePurchaseStatus_ReturnBadRequest() throws Exception {
        Mockito.when(purchaseService.changePurchaseStatus(Mockito.anyLong(), Mockito.any(PurchaseStatus.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/purchase/123/status")
                        .content(objectMapper.writeValueAsString(PurchaseStatus.CANCELLED))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
