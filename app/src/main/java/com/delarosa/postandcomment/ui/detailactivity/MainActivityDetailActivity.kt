package com.delarosa.postandcomment.ui.detailactivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.delarosa.postandcomment.R
import com.delarosa.postandcomment.model.CommentData
import com.delarosa.postandcomment.ui.comment.CommentActivity
import kotlinx.android.synthetic.main.activity_post_detail.*
import java.util.regex.Pattern


class MainActivityDetailActivity : AppCompatActivity() {

    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(".+@.+\\.[a-z]+")
    private var originalItemId: Int = 0
    private lateinit var model: DetailMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        getExtras()

        //init viewModel
        model = ViewModelProviders.of(this).get(DetailMainViewModel::class.java)

        //validate fields and send data to viewmodel
        comment_button.setOnClickListener {
            if (comment_text.text.toString().isNotEmpty()
                && email_text.text.toString().isNotEmpty()
                && EMAIL_ADDRESS_PATTERN.matcher(email_text.text.toString()).matches()
                && name_text.text.toString().isNotEmpty()
            ) {

                model.sendComment(
                    originalItemId,
                    CommentData(
                        originalItemId,
                        name_text.text.toString(),
                        email_text.text.toString(),
                        comment_text.text.toString()
                    )
                )
                model.successfulResponse.observe(this, Observer {
                    if (it) cleanData()
                    else showMessage("problems posting your comment")
                })
            } else showMessage("fill all fields or enter a valid email")

        }

        see_comments.setOnClickListener {
            val showDetailActivityIntent = Intent(this, CommentActivity::class.java)
            showDetailActivityIntent.putExtra("id", originalItemId)
            startActivity(showDetailActivityIntent)
        }
    }

    /**
     * get extras to show the complete data
     */
    private fun getExtras() {
        try {
            originalItemId = intent.getIntExtra("id", 0)
            title_text.text = intent.getStringExtra("title")
            author.text = intent.getStringExtra("author")
            content.text = intent.getStringExtra("content")
            val url = intent.getStringExtra("image")

            Glide.with(this@MainActivityDetailActivity).load(url).into(image)


        } catch (e: Exception) {
            showMessage("error getting data")
        }

    }

    private fun showMessage(text: String) =
        Toast.makeText(this@MainActivityDetailActivity, text, Toast.LENGTH_LONG).show()

    /**
     * clean textview so user can send more comments
     */
    private fun cleanData() {
        comment_text.text = Editable.Factory.getInstance().newEditable("")
        email_text.text = Editable.Factory.getInstance().newEditable("")
        name_text.text = Editable.Factory.getInstance().newEditable("")
    }
}
