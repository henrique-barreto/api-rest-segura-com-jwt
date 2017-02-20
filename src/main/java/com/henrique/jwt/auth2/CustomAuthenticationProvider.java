package com.henrique.jwt.auth2;

import com.henrique.jwt.entity.Usuario;
import com.henrique.jwt.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henrique on 19/02/17.
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        System.out.println("Autenticando usuario: " + username + " com password: " + password);

        if (username == null || password == null) {
            throw new BadCredentialsException("Bad credentials. Username ou password null.");
        }

        Usuario user = findUserWith(username);
        AuthenticatedUser authenticatedUser = authenticateUser(user, username, password);
        return authenticatedUser;
    }

    private AuthenticatedUser authenticateUser(Usuario user, String username, String password) {

        System.out.println("Achou usuario, verificando username e password.");

        if (!user.getEmail().equals(username) || !user.getSenha().equals(password)) {
            System.out.println("Usuario ou senha incorreto.");
            throw new BadCredentialsException("Bad credentials. Username ou password incorretos.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user.getEmail(), user.getSenha(), user.getNome(), user.getEmail(), String.valueOf(user.getIdUsuario()), authorities);
        authenticatedUser.setAuthenticated(true);
        return authenticatedUser;

    }

    private Usuario findUserWith(String username) {

        System.out.println("Buscando por usuario = " + username);
        Usuario usuario = usuarioRepository.buscarPorUsuario(username);
        if (usuario == null) {
            System.out.println("Nao achou usuario, retornando lancando exception");
            throw new UsernameNotFoundException("Usuario com com username: " + username + " nao encontrado.");
        }

        return usuario;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

}
