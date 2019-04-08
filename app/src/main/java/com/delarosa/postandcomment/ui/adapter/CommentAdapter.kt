package com.delarosa.postandcomment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delarosa.postandcomment.R
import com.delarosa.postandcomment.model.CommentData
import kotlinx.android.synthetic.main.comment_item_list.view.*

/**
 * comment recyclerView implementation
 */
class CommentAdapter(var postItemList: List<CommentData>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.comment_item_list, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(postItemList[position])
    }

    override fun getItemCount() = postItemList.size

    class PartViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(commentData: CommentData) {
            itemView.name.text = commentData.author
            itemView.email.text = commentData.email
            itemView.comment.text = commentData.comment
        }
    }
}