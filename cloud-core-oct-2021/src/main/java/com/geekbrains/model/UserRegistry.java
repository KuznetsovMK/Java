package com.geekbrains.model;

public class UserRegistry extends AbstractMessage{

    private String login;
    private String password;

    public UserRegistry(String login, String password) {
        this.login = login;
        this.password = password;

        setType(Operation.REGISTRATION);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
