package com.example.myapplication.ui.viewItem.presenter.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.data.utils.Constants.ARG_PRODUCT_ID
import com.example.myapplication.databinding.ActivityDetailsBinding
import com.example.myapplication.ui.viewItem.presenter.fragment.comment.CommentFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.description.presenter.DescriptionFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.financing.presenter.FinancingFragment
import com.example.myapplication.ui.viewItem.presenter.fragment.image.presenter.ImageFragment

class DetailsActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailsViewModel>()

    private var idProduct: Int? = null

    private lateinit var fragmentFinancing: FinancingFragment
    private lateinit var fragmentImage: ImageFragment
    private lateinit var fragmentComment: CommentFragment
    private lateinit var fragmentDescription: DescriptionFragment

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        val bundle = intent.extras

        bundle?.let {
            idProduct = it.getInt(ARG_PRODUCT_ID)
        }

        fragmentImage = ImageFragment.newInstance(idProduct ?: -1)
        fragmentFinancing = FinancingFragment.newInstance(idProduct ?: -1)
        fragmentDescription = DescriptionFragment.newInstance(idProduct ?: -1)
        fragmentComment = CommentFragment.newInstance(idProduct ?: -1)
        showFragment(fragmentImage, ImageFragment::class.java.toString())
        setDailyOffer(idProduct ?: -1)
        actions()
    }

    private fun actions() {
        binding.detailsTvImages.setOnClickListener {
            showFragment(fragmentImage, ImageFragment::class.java.toString())
        }

        binding.detailsTvDescription.setOnClickListener {
            showFragment(fragmentDescription, DescriptionFragment::class.java.toString())
        }

        binding.detailsTvFinancing.setOnClickListener {
            showFragment(fragmentFinancing, FinancingFragment::class.java.toString())
        }

        binding.detailsTvComments.setOnClickListener {
            showFragment(fragmentComment, CommentFragment::class.java.toString())
        }
    }

    private fun setDailyOffer(id: Int) {
        viewModel.setNewDailyOffer(id)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(binding.itemViewFragment.id, fragment, tag)
            .addToBackStack(null)
            .commit()
        effectSelectView(fragment)
    }

    private fun effectSelectView(fragment: Fragment) {
        binding.apply {
            detailsCircleComments.visibility =
                if (fragment is CommentFragment) View.VISIBLE else View.GONE
            detailsCircleDescrption.visibility =
                if (fragment is DescriptionFragment) View.VISIBLE else View.GONE
            detailsCircleFinancing.visibility =
                if (fragment is FinancingFragment) View.VISIBLE else View.GONE
            detailsCircleImage.visibility =
                if (fragment is ImageFragment) View.VISIBLE else View.GONE
        }
    }
}