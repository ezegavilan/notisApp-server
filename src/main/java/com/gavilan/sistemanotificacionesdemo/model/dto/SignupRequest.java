package com.gavilan.sistemanotificacionesdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NotNull(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotNull(message = "El email del usuario es obligatorio")
    private String email;

    @NotNull(message = "La contraseña es obligatoria.")
    private String password;

}
