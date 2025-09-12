package com.notlinode.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notlinode.shoppingcart.model.Product;
import com.notlinode.shoppingcart.repository.ProductRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductRepository productRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private JSONParser jsonParser;
    private List<Product> products;

    @BeforeEach
    public void init() {
        jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);

        products = new ArrayList<>(20);
        for (long i = 1; i <= 20; i++) {
            products.add(
                    Product.builder()
                            .id(i)
                            .name("Product " + i)
                            .price((double)i * 11.0)
                            .description("Product " + i + " Description")
                            .inStock(true)
                            .build()
            );
        }
    }

    @Test
    public void ProductController_GetPaginated_ReturnPage() throws Exception {
        Mockito.when(productRepo.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(products));

        JSONArray listJson = (JSONArray)jsonParser.parse(objectMapper.writeValueAsString(products));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/product")
                        .content(objectMapper.writeValueAsString(Pageable.ofSize(20)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content")
                        .value(Matchers.containsInAnyOrder(listJson.toArray())));
    }

}
