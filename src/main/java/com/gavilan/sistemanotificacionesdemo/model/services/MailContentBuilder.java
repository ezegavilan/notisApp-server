package com.gavilan.sistemanotificacionesdemo.model.services;

public interface MailContentBuilder {

    String build(String message, String url);

}
