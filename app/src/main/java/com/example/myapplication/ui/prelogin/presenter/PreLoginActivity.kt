package com.example.myapplication.ui.prelogin.presenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityPreLoginBinding
import com.example.myapplication.ui.login.presenter.LoginActivity
import com.example.myapplication.ui.register.presenter.RegisterActivity

class PreLoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPreLoginBinding

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

//        binding.registerButtonEnter.setOnClickListener(){
//            val myIntent = Intent(this, RegisterActivity::class.java)
//            startActivity(myIntent)
//        }

        fun communicationSupport(view: View) {
            val supportEmail = "karen.24.tc@gmail.com"
            val subject = "Necesito ayuda con la aplicación"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay ninguna aplicación de correo instalada.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
