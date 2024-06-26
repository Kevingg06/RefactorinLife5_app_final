package com.example.myapplication.ui.prelogin.presenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.ActivityPreLoginBinding
import com.example.myapplication.ui.login.presenter.LoginActivity
import com.example.myapplication.ui.register.presenter.RegisterActivity

class PreLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actions()
    }

    private fun actions() {
        binding.loginBtnEnter.setOnClickListener {
            navigateTo(LoginActivity::class.java)
        }

        binding.registerBtnEnter.setOnClickListener {
            navigateTo(RegisterActivity::class.java)
        }

        binding.preLoginTvHelp.setOnClickListener {
            sendSupportEmail()
        }
    }

    private fun navigateTo(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    private fun createEmailIntent(): Intent {
        val subject = Constants.SUPPORT_EMAIL_SUBJECT
        val email = Constants.SUPPORT_EMAIL
        val uriText = "mailto:$email?subject=${Uri.encode(subject)}"
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(uriText)
        }
    }

    private fun sendSupportEmail() {
        val emailIntent = createEmailIntent()
        startActivity(Intent.createChooser(emailIntent, "Enviar correo"))
    }
}


