package com.example.myapplication.ui.viewItem.presenter.fragment.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StateComments
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.dto.response.CommentsResponse
import com.example.myapplication.data.dto.response.ProductByIdResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.ui.adapter.CommentAdapter

class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CommentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val idComments = bundle.getInt(Constants.ARG_PRODUCT_ID)
            callCommentsInfo(idComments)
            callProductById(idComments)
        }
        actions()
        setUpRecyclerView()
        observePriceInfo()
        observeCommentInfo()
    }

    private fun callCommentsInfo(id: Int) {
        viewModel.getComments(id)
    }

    private fun callProductById(id: Int) {
        viewModel.getProductById(id)
    }

    private fun setUpRecyclerView() {
        binding.rvComments.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun setRecyclerView(
        value: CommentsResponse
    ) {
        val adapter = CommentAdapter(value)
        binding.rvComments.adapter = adapter
    }

    private fun render(value: ProductByIdResponse) {
        binding.commentsTvPrice.text = value.price.toString()
        binding.commentsTvCurrency.text = value.currency
    }

    private fun actions() {
        binding.commentImageViewBack.setOnClickListener {
            activity?.finish()
        }
    }

    private fun hideLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.GONE
    }

    private fun showLoading() {
        binding.loadingScreenImages.rlLoading.visibility = View.VISIBLE
    }

    private fun observeCommentInfo() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            when (data) {
                is StateComments.Success -> {
                    hideLoading()
                    setRecyclerView(data.info)
                }

                is StateComments.Error -> {
                    hideLoading()
                }

                is StateComments.Loading -> {
                    showLoading()
                }

                else -> {

                }
            }

        }
    }

    private fun observePriceInfo() {
        viewModel.data1.observe(viewLifecycleOwner) { data1 ->
            when (data1) {
                is StateProductById.Success -> {
                    hideLoading()
                    render(data1.info)
                }

                is StateProductById.Error -> {
                    hideLoading()
                }

                is StateProductById.Loading -> {
                    showLoading()
                }

                else -> {

                }
            }

        }
    }

    companion object {
        fun newInstance(productId: Int): CommentFragment {
            val fragment = CommentFragment()
            val bundle = Bundle().apply {
                putInt(Constants.ARG_PRODUCT_ID, productId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}