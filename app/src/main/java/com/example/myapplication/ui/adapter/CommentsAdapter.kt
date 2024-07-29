package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.dto.response.Comments
import com.example.myapplication.data.dto.response.CommentsResponse
import com.example.myapplication.databinding.ItemRvCommentsBinding

class CommentAdapter(private val comments: CommentsResponse) :
    RecyclerView.Adapter<CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_comments, parent, false)
        return CommentHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.comments?.size ?: 0
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        comments.comments?.let { holder.render(comments.comments[position]) }
    }
}

class CommentHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRvCommentsBinding.bind(view)
    fun render(value: Comments) {
        binding.commentName.text = value.commentBy
        binding.comment.text = value.comment
    }
}

