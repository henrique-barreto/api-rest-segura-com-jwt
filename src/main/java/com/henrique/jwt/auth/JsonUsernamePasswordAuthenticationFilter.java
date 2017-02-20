package com.henrique.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by henrique on 19/02/17.
 */
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private String usernameParameter = "username";
    private String passwordParameter = "password";
    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            LoginRequestJson json = this.getLoginRequestJson(request);

            String username = json.getUsername().trim();
            System.out.println("username = " + username);
            String password = json.getPassword();
            System.out.println("password = " + password);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
//            return authRequest;
        }

    }

    private LoginRequestJson getLoginRequestJson(HttpServletRequest request) {

        try {
            String body = this.getRequestBody(request);
            System.out.println("body = " + body);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, LoginRequestJson.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Erro ao transformar corpo da requisicao em json.");
        }

    }

    private String getRequestBody(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Nao foi possivel ler o corpo da requiisicao");
        }

        String body = sb.toString();
        if (body == null)
            throw new AuthenticationServiceException("Nao foi possivel ler o corpo da requisicao. body NULL.");

        return body;
    }
}
