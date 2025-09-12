package com.notlinode.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notlinode.shoppingcart.dto.PurchaseDetailDto;
import com.notlinode.shoppingcart.service.impl.PurchaseDetailServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = PurchaseDetailController.class)
public class PurchaseDetailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseDetailServiceImpl detailService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<PurchaseDetailDto> details;

    @BeforeEach
    public void init() {
        details = new ArrayList<>();
        for (long i = 1; i <= 10; i++) {
            details.add(
                    PurchaseDetailDto.builder()
                            .id(i)
                            .purchaseId(1L)
                            .productId(i)
                            .price((double)i * 11.0)
                            .quantity(1)
                            .build()
            );
        }
    }

    @Test
    public void PurchaseDetailController_GetDetails_ReturnOk() throws Exception {
        Mockito.when(detailService.getDetails(Mockito.anyLong()))
                .thenReturn(details);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchase/1/detail")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(details)));
    }

    @Test
    public void PurchaseDetailController_GetDetails_ReturnNotFound() throws Exception {
        Mockito.when(detailService.getDetails(Mockito.anyLong()))
                .thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/purchase/123/detail")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void PurchaseDetailController_AddProduct_ReturnOk() throws Exception {
        PurchaseDetailDto detailDto = details.getFirst();

        Mockito.when(detailService.addProduct(Mockito.any(PurchaseDetailDto.class)))
                .thenReturn(Optional.of(detailDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/1/detail")
                        .content(objectMapper.writeValueAsString(detailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(detailDto)));
    }

    @Test
    public void PurchaseDetailController_GetDetails_ReturnBadRequestOnEmptyResponseFromService() throws Exception {
        PurchaseDetailDto detailDto = details.getFirst();

        Mockito.when(detailService.addProduct(Mockito.any(PurchaseDetailDto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/123/detail")
                        .content(objectMapper.writeValueAsString(detailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void PurchaseDetailController_GetDetails_ReturnBadRequestOnValidationError() throws Exception {
        PurchaseDetailDto detailDtoInvalid = PurchaseDetailDto.builder()
                .productId(1L)
                .price(0.0)
                .quantity(0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/purchase/1/detail")
                        .content(objectMapper.writeValueAsString(detailDtoInvalid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void PurchaseDetailController_RemoveProduct_ReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/purchase/1/detail")
                        .content("1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void PurchaseDetailController_RemoveProduct_ReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/purchase/1/detail")
                        .content("null")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
