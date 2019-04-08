package com.delarosa.postandcomment.ui.comment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delarosa.postandcomment.datasource.WebAccess
import com.delarosa.postandcomment.model.CommentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class CommentViewModel : ViewModel() {

    //outputs
    private val _commentList = MutableLiveData<List<CommentData>>()
    val commentList: LiveData<List<CommentData>> get() = _commentList

    //outputs
    private val _successfulResponse = MutableLiveData<Boolean>()
    val successfulResponse: LiveData<Boolean> get() = _successfulResponse

    //events
    /**
     * get list of comments using coroutines
     */
    fun loadPost(postId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = WebAccess.API.getCommentsAsync(postId).await()
                if (webResponse.isSuccessful) {
                    val postList: List<CommentData>? = webResponse.body()
                    _commentList.value = postList
                    _successfulResponse.value = true
                } else {
                    _successfulResponse.value = false
                }
            } catch (e: IOException) {
            }
        }
    }

}
