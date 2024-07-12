package com.lydiaplullc.CarLeasing.response;

import com.lydiaplullc.CarLeasing.utils.BlobUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String password;
    private String driverLicenseNumber;
    private String driverLicenseFrontPhoto;
    private String driverLicenseBackPhoto;
    private String creditScore;
    private String drivingYears;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String emergencyContactPhone;
    private String isDisabled;
    private String disabilityDescription;
    private String registrationDate;

    public CustomerResponse(Long id, String firstName, String lastName, String middleName, String gender, LocalDate dateOfBirth, String phone,
                            String email, String password, String driverLicenseNumber, Blob driverLicenseFrontPhoto, Blob driverLicenseBackPhoto,
                            Integer creditScore, Integer drivingYears, String address, String city,String state,String country,String postalCode,
                            String emergencyContactPhone, Boolean isDisabled, String disabilityDescription, LocalDateTime registrationDate) {
        this.id = String.valueOf(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth != null ? String.valueOf(dateOfBirth) : "";
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseFrontPhoto = BlobUtils.converBlobToString(driverLicenseFrontPhoto);
        this.driverLicenseBackPhoto = BlobUtils.converBlobToString(driverLicenseBackPhoto);
        this.creditScore = String.valueOf(creditScore);
        this.drivingYears = String.valueOf(drivingYears);
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.emergencyContactPhone = emergencyContactPhone;
        this.isDisabled = isDisabled!=null ? String.valueOf(isDisabled) : "";
        this.disabilityDescription = disabilityDescription;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.registrationDate = registrationDate != null ? registrationDate.format(formatter) : "";
    }
}
