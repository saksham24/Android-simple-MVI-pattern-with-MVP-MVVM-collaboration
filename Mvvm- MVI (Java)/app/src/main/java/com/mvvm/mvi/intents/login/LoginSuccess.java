package com.mvvm.mvi.intents.login;

import com.mvvm.mvi.models.LoginResponseModel;

public class LoginSuccess extends LoginStateIntent{
    private LoginResponseModel loginResponseModel;

    public LoginSuccess(LoginResponseModel loginResponseModel) {
        this.loginResponseModel = loginResponseModel;
    }

    public LoginResponseModel getLoginResponseModel() {
        return loginResponseModel;
    }

    public void setLoginResponseModel(LoginResponseModel loginResponseModel) {
        this.loginResponseModel = loginResponseModel;
    }
}
