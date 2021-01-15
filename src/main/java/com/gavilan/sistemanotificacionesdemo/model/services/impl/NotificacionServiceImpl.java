package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.dto.Notificacion;
import com.gavilan.sistemanotificacionesdemo.model.entities.Post;
import com.gavilan.sistemanotificacionesdemo.model.services.NotificacionService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {
    private static final String CLIENT_URL = "http://localhost:4200";

    @Async
    @Override
    public void enviarNotificacion(Post post) {
        Notificacion notificacion = this.crearNotificacion(post);

        Map<String, Object> data = new HashMap<>();
        data.put("title", notificacion.getTitle());
        data.put("content", notificacion.getContent());
        data.put("category", notificacion.getCategory());
        data.put("action_url", CLIENT_URL.concat(notificacion.getActionUrl()));
        data.put("recipient", Map.of("email", notificacion.getRecipient()));

        HttpResponse<JsonNode> request = Unirest.post("https://api.magicbell.io/notifications")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("X-MAGICBELL-API-SECRET", "c7d010e7def46aec61c8524f85371103a1e48fb0")
                .header("X-MAGICBELL-API-KEY", "fe895cc2053486135913f5557c5fae1ca049a04e")
                .body(Map.of("notification", data)).asJson();

        log.info(String.valueOf(request.getBody()));
    }

    private Notificacion crearNotificacion(Post post) {
        return Notificacion.builder()
                .title("Nuevo post creado")
                .content("¡Tu post ' " + post.getNombrePost() + "' fue creado con éxito!")
                .category("new_message")
                .actionUrl("/profile/my-posts")
                .recipient(post.getUsuario().getEmail()).build();
    }
}
