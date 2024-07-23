package com.example.myapplication.ui.viewItem.presenter.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityDetailsBinding
import com.example.myapplication.ui.viewItem.presenter.fragment.CommentFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.DescriptionFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.FinancingFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.ImageFragment

class DetailsActivity : AppCompatActivity() {


//    private val fragmentFinancing = FinancingFragment.newInstance()
//
//    private val fragmentImage = ImageFragment.newInstance()
//
//    private val fragmentComment = CommentFragment.newInstance()
//
//    private val fragmentDescription = DescriptionFragment.newInstance()

    private val fragmentFinancing by lazy { FinancingFragment.newInstance() }
    private val fragmentImage by lazy { ImageFragment.newInstance() }
    private val fragmentComment by lazy { CommentFragment.newInstance() }
    private lateinit var fragmentDescription: DescriptionFragment

    private lateinit var binding: ActivityDetailsBinding

    private var idMainProduct: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idMainProduct = intent.getIntExtra("ID_MAIN_PRODUCT", 0)
        idMainProduct?.let {
            fragmentDescription = DescriptionFragment.newInstance(it)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        showFragment(fragmentImage, ImageFragment::class.java.toString())
        actions()
    }

    private fun actions(){
        binding.detailsTvImageFirst.setOnClickListener {
            showFragment(fragmentImage, ImageFragment::class.java.toString())
        }

        binding.detailsTvImageSecond.setOnClickListener {
            showFragment(fragmentImage, ImageFragment::class.java.toString())
        }

        binding.detailsTvDescriptionFirst.setOnClickListener {
            showFragment(fragmentDescription, DescriptionFragment::class.java.toString())

        }

        binding.detailsTvDescriptionSecond.setOnClickListener {
            showFragment(fragmentDescription, DescriptionFragment::class.java.toString())
        }

        binding.detailsTvFinancingFirst.setOnClickListener {
            showFragment(fragmentFinancing, FinancingFragment::class.java.toString())
        }

        binding.detailsTvFinancingSecond.setOnClickListener {
            showFragment(fragmentFinancing, FinancingFragment::class.java.toString())
        }

        binding.detailsTvCommentsFirst.setOnClickListener {
            showFragment(fragmentComment, CommentFragment::class.java.toString())
        }

        binding.detailsTvCommentsSecond.setOnClickListener {
            showFragment(fragmentComment, CommentFragment::class.java.toString())
        }
    }
    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(binding.itemViewFragment.id, fragment, tag)
            .addToBackStack(null)
            .commit()

            effectSelectView(fragment)
    }
    private fun effectSelectView(fragment : Fragment){
       binding.apply {
           detailsCircleComments.visibility = if (fragment is CommentFragment) View.VISIBLE else View.GONE
           detailsCircleDescrption.visibility = if (fragment is DescriptionFragment) View.VISIBLE else View.GONE
           detailsCircleFinancing.visibility = if (fragment is FinancingFragment) View.VISIBLE else View.GONE
           detailsCircleImage.visibility = if (fragment is ImageFragment) View.VISIBLE else View.GONE
       }
    }
}