package com.lydiaplullc.CarLeasing.enums;

public enum PaymentStatus {
    PENDING("pending"),
    PAID("paid"),
    FAILED("failed");

    private final String value;

    PaymentStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for(PaymentStatus status: values()) {
            if(status.value.equals(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid payment status: " + value);
    }
}
