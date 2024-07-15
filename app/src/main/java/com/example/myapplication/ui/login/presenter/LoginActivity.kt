package com.example.myapplication.ui.login.presenter

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.data.dto.dataSource.saveToken
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.register.presenter.RegisterActivity
import com.example.myapplication.data.dto.model.StateLogin
import com.example.myapplication.ui.home.presenter.HomeActivity
import com.example.myapplication.ui.prelogin.presenter.PreLoginActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actions()
        setButtonState()
        setCheckBoxStatus()
        setRegisterRedirection()
        observeErrorMessage()
        observerLogin()
    }

    private fun actions() {
        binding.loginInputEmail.addTextChangedListener {
            viewModel.checkFields(
                binding.loginInputEmail.text.toString(),
                binding.loginInputPassword.text.toString()
            )
        }

        binding.loginInputPassword.addTextChangedListener {
            viewModel.checkFields(
                binding.loginInputEmail.text.toString(),
                binding.loginInputPassword.text.toString()
            )
        }

        binding.loginCbPassword.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCheckBoxStatus(isChecked)
        }

        binding.loginBtnEnter.setOnClickListener {
            sendLogin()
        }
    }

    private fun setButtonState() {
        viewModel.validateFields.observe(this) { isEnabled ->
            binding.loginBtnEnter.isEnabled = isEnabled
        }
    }

    private fun sendLogin() {
        val email = binding.loginInputEmail.text.toString()
        val password = binding.loginInputPassword.text.toString()
        viewModel.login(email, password)
    }

    private fun showLoading() {
        binding.loginRlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loginRlLoading.visibility = View.GONE
    }

    private fun observerLogin() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateLogin.Success -> {
                    hideLoading()
                    setHomeRedirection()
                    saveToken(this, data.info.accessToken)
                }

                is StateLogin.Loading -> {
                    showLoading()
                }

                is StateLogin.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun setCheckBoxStatus() {
        viewModel.checkBoxState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.loginInputPassword.transformationMethod = null
            } else {
                binding.loginInputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun observeErrorMessage() {
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrBlank()) {
                binding.registerTvErrorMessage.text = errorMessage
                binding.registerTvErrorMessage.visibility = TextView.VISIBLE
            } else {
                binding.registerTvErrorMessage.visibility = TextView.GONE
            }
        }
    }

    private fun setHomeRedirection() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }

    private fun setRegisterRedirection() {
        binding.loginTvRegisterHere.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}
