package com.mvp.mvi.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mvp.mvi.R;
import com.mvp.mvi.intents.login.LoginError;
import com.mvp.mvi.intents.login.LoginNotInitiated;
import com.mvp.mvi.intents.login.LoginProgressing;
import com.mvp.mvi.intents.login.LoginStateIntent;
import com.mvp.mvi.intents.login.LoginSuccess;
import com.mvp.mvi.intents.userActions.UserLoginClick;
import com.mvp.mvi.intents.userActions.UserRestartClick;
import com.mvp.mvi.presenters.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView<LoginStateIntent> {

    private LoginPresenter mLoginPresenter;
    private EditText edtUsername, edtPassword;
    private TextView tvInfo;
    private ProgressBar progressBar;
    private Button btnLogin;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);
        initViews();
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
     * Unidirectional flow with single entry point for state
     */
    @Override
    public void onStateChanged(LoginStateIntent loginStateIntent) {
        renderView(loginStateIntent);
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
                mLoginPresenter.processUserAction(userLoginClick);
                break;
            case R.id.btn_reset:
                mLoginPresenter.processUserAction(new UserRestartClick());
                break;
        }
    }
}