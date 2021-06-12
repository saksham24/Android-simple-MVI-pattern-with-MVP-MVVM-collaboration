package com.mvvm.mvi.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mvvm.mvi.R
import com.mvvm.mvi.intents.login.LoginStateIntent
import com.mvvm.mvi.intents.login.LoginStateIntent.*
import com.mvvm.mvi.viewModels.LoginViewModel
import com.mvvm.mvi.intents.userActions.UserAction.UserLoginClick
import com.mvvm.mvi.intents.userActions.UserAction.UserRestartClick
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.String

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initViewClicks()
        initObservers()
    }

    private fun initViewClicks() {
        buttonLogin.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }

    /**
     * Observers the intent login state data. Unidirectional flow with single entry point
     */
    private fun initObservers() {
        loginViewModel.loginStateIntentLiveData.observe(this, Observer {
            loginStateIntent -> renderView(loginStateIntent) })
    }

    private fun renderView(loginStateIntent: LoginStateIntent) {
        when (loginStateIntent) {
            is LoginNotInitiated -> {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = true
                edittextUsername.isEnabled = true
                edittextPassword.isEnabled = true
                edittextUsername.text.clear()
                edittextPassword.text.clear()
                btnReset.isEnabled = true
                textInfo.setText(R.string.text_start_info)
            }
            is LoginProgressing -> {
                progressBar.visibility = View.VISIBLE
                buttonLogin.isEnabled = false
                edittextUsername.isEnabled = false
                edittextPassword.isEnabled = false
                btnReset.isEnabled = false
                textInfo.setText(R.string.text_processing_login)
            }
            is LoginSuccess -> {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = false
                edittextUsername.isEnabled = false
                edittextPassword.isEnabled = false
                btnReset.isEnabled = true
                textInfo.text = String.format("%s%s", getString(R.string.label_token_received), loginStateIntent.loginResponseModel.token)
            }
            is LoginError -> {
                progressBar.visibility = View.GONE
                buttonLogin.isEnabled = true
                edittextUsername.isEnabled = true
                edittextPassword.isEnabled = true
                btnReset.isEnabled = true
                textInfo.text = loginStateIntent.error
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                val userLoginClick = UserLoginClick(edittextUsername.text.toString(), edittextPassword.text.toString())
                loginViewModel.processUserAction(userLoginClick)
            }
            R.id.btnReset -> loginViewModel.processUserAction(UserRestartClick)
        }
    }
}