package com.mvvm.mvi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.mvvm.mvi.R;
import com.mvvm.mvi.databinding.ActivityLoginBinding;
import com.mvvm.mvi.intents.login.LoginError;
import com.mvvm.mvi.intents.login.LoginNotInitiated;
import com.mvvm.mvi.intents.login.LoginProgressing;
import com.mvvm.mvi.intents.login.LoginStateIntent;
import com.mvvm.mvi.intents.login.LoginSuccess;
import com.mvvm.mvi.intents.userActions.UserLoginClick;
import com.mvvm.mvi.intents.userActions.UserRestartClick;
import com.mvvm.mvi.viewModels.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    public LoginBindingData loginBindingData; // this is observed by views
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        activateBinding();
        initObservers();
    }

    private void activateBinding() {
        loginBindingData = new LoginBindingData(getString(R.string.text_start_info), true, false, View.GONE);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setModel(loginBindingData); // setting view data to layout binding
        binding.setOnClickListener(this); // give instance of onclickListener to view
    }

    /**
     * Observers the intent login state data. Unidirectional flow with single entry point
     */
    private void initObservers() {
        loginViewModel.getLoginStateIntentLiveData().observe(this, new Observer<LoginStateIntent>() {
            @Override
            public void onChanged(LoginStateIntent loginStateIntent) {
                renderView(loginStateIntent);
            }
        });
    }

    private void renderView(LoginStateIntent loginStateIntent) {
        if (loginStateIntent instanceof LoginNotInitiated) {
            loginBindingData.setData(getString(R.string.text_start_info), true, true, View.GONE);

        } else if (loginStateIntent instanceof LoginProgressing) {
            loginBindingData.setData(getString(R.string.text_processing_login), false, false, View.VISIBLE);

        } else if (loginStateIntent instanceof LoginSuccess) {
            LoginSuccess loginSuccess = (LoginSuccess) loginStateIntent;
            String infoText = String.format("%s%s", getString(R.string.label_token_received), loginSuccess.getLoginResponseModel().getToken());
            loginBindingData.setData(infoText, false, true, View.GONE);

        } else if (loginStateIntent instanceof LoginError) {
            LoginError loginError = (LoginError) loginStateIntent;
            loginBindingData.setData(loginError.getError(), true, true, View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                UserLoginClick userLoginClick = new UserLoginClick(binding.edittextUsername.getText().toString(),
                        binding.edittextPassword.getText().toString());
                loginViewModel.processUserAction(userLoginClick);
                break;
            case R.id.btn_reset:
                loginViewModel.processUserAction(new UserRestartClick());
                break;
        }
    }
    public void onClick() {

    }

    }