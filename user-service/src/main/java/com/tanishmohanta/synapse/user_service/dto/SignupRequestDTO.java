package com.tanishmohanta.synapse.user_service.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String name;
    private String email;
    private String password;
}
