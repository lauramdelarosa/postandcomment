package com.delarosa.postandcomment.ui.detailactivity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delarosa.postandcomment.datasource.WebAccess
import com.delarosa.postandcomment.model.CommentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class DetailMainViewModel : ViewModel() {

    //outputs
    private val _successfulResponse = MutableLiveData<Boolean>()
    val successfulResponse: LiveData<Boolean> get() = _successfulResponse

    //events
    /**
     * send the comment using coroutines
     */
    fun sendComment(originalItemId: Int, commentData: CommentData) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                WebAccess.API.postCommentsAsync(originalItemId, commentData).await()
                _successfulResponse.value = true
            } catch (e: IOException) {
                _successfulResponse.value = true
            }
        }

    }

}
