package com.example.myapplication.ui.prelogin.presenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityPreLoginBinding
import com.example.myapplication.ui.login.presenter.LoginActivity

class PreLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
    }
}
