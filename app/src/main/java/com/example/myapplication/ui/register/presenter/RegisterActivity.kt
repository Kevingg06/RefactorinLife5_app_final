package com.example.myapplication.ui.register.presenter

import RegisterViewModel
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.data.dto.model.StateRegister
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.ui.home.presenter.HomeActivity
import com.example.myapplication.ui.login.presenter.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actions()
        setButtonState()
        observeErrorMessage()
        setLoginRedirection()
        observerRegister()
    }

    private fun actions() {
        binding.registerInputEmail.addTextChangedListener {
            viewModel.checkFields(
                binding.registerInputEmail.text.toString(),
                binding.registerInputPassword.text.toString(),
                binding.registerInputConfirmPassword.text.toString()
            )
        }

        binding.registerInputPassword.addTextChangedListener {
            viewModel.checkFields(
                binding.registerInputEmail.text.toString(),
                binding.registerInputPassword.text.toString(),
                binding.registerInputConfirmPassword.text.toString()
            )
        }

        binding.registerInputConfirmPassword.addTextChangedListener {
            viewModel.checkFields(
                binding.registerInputEmail.text.toString(),
                binding.registerInputPassword.text.toString(),
                binding.registerInputConfirmPassword.text.toString()
            )
        }

        binding.registerCbPassword.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCheckBoxPasswordStatus(isChecked)
        }

        binding.registerCbConfirmPassword.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCheckBoxConfirmPasswordStatus(isChecked)
        }

        viewModel.checkBoxPasswordState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.registerInputPassword.transformationMethod = null
            } else {
                binding.registerInputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        viewModel.checkBoxConfirmPasswordState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.registerInputConfirmPassword.transformationMethod = null
            } else {
                binding.registerInputConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        binding.registerBtnEnter.setOnClickListener {
            sendRegister()
        }
    }

    private fun setButtonState() {
        viewModel.validateFields.observe(this) { isEnabled ->
            binding.registerBtnEnter.isEnabled = isEnabled
        }
    }

    private fun sendRegister() {
        val email = binding.registerInputEmail.text.toString()
        val password = binding.registerInputPassword.text.toString()
        viewModel.register(email, password)
    }

    private fun showLoading() {
        binding.registerRlLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.registerRlLoading.visibility = View.GONE
    }

    private fun observerRegister() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateRegister.Success -> {
                    hideLoading()
                    setHomeRedirection()
                }

                is StateRegister.Loading -> {
                    showLoading()
                }

                is StateRegister.Error -> {
                }
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

    private fun setLoginRedirection() {
        binding.registerTvLoginHere.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}

