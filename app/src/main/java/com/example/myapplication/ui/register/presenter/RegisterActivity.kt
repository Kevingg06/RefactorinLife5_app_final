package com.example.myapplication.ui.register.presenter

import RegisterViewModel
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityRegisterBinding

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

        // Observador para el estado del CheckBox de Password
        viewModel.checkBoxPasswordState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.registerInputPassword.transformationMethod = null
            } else {
                binding.registerInputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        // Observador para el estado del CheckBox de Confirm Password
        viewModel.checkBoxConfirmPasswordState.observe(this) { checkBoxStatus ->
            if (checkBoxStatus) {
                binding.registerInputConfirmPassword.transformationMethod = null
            } else {
                binding.registerInputConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun setButtonState() {
        viewModel.validateFields.observe(this) { isEnabled ->
            binding.registerBtnEnter.isEnabled = isEnabled
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
}

