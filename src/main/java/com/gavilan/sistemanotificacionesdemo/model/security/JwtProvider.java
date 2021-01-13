package com.gavilan.sistemanotificacionesdemo.model.security;

import com.gavilan.sistemanotificacionesdemo.model.exceptions.NotisException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
@ConfigurationProperties(prefix = "jwt")
public class JwtProvider {

    private KeyStore keyStore;
    private Long expirationInMillis;
    private String secretKey;

    @PostConstruct
    public void iniciar() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = getClass().getResourceAsStream("/notis.jks");
            keyStore.load(inputStream, this.secretKey.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new NotisException("Excepción al cargar keystore: ".concat(e.getMessage()));
        }
    }

    public String generarToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(this.getPrivateKey())
                .setExpiration(new Date(new Date().getTime() + this.expirationInMillis))
                .compact();
    }

    public PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) this.keyStore.getKey("notis", this.secretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new NotisException("Excepción al obtener private key: ".concat(e.getMessage()));
        }
    }

    public PublicKey getPublicKey() {
        try {
            return this.keyStore.getCertificate("notis").getPublicKey();
        } catch (KeyStoreException e) {
            throw new NotisException("Excepción al obtener public key: ".concat(e.getMessage()));
        }
    }

    public boolean validarToken(String jwt) {
        parser().setSigningKey(this.getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String jwt) {
        Claims claims = parser()
                .setSigningKey(this.getPublicKey())
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    public Long getExpirationInMillis() {
        return this.expirationInMillis;
    }

    public void setExpirationInMillis(Long expirationInMillis) {
        this.expirationInMillis = expirationInMillis;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
