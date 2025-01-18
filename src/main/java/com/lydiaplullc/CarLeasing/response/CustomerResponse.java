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
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.middleName = middleName != null ? middleName : "";
        this.gender = gender != null ? gender : "";
        this.dateOfBirth = dateOfBirth != null ? String.valueOf(dateOfBirth) : "";
        this.phone = phone != null ? phone : "";
        this.email = email != null ? email : "";
        this.password = password != null ? password : "";
        this.driverLicenseNumber = driverLicenseNumber != null ? driverLicenseNumber : "";
        this.driverLicenseFrontPhoto = driverLicenseFrontPhoto != null ? BlobUtils.converBlobToString(driverLicenseFrontPhoto) : "";
        this.driverLicenseBackPhoto = driverLicenseBackPhoto != null ? BlobUtils.converBlobToString(driverLicenseBackPhoto) : "";
        this.creditScore = creditScore != null ? String.valueOf(creditScore) : "";
        this.drivingYears = drivingYears != null ? String.valueOf(drivingYears) : "";
        this.address = address != null ? address : "";
        this.city = city != null ? city : "";
        this.state = state != null ? state : "";
        this.country = country != null ? country : "";
        this.postalCode = postalCode != null ? postalCode : "";
        this.emergencyContactPhone = emergencyContactPhone != null ? emergencyContactPhone : "";
        this.isDisabled = isDisabled != null ? String.valueOf(isDisabled) : "";
        this.disabilityDescription = disabilityDescription != null ? disabilityDescription : "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.registrationDate = registrationDate != null ? registrationDate.format(formatter) : "";
    }
}
