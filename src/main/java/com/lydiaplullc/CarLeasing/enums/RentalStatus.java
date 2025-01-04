package com.lydiaplullc.CarLeasing.enums;

public enum RentalStatus {
    RENTED("rented"),
    CANCELED("canceled");

    private final String value;

    RentalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static com.lydiaplullc.CarLeasing.enums.RentalStatus fromValue(String value) {
        for (com.lydiaplullc.CarLeasing.enums.RentalStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid RentalStatus status: " + value);
    }
}
