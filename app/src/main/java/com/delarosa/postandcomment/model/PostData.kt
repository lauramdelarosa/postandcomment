package com.delarosa.postandcomment.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostData(
    var id: Int,
    var author: String,
    var title: String,
    var thumbnail: String,
    var image: String,
    var content: String
)
