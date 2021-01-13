package com.gavilan.sistemanotificacionesdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String authToken;
    private String username;
    private String email;
    private String rol;
    private Date expiraEn;
}
