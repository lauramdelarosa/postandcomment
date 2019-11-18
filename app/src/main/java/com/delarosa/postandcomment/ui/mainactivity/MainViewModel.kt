package com.delarosa.postandcomment.ui.mainactivity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delarosa.postandcomment.datasource.errors.InvalidToken
import com.delarosa.postandcomment.datasource.errors.Status
import com.delarosa.postandcomment.datasource.WebAccess
import com.delarosa.postandcomment.model.PostData
import com.delarosa.postandcomment.repository.PostDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val postDataRepository: PostDataRepository = PostDataRepository(WebAccess.API)
    private val foreground = Dispatchers.Main
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
        GlobalScope.launch(foreground) {
            val bankCards = withContext(foreground) {
                postDataRepository.getPostList()
            }
            when (bankCards.status) {
                Status.SUCCESS -> {
                    _postList.value = bankCards.data
                    _successfulResponse.value = true
                }
                Status.ERROR -> {
                    when (bankCards.message) {
                        is InvalidToken -> {
                            _successfulResponse.value = false
                            _postList.value = null
                        }
                        else -> {
                            _successfulResponse.value = false
                            _postList.value = null
                        }
                    }
                }
            }
        }
    }

}

