package com.gavilan.sistemanotificacionesdemo.model.exceptions;

public class MailSenderException extends RuntimeException {
    public MailSenderException(String exMessage) {
        super(exMessage);
    }
}
