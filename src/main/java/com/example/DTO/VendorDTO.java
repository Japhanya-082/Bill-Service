package com.example.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {

    private long vendorId;
    private String vendorName;
    private String vendorAccountNumber;
    private String email;
    private String gstNumber;
    private Long phoneNumber;

    private VendorAddress vendorAddress; // Embedded object
}
