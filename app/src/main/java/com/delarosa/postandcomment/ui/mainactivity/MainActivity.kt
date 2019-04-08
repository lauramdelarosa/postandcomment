package com.delarosa.postandcomment.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.delarosa.postandcomment.R
import com.delarosa.postandcomment.model.PostData
import com.delarosa.postandcomment.ui.adapter.PostAdapter
import com.delarosa.postandcomment.ui.detailactivity.MainActivityDetailActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init recyclerview
        val controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_down_to_up)
        recycler_view.layoutAnimation = controller
        recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view.hasFixedSize()
        adapter = PostAdapter(this@MainActivity, listOf()) { postItem: PostData -> itemClicked(postItem) }
        recycler_view.adapter = adapter

        loadPost()

    }

    /**
     * observes when viewmodel pass the list of post and set it to the adapter
     */
    private fun loadPost() {

        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        model.postList.observe(this, Observer {
            adapter.postItemList = it ?: listOf()
            adapter.notifyDataSetChanged()
            recycler_view.scheduleLayoutAnimation()
        })

        model.successfulResponse.observe(this, Observer {
            if (!it) Toast.makeText(this@MainActivity, "problems loading comments", Toast.LENGTH_LONG).show()
        })


    }

    /**
     * when item is clicked pass data to detailActivity for showing it complete
     */
    private fun itemClicked(postItem: PostData) {
        val showDetailActivityIntent = Intent(this, MainActivityDetailActivity::class.java)
        showDetailActivityIntent.putExtra("id", postItem.id)
        showDetailActivityIntent.putExtra("author", postItem.author)
        showDetailActivityIntent.putExtra("title", postItem.title)
        showDetailActivityIntent.putExtra("content", postItem.content)
        showDetailActivityIntent.putExtra("image", postItem.image)
        val bundle = ActivityOptionsCompat.makeCustomAnimation(
            this@MainActivity,
            android.R.anim.fade_in, android.R.anim.fade_out
        ).toBundle()
        startActivity(showDetailActivityIntent, bundle)
    }


}
