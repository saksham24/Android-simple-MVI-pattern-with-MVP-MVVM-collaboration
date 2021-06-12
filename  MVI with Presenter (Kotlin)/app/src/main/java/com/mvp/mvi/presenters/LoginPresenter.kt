package com.mvp.mvi.presenters

import android.os.Handler
import android.util.Log
import com.mvp.mvi.intents.login.LoginStateIntent
import com.mvp.mvi.intents.login.LoginStateIntent.*
import com.mvp.mvi.intents.userActions.UserAction
import com.mvp.mvi.intents.userActions.UserAction.UserLoginClick
import com.mvp.mvi.intents.userActions.UserAction.UserRestartClick
import com.mvp.mvi.models.LoginResponseModel
import com.mvp.mvi.views.ILoginView

class LoginPresenter(private val iLoginView: ILoginView<LoginStateIntent>) {

    /**
     * Unidirectional flow with single entry point of userAction intents
     */
    fun processUserAction(userAction: UserAction?) {
        when(userAction) {
            is UserLoginClick -> hitLoginApiGetToken(userAction)
            is UserRestartClick -> iLoginView.onStateChanged(LoginNotInitiated) //login state changed to not initialised
        }
    }

    private fun hitLoginApiGetToken(userAction: UserAction) {
        iLoginView.onStateChanged(LoginProgressing) //login state changed to processing
        val userLoginClick = userAction as UserLoginClick
        Log.i(this.javaClass.simpleName, userLoginClick.userName + ":" + userLoginClick.password)
        Handler().postDelayed({
            if (userLoginClick.userName.isEmpty() || userLoginClick.password.isEmpty()) {
                //login state changed to error
                iLoginView.onStateChanged(LoginError("Error - Username and password can't be empty"))
            } else {
                //login state changed to success result
                val loginSuccess = LoginSuccess(LoginResponseModel("T_T_M_M_P_K_01", 5000))
                iLoginView.onStateChanged(loginSuccess)
            }
        }, 4000)
    }

}
