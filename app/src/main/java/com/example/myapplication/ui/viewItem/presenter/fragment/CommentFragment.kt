package com.example.myapplication.ui.viewItem.presenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Comments
import com.example.myapplication.data.dto.response.CommentsResponse
import com.example.myapplication.data.utils.Constants
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.databinding.FragmentDescriptionBinding
import com.example.myapplication.ui.adapter.CommentAdapter

class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

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
            val idProduct = bundle.getInt(Constants.ARG_PRODUCT_ID)

        }
        setUpRecyclerView()
        setRecyclerView(getInfo())
    }

    private fun setUpRecyclerView() {
        binding.rvComments.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }
    private fun setRecyclerView(
        value : CommentsResponse
    ) {
        val adapter = CommentAdapter(value)
        binding.rvComments.adapter = adapter
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
                Comments(1, "asjkiojgng", "pepe")
            )
        )
    }
}