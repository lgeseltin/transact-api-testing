package com.lgeseltins.backend.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AuthorizationDto {
    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String pwd;

    public AuthorizationDto(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
