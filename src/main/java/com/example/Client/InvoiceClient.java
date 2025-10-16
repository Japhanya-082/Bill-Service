package com.example.Client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVOICE-SERVICE")
public interface InvoiceClient {

    @PutMapping("/manual-invoice/update-status/{invoiceNumber}")
    void updateInvoiceStatus(
        @PathVariable("invoiceNumber") String invoiceNumber,
        @RequestBody Map<String, String> payload);
}
