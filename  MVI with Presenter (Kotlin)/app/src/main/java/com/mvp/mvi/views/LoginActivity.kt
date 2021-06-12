package com.mvp.mvi.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mvp.mvi.R
import com.mvp.mvi.intents.login.LoginStateIntent
import com.mvp.mvi.intents.login.LoginStateIntent.*
import com.mvp.mvi.intents.userActions.UserAction.UserLoginClick
import com.mvp.mvi.intents.userActions.UserAction.UserRestartClick
import com.mvp.mvi.presenters.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.String

class LoginActivity : AppCompatActivity(), View.OnClickListener, ILoginView<LoginStateIntent> {
    private lateinit var mLoginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mLoginPresenter = LoginPresenter(this)
        initViewClicks()
    }

    private fun initViewClicks() {
        buttonLogin.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }

    /**
     * Unidirectional flow with single entry point for state
     */
    override fun onStateChanged(loginStateIntent: LoginStateIntent) {
        renderView(loginStateIntent)
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
                mLoginPresenter.processUserAction(userLoginClick)
            }
            R.id.btnReset -> mLoginPresenter.processUserAction(UserRestartClick)
        }
    }
}