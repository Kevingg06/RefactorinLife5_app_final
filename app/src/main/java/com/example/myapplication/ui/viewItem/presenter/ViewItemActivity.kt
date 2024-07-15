package com.example.myapplication.ui.viewItem.presenter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityViewItemBinding

class ViewItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewItemBinding

    private lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.main_graph)

        binding.viewItemTvImage1.setOnClickListener {
            navController.navigate(R.id.commentFragment)
        }

        binding.viewItemTvImage2.setOnClickListener {
            navController.navigate(R.id.commentFragment)
        }

        binding.viewItemTvDescription1.setOnClickListener {
            navController.navigate(R.id.descriptionFragment)
        }

        binding.viewItemTvDescription2.setOnClickListener {
            navController.navigate(R.id.descriptionFragment)
        }
    }



}