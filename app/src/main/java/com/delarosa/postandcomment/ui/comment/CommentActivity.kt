package com.delarosa.postandcomment.ui.comment

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.delarosa.postandcomment.R
import com.delarosa.postandcomment.ui.adapter.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment.*


class CommentActivity : AppCompatActivity() {

    private var postId: Int = 0
    private lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        //get postId with extras
        postId = intent.getIntExtra("id", 0)
        //init recyclerview.
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_down_to_up)
        recycler_view_comment.layoutAnimation = controller
        recycler_view_comment.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view_comment.hasFixedSize()
        adapter = CommentAdapter(listOf())
        recycler_view_comment.adapter = adapter

        loadComments()

    }
    /**
     * observes when viewmodel pass the list of comments and set it to the adapter
     */
    private fun loadComments() {

        val model = ViewModelProviders.of(this).get(CommentViewModel::class.java)

        model.loadPost(postId)

        model.commentList.observe(this, Observer {
            adapter.postItemList = it ?: listOf()
            adapter.notifyDataSetChanged()
            recycler_view_comment.scheduleLayoutAnimation()
        })

        model.successfulResponse.observe(this, Observer {
            if (!it) Toast.makeText(this@CommentActivity, "problems loading comments", Toast.LENGTH_LONG).show()
        })
    }


}
