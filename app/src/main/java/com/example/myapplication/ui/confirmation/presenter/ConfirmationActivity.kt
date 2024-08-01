package com.example.myapplication.ui.confirmation.presenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.ActivityConfirmationBinding
import com.example.myapplication.ui.home.presenter.HomeActivity

class ConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actions()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun actions() {
        binding.buyProductButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        binding.supportMessageHome.setOnClickListener {
            sendSupportEmail()
        }
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