package com.example.converter;

import com.example.status.BillStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BillStatusConverter implements AttributeConverter<BillStatus, String> {

    @Override
    public String convertToDatabaseColumn(BillStatus status) {
        return (status == null) ? null : status.name(); // always store uppercase
    }

    @Override
    public BillStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        for (BillStatus status : BillStatus.values()) {
            if (status.name().equalsIgnoreCase(dbData)) {
                return status; //  case-insensitive match
            }
        }
        throw new IllegalArgumentException("Unknown BillStatus: " + dbData);
    }
}
