package com.gavilan.sistemanotificacionesdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notificacion {
    private String title;
    private String content;
    private String category;
    private String actionUrl;
    private String recipient;
}
