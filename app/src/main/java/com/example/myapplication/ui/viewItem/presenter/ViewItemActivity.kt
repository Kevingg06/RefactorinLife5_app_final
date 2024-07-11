package com.example.myapplication.ui.viewItem.presenter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityViewItemBinding

class ViewItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}