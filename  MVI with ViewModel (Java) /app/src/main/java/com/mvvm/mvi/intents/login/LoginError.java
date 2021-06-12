package com.mvvm.mvi.intents.login;

public class LoginError extends LoginStateIntent{
    private String error;

    public LoginError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
