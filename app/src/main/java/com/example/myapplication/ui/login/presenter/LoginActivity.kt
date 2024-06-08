package com.example.myapplication.ui.login.presenter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actions()
        setButtonState()
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
    }

    private fun setButtonState() {
        viewModel.validateFields.observe(this, Observer { isEnabled ->
            binding.loginBtnEnter.isEnabled = isEnabled
        })
    }
}
