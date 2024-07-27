package com.example.myapplication.ui.viewItem.presenter.fragment.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dto.model.StateComments
import com.example.myapplication.data.dto.model.StateProductById
import com.example.myapplication.data.repository.CommentsRepository
import com.example.myapplication.data.repository.ProductRepository
import com.example.myapplication.data.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(
    private val repositoryComments: CommentsRepository = CommentsRepository(),
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {
    private val _data = MutableLiveData<StateComments>()
    val data: LiveData<StateComments> = _data

    private val _data1 = MutableLiveData<StateProductById>()
    val data1: LiveData<StateProductById> = _data1

    fun getComments(id: Int) {
        viewModelScope.launch {
            _data.postValue(StateComments.Loading)
            val response = repositoryComments.getComments(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data.postValue(StateComments.Success(it))
                } ?: _data.postValue(StateComments.Error(Constants.COMMENTS_FAILED))
            } else {
                _data.postValue(StateComments.Error(Constants.NETWORK_ERROR))
            }
        }
    }

    fun getProductById(idProduct: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _data1.postValue(StateProductById.Loading)
            val response = repository.getProductById(idProduct)
            if (response.isSuccessful) {
                response.body()?.let {
                    _data1.postValue(StateProductById.Success(it))
                } ?: {
                    _data1.postValue(StateProductById.Error(Constants.LOGIN_FAILED))
                }
            } else {
                _data1.postValue(StateProductById.Error(Constants.NETWORK_ERROR))
            }
        }
    }
}