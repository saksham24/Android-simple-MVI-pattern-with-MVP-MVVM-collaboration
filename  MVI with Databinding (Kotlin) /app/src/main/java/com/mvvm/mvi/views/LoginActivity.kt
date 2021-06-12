package com.mvvm.mvi.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mvvm.mvi.R
import com.mvvm.mvi.databinding.ActivityLoginBinding
import com.mvvm.mvi.intents.login.LoginStateIntent
import com.mvvm.mvi.intents.login.LoginStateIntent.*
import com.mvvm.mvi.intents.userActions.UserAction.UserLoginClick
import com.mvvm.mvi.intents.userActions.UserAction.UserRestartClick
import com.mvvm.mvi.viewModels.LoginViewModel
import java.lang.String

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var loginViewModel: LoginViewModel
    lateinit var loginBindingData: LoginBindingData // this is observed by views
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        activateBinding()
        initObservers()
    }

    private fun activateBinding() {
        loginBindingData = LoginBindingData(getString(R.string.text_start_info), true, false, View.GONE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        binding.setModel(loginBindingData) // setting view data to layout binding
        binding.setOnClickListener(this) // give instance of onclickListener to view
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
            is LoginNotInitiated -> loginBindingData.setData(getString(R.string.text_start_info), true, true, View.GONE)
            is LoginProgressing -> loginBindingData.setData(getString(R.string.text_processing_login), false, false, View.VISIBLE)
            is LoginSuccess -> {
                val infoText = String.format("%s%s", getString(R.string.label_token_received), loginStateIntent.loginResponseModel.token)
                loginBindingData.setData(infoText, false, true, View.GONE)
            }
            is LoginError -> loginBindingData.setData(loginStateIntent.error, true, true, View.GONE)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_login -> {
                val userLoginClick = UserLoginClick(binding.edittextUsername.text.toString(),
                        binding.edittextPassword.text.toString())
                loginViewModel.processUserAction(userLoginClick)
            }
            R.id.btn_reset -> loginViewModel.processUserAction(UserRestartClick)
        }
    }
}