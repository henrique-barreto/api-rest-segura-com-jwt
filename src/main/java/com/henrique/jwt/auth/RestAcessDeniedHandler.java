package com.henrique.jwt.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by henrique on 19/02/17.
 */
@Component
public class RestAcessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        String msg = "{\n" +
                "\"mensagem\": \"Acesso negado. Usuario nao tem permissao.\",\n" +
                "\"codigo\": \"403\"\n" +
                "}";
        out.print(msg);
        out.flush();
        out.close();

    }

}
