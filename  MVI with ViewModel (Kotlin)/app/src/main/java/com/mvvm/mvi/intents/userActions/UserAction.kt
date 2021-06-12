package com.mvvm.mvi.intents.userActions

sealed class UserAction{
    object UserRestartClick : UserAction()
    class UserLoginClick(var userName : String, var password : String) : UserAction()
}
