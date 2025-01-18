package com.lydiaplullc.CarLeasing.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCustomerResponse {
    private String id;
    private String email;

    public LoginCustomerResponse(Long id, String email){
        this.id = String.valueOf(id);
        this.email = email;
    }
}
