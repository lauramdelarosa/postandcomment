package com.delarosa.postandcomment.ui.mainactivity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delarosa.postandcomment.datasource.WebAccess
import com.delarosa.postandcomment.model.PostData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    //outputs
    private val _postList = MutableLiveData<List<PostData>>()
    val postList: LiveData<List<PostData>> get() = _postList

    //outputs
    private val _successfulResponse = MutableLiveData<Boolean>()
    val successfulResponse: LiveData<Boolean> get() = _successfulResponse

    init {
        loadPost()
    }

    //events
    /**
     * get list of posts using coroutines
     */
    private fun loadPost() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = WebAccess.API.getPostAsync().await()
                if (webResponse.isSuccessful) {
                    val postList: List<PostData>? = webResponse.body()
                    _postList.value = postList
                    _successfulResponse.value = true
                } else {
                    _successfulResponse.value = false
                }
            } catch (e: IOException) {
            }
        }
    }

}
