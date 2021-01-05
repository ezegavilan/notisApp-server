package com.gavilan.sistemanotificacionesdemo.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NotNull(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotNull(message = "La contraseña es obligatoria.")
    private String password;
}
