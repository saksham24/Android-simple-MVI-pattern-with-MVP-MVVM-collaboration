package com.mvvm.mvi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mvvm.mvi.R;
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
    private EditText edtUsername, edtPassword;
    private TextView tvInfo;
    private ProgressBar progressBar;
    private Button btnLogin;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initViews();
        initObservers();
    }

    private void initViews() {
        edtUsername = (EditText) findViewById(R.id.edittext_username);
        edtPassword = (EditText) findViewById(R.id.edittext_password);
        tvInfo = (TextView) findViewById(R.id.text_info);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnLogin = (Button) findViewById(R.id.button_login);
        btnReset = (Button) findViewById(R.id.btn_reset);
        btnLogin.setOnClickListener(this);
        btnReset.setOnClickListener(this);
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
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            edtUsername.setEnabled(true);
            edtPassword.setEnabled(true);
            edtUsername.getText().clear();
            edtPassword.getText().clear();
            btnReset.setEnabled(true);
            tvInfo.setText(R.string.text_start_info);

        } else if (loginStateIntent instanceof LoginProgressing) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            edtUsername.setEnabled(false);
            edtPassword.setEnabled(false);
            btnReset.setEnabled(false);
            tvInfo.setText(R.string.text_processing_login);

        } else if (loginStateIntent instanceof LoginSuccess) {
            LoginSuccess loginSuccess = (LoginSuccess) loginStateIntent;

            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(false);
            edtUsername.setEnabled(false);
            edtPassword.setEnabled(false);
            btnReset.setEnabled(true);
            tvInfo.setText(String.format("%s%s", getString(R.string.label_token_received), loginSuccess.getLoginResponseModel().getToken()));

        } else if (loginStateIntent instanceof LoginError) {
            LoginError loginError = (LoginError) loginStateIntent;

            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            edtUsername.setEnabled(true);
            edtPassword.setEnabled(true);
            btnReset.setEnabled(true);
            tvInfo.setText(loginError.getError());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                UserLoginClick userLoginClick = new UserLoginClick(edtUsername.getText().toString(), edtPassword.getText().toString());
                loginViewModel.processUserAction(userLoginClick);
                break;
            case R.id.btn_reset:
                loginViewModel.processUserAction(new UserRestartClick());
                break;
        }
    }
}