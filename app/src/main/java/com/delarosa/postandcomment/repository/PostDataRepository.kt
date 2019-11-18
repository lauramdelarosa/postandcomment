package com.delarosa.postandcomment.repository

import com.delarosa.postandcomment.datasource.ApiClient
import com.delarosa.postandcomment.datasource.errors.Result
import com.delarosa.postandcomment.datasource.errors.ResponseHandler
import com.delarosa.postandcomment.datasource.errors.TYPE_STATUS
import com.delarosa.postandcomment.model.PostData

class PostDataRepository(private val api: ApiClient) {
    private val responseHandler = ResponseHandler()

    suspend fun getPostList(): Result<List<PostData>> {
        return try {
            val response = api.getPostAsync()
            return responseHandler.handleSuccess(response.body()!!)
        } catch (e: Exception) {
            responseHandler.handleException(e, TYPE_STATUS.TYPE1)
        }
    }


}