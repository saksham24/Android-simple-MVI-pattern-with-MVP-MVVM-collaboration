package com.mvvm.mvi.viewModels

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.mvi.intents.login.LoginStateIntent
import com.mvvm.mvi.intents.login.LoginStateIntent.*
import com.mvvm.mvi.intents.userActions.UserAction
import com.mvvm.mvi.intents.userActions.UserAction.UserLoginClick
import com.mvvm.mvi.intents.userActions.UserAction.UserRestartClick
import com.mvvm.mvi.models.LoginResponseModel

class LoginViewModel : ViewModel() {
    val loginStateIntentLiveData = MutableLiveData<LoginStateIntent>()

    private fun setLoginStateIntentLiveData(loginStateIntent: LoginStateIntent) {
        loginStateIntentLiveData.value = loginStateIntent
    }

    /**
     * Unidirectional flow with single entry point of userAction intents
     */
    fun processUserAction(userAction: UserAction) {
        when(userAction) {
            is UserLoginClick -> hitLoginApiGetToken(userAction)
            is UserRestartClick -> setLoginStateIntentLiveData(LoginNotInitiated) //login state changed to not initialised
        }
    }

    private fun hitLoginApiGetToken(userAction: UserAction) {
        setLoginStateIntentLiveData(LoginProgressing) //login state changed to processing
        val userLoginClick = userAction as UserLoginClick
        Log.i(this.javaClass.simpleName, userLoginClick.userName + ":" + userLoginClick.password)

        Handler().postDelayed({
            if (userLoginClick.userName.isEmpty() || userLoginClick.password.isEmpty()) {
                //login state changed to error
                setLoginStateIntentLiveData(LoginError("Error - Username and password can't be empty"))
            } else {
                //login state changed to success result
                val loginSuccess = LoginSuccess(LoginResponseModel("T_T_M_M_P_K_01", 5000))
                setLoginStateIntentLiveData(loginSuccess)
            }
        }, 4000)
    }
}
