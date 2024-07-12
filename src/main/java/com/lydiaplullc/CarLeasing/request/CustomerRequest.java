package com.lydiaplullc.CarLeasing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String password;
    private String driverLicenseNumber;
//    private Blob driverLicenseFrontPhoto;
//    private Blob driverLicenseBackPhoto;
    private Integer creditScore;
    private Integer drivingYears;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String emergencyContactPhone;
    private Boolean isDisabled;
    private String disabilityDescription;
//    private LocalDateTime registrationDate;

}