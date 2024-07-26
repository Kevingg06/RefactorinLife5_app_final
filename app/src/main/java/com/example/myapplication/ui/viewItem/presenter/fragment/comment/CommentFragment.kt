package com.example.myapplication.ui.viewItem.presenter.fragment.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.dto.model.StateComments
import com.example.myapplication.data.dto.response.Comments
import com.example.myapplication.data.dto.response.CommentsResponse
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
        }
        actions()
        setUpRecyclerView()
        observeCommentInfo()
    }

    private fun callCommentsInfo(id : Int) {
        viewModel.getComments(id)
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

    private fun actions() {
        binding.commentImageViewBack.setOnClickListener {
            activity?.finish()
        }
    }

    fun hideLoading() {

    }

    fun showLoading() {

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

    private fun getInfo(): CommentsResponse {

        return CommentsResponse(
            listOf(
                Comments(1, "asjkiojgng", "pepe"),
                Comments(2, "asdasd", "elena"),
                Comments(2, "asdasd", "elena"),
                Comments(2, "asdasd", "elena")
            )
        )
    }
}