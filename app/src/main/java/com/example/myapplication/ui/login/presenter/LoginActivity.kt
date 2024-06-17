package com.example.myapplication.ui.login.presenter

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.data.dto.model.StateLoginViewModel


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

        binding.loginCheckboxPassword.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setCheckBoxStatus(isChecked)
        }

        binding.loginBtnEnter.setOnClickListener {
            sendLogin()
            observerLogin()
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
        //        falta componente de progressBar en el diseño
        //        binding.loadingScreen.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        //        falta componente de progressBar en el diseño
        //        binding.loadingScreen.visibility = View.GONE
    }

    private fun observerLogin() {
        viewModel.data.observe(this) { data ->
            when (data) {
                is StateLoginViewModel.Success -> {
                    hideLoading()
                    Toast.makeText(this,"Exito: " + data.info.accessToken, Toast.LENGTH_SHORT).show()
                }

                is StateLoginViewModel.Loading -> {
                    showLoading()
                }

                is StateLoginViewModel.Error -> {
                    Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                }
            }
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
}
