package com.henrique.jwt.auth2;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Created by henrique on 19/02/17.
 */
public class AuthenticatedUser implements Authentication {

    private String username;
    private String password;
    private String nome;
    private String email;
    private String idUsuario;
    private List<GrantedAuthority> authorities;
    private boolean isAuthenticated;

    public AuthenticatedUser(String username, String password, String nome, String email, String idUsuario, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
        this.idUsuario = idUsuario;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.nome;
    }


    public JWTPayload buildPayload() {
        JWTPayload payload = new JWTPayload();
        payload.setNome(this.nome);
        payload.setEmail(this.email);
        payload.setAuthorities(this.authorities);
        payload.setIdUsuario(this.idUsuario);
        payload.setUsername(this.email);
        return payload;
    }


}

