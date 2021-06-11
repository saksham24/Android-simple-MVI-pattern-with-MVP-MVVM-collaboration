package com.mvvm.mvi.viewModels;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mvvm.mvi.intents.login.LoginError;
import com.mvvm.mvi.intents.login.LoginNotInitiated;
import com.mvvm.mvi.intents.login.LoginProgressing;
import com.mvvm.mvi.intents.login.LoginStateIntent;
import com.mvvm.mvi.intents.login.LoginSuccess;
import com.mvvm.mvi.intents.userActions.UserAction;
import com.mvvm.mvi.intents.userActions.UserRestartClick;
import com.mvvm.mvi.intents.userActions.UserLoginClick;
import com.mvvm.mvi.models.LoginResponseModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginStateIntent> loginStateIntentLiveData = new MutableLiveData<>();

    public MutableLiveData<LoginStateIntent> getLoginStateIntentLiveData() {
        return loginStateIntentLiveData;
    }

    public void setLoginStateIntentLiveData(LoginStateIntent loginStateIntent) {
        this.loginStateIntentLiveData.setValue(loginStateIntent);
    }

    /**
     * Unidirectional flow with single entry point of userAction intents
     */
    public void processUserAction(UserAction userAction) {
        if (userAction instanceof UserLoginClick) {
            hitLoginApiGetToken(userAction);
        } else if (userAction instanceof UserRestartClick) {
            setLoginStateIntentLiveData(new LoginNotInitiated()); //login state changed to not initialised
        }
    }

    private void hitLoginApiGetToken(UserAction userAction) {
        setLoginStateIntentLiveData(new LoginProgressing()); //login state changed to processing

        UserLoginClick userLoginClick = (UserLoginClick) userAction;
        Log.i(this.getClass().getSimpleName(), userLoginClick.getUserName() + ":" + userLoginClick.getPassword());

        new Handler().postDelayed(new Runnable() { // Just replacement of Login API
            @Override
            public void run() {
                if (userLoginClick.getUserName().isEmpty() || userLoginClick.getPassword().isEmpty()) {
                    //login state changed to error
                    setLoginStateIntentLiveData(new LoginError("Error - Username and password can't be empty"));
                } else {
                    //login state changed to success result
                    LoginSuccess loginSuccess = new LoginSuccess(new LoginResponseModel("T_T_M_M_P_K_01", 5000));
                    setLoginStateIntentLiveData(loginSuccess);
                }
            }
        }, 4000);
    }
}
