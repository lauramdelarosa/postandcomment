package com.delarosa.postandcomment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.delarosa.postandcomment.R
import com.delarosa.postandcomment.model.PostData
import kotlinx.android.synthetic.main.post_list_item.view.*

/**
 * posts recyclerView implementation
 */
class PostAdapter(
    private val context: Context,
    var postItemList: List<PostData>,
    private val clickListener: (PostData) -> Unit
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.post_list_item, parent, false)
        return PartViewHolder(view)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        (holder as PartViewHolder).bind(postItemList[position], clickListener, context)
    }

    override fun getItemCount() = postItemList.size

    class PartViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(post: PostData, clickListener: (PostData) -> Unit, context: Context) {
            itemView.title.text = post.title
            itemView.content.text = post.content
            Glide.with(context).load(post.thumbnail).into(itemView.thumbnail)
            itemView.setOnClickListener { clickListener(post) }
        }
    }
}