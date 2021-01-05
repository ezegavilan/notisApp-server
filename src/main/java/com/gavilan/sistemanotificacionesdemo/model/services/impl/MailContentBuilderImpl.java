package com.gavilan.sistemanotificacionesdemo.model.services.impl;

import com.gavilan.sistemanotificacionesdemo.model.services.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@Slf4j
public class MailContentBuilderImpl implements MailContentBuilder {

    private final TemplateEngine templateEngine;

    @Override
    public String build(String message, String url) {
        Context ctx = new Context();
        ctx.setVariable("message", message);
        ctx.setVariable("url", url);

        log.info(String.valueOf(ctx));

        return this.templateEngine.process("newUserTemplate", ctx);
    }
}
