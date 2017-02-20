package com.henrique.jwt.auth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * Created by henrique on 19/02/17.
 */
public class TokenAuthenticationService {

    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private String secret = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";
    private static Key key = MacProvider.generateKey();

    public void addAuthentication(HttpServletResponse response, AuthenticatedUser authenticatedUser) {

        String subject = getJsonSubject(authenticatedUser.buildPayload());
        System.out.println("subject gerado = " + subject);
        String jsonWebToken = createToken(subject);

        response.addHeader(headerString, tokenPrefix + " " + jsonWebToken);

    }

    private String createToken(String subject) {

        try {
            String JWT = null;
            JWT = Jwts.builder()
                    .setSubject(subject)
                    .signWith(
                            SignatureAlgorithm.HS256,
                            "secret".getBytes("UTF-8")
                    )
                    .compact();
            return JWT;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Erro ao gerar token.");
        }
    }

    private String getJsonSubject(JWTPayload payload) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String subject = mapper.writeValueAsString(payload);
            return subject;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Erro ao transformar AuthenticatedUser em json.");
        }

    }

    public Authentication getAuthentication(HttpServletRequest request) {

        String token = getTokenFromRequest(request);
        System.out.println("token = " + token);

        try {

            if (token != null) {
                // parse the token.
                String subject = null;
                subject = Jwts.parser()
                        .setSigningKey("secret".getBytes("UTF-8"))
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();


                if (subject != null) {
                    return parseSubjectoToAuthenticatedUser(subject);
                }
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Erro.");
        }
    }

    private Authentication parseSubjectoToAuthenticatedUser(String subject) {

        try {
            System.out.println("subject = " + subject);
            ObjectMapper mapper = new ObjectMapper();
            JWTPayload jwt = mapper.readValue(subject, JWTPayload.class);

            return new AuthenticatedUser(jwt.getUsername(), jwt.get, jwt.getNome(), jwt.getEmail(), jwt.getIdUsuario(), jwt.getAuthorities());

        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Erro ao transformar json em AuthenticatedUser.");
        }

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String authToken = request.getHeader(headerString);
        if (authToken == null) {
            throw new AuthenticationServiceException("Atributo " + headerString + " nao existe no header.");
        }

        String[] split = authToken.split(" ");
        if (split.length != 2) {
            throw new AuthenticationServiceException("Token invalido. Deve ser: '" + tokenPrefix + " ACESS_TOKEN'");
        }

        String type = split[0];
        if (!type.equalsIgnoreCase(tokenPrefix)) {
            throw new AuthenticationServiceException("Token invalido. Deve ser: '" + tokenPrefix + " ACESS_TOKEN'");
        }
        return split[1];
    }

}
