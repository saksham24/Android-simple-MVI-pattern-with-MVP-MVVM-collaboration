package com.mvvm.mvi.models;

public class LoginResponseModel {
    private String token;
    private int tokenExpiryTime;

    public LoginResponseModel(String token, int tokenExpiryTime) {
        this.token = token;
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenExpiryTime() {
        return tokenExpiryTime;
    }

    public void setTokenExpiryTime(int tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }
}
