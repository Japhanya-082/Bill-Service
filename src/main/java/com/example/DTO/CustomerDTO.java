package com.example.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String moblieNumber;

    private String billingAddress;
    private String shippingAddress;

    private String taxId;
    private String paymentTerms;
    private String paymentMethod;

    private String status;
}
