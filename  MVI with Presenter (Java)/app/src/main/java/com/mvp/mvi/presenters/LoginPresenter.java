package com.mvp.mvi.presenters;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mvp.mvi.intents.login.LoginError;
import com.mvp.mvi.intents.login.LoginNotInitiated;
import com.mvp.mvi.intents.login.LoginProgressing;
import com.mvp.mvi.intents.login.LoginStateIntent;
import com.mvp.mvi.intents.login.LoginSuccess;
import com.mvp.mvi.intents.userActions.UserAction;
import com.mvp.mvi.intents.userActions.UserLoginClick;
import com.mvp.mvi.intents.userActions.UserRestartClick;
import com.mvp.mvi.models.LoginResponseModel;
import com.mvp.mvi.views.ILoginView;

public class LoginPresenter {
    private ILoginView<LoginStateIntent> iLoginView;

    public LoginPresenter(ILoginView<LoginStateIntent> iLoginView) {
        this.iLoginView = iLoginView;
    }

    /**
     * Unidirectional flow with single entry point of userAction intents
     */
    public void processUserAction(UserAction userAction) {
        if (userAction instanceof UserLoginClick) {
            hitLoginApiGetToken(userAction);
        } else if (userAction instanceof UserRestartClick) {
            iLoginView.onStateChanged(new LoginNotInitiated()); //login state changed to not initialised
        }
    }

    private void hitLoginApiGetToken(UserAction userAction) {
        iLoginView.onStateChanged(new LoginProgressing()); //login state changed to processing

        UserLoginClick userLoginClick = (UserLoginClick) userAction;
        Log.i(this.getClass().getSimpleName(), userLoginClick.getUserName() + ":" + userLoginClick.getPassword());

        new Handler().postDelayed(new Runnable() { // Just replacement of Login API
            @Override
            public void run() {
                if (userLoginClick.getUserName().isEmpty() || userLoginClick.getPassword().isEmpty()) {
                    //login state changed to error
                    iLoginView.onStateChanged(new LoginError("Error - Username and password can't be empty"));
                } else {
                    //login state changed to success result
                    LoginSuccess loginSuccess = new LoginSuccess(new LoginResponseModel("T_T_M_M_P_K_01", 5000));
                    iLoginView.onStateChanged(loginSuccess);
                }
            }
        }, 4000);
    }
}
