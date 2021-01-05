package com.gavilan.sistemanotificacionesdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionEmail {

    private String subject;
    private String recipient;
    private String body;
}
