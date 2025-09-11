package com.notlinode.shoppingcart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    @Size(min = 127)
    private String firstName;

    @NotBlank
    @Size(max = 127)
    private String surname;

    @NotBlank
    @Size(max = 127)
    private String address;

    @NotBlank
    @Size(max = 127)
    private String email;

    @NotBlank
    @Size(max = 63)
    private String phone;

}
