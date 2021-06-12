package com.mvp.mvi.intents.login

import com.mvp.mvi.models.LoginResponseModel

sealed class LoginStateIntent{
    object LoginNotInitiated : LoginStateIntent()
    object LoginProgressing : LoginStateIntent()
    class LoginSuccess(var loginResponseModel: LoginResponseModel) : LoginStateIntent()
    class LoginError(var error : String) : LoginStateIntent()
}
