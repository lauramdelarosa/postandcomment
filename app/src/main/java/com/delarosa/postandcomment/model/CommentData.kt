package com.delarosa.postandcomment.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentData(var post_id: Int, var author: String, var email: String, var comment: String)
