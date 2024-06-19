package com.example.myapplication.ui.login.presenter

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.register.presenter.RegisterActivity


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

        binding.loginCbPassword.setOnCheckedChangeListener {_, isChecked ->
            viewModel.setCheckBoxStatus(isChecked)
        }
    }

    private fun setButtonState() {
        viewModel.validateFields.observe(this) { isEnabled ->
            binding.loginBtnEnter.isEnabled = isEnabled
        }
    }

    private fun setCheckBoxStatus() {
        viewModel.checkBoxState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.loginInputPassword.transformationMethod = null
            } else {
                binding.loginInputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun setRegisterRedirection() {
        binding.loginTvRegisterHere.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}
