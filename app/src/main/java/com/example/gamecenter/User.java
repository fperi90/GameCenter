package com.example.gamecenter;

public class User {

    private String name;
    private String password;
    private final int score2048;
    private final int scorePeg;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        score2048 = 0;
        scorePeg = 0;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
