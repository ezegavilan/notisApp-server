package com.gavilan.sistemanotificacionesdemo.model.services;

import com.gavilan.sistemanotificacionesdemo.model.dto.NotificacionEmail;

public interface MailService {

    void sendEmail(NotificacionEmail notificacionEmail, String url);

}
