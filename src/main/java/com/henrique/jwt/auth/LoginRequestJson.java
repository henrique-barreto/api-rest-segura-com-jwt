package com.henrique.jwt.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by henrique on 19/02/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class LoginRequestJson {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null)
            username = "";

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null)
            password = "";

        this.password = password;
    }
}
