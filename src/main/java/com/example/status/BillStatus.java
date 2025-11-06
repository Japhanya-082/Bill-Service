package com.example.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BillStatus {
    PENDING,
    PAID,
    PARTIALLY_PAID,
    OVERDUE;  // fixed casing 

    @JsonCreator
    public static BillStatus fromValue(String value) {
        if (value == null) return null;
        for (BillStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status; //  Case-insensitive
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }

    @JsonValue
    public String toValue() {
        return name(); // Always serialize as uppercase
    }
}
