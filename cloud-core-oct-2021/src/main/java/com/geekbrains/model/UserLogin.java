package com.geekbrains.model;

public class UserLogin extends AbstractMessage{

    private String login;
    private String password;

    public UserLogin(String login, String password) {
        this.login = login;
        this.password = password;

        setType(Operation.LOGIN);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
