package com.example.myapplication.ui.prelogin.presenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
        binding.registerBtnEnter.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

}
