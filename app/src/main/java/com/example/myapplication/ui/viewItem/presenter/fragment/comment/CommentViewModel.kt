package com.example.myapplication.ui.viewItem.presenter.fragment.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateComments
import com.example.myapplication.data.repository.CommentsRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.launch

class CommentViewModel(private val repository : CommentsRepository = CommentsRepository()) : ViewModel() {
    private val _data = MutableLiveData<StateComments>()
    val data : LiveData<StateComments> = _data

    fun getComments(id : Int) {
        viewModelScope.launch {
            _data.postValue(StateComments.Loading)
            val response = repository.getComments(id)
            if (response.isSuccessful){
                response.body()?.let {
                    _data.postValue(StateComments.Success(it))
                } ?: _data.postValue(StateComments.Error(Constants.COMMENTS_FAILED))
            } else {
                _data.postValue(StateComments.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}