package com.delarosa.postandcomment.datasource


import com.delarosa.postandcomment.model.CommentData
import com.delarosa.postandcomment.model.PostData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {
    @GET("posts")
    fun getPostAsync(): Deferred<Response<List<PostData>>>

    @GET("posts/{postId}/comments")
    fun getCommentsAsync(@Path("postId") id: Int): Deferred<Response<List<CommentData>>>

    @POST("posts/{postId}/comments")
    fun postCommentsAsync(@Path("postId") id: Int, @Body commentData: CommentData): Deferred<Response<Void>>
}