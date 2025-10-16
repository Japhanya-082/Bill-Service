package com.example.status;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BillStatus {
	PENDING,
    PAID,
    PARTIALLY_PAID,
    OverDue;
    
    @JsonCreator
    public static BillStatus fromValue(String value) {
        for (BillStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
