package com.example.broccoli.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.broccoli.data.datasource.APIs
import com.example.broccoli.data.response.BaseResponse
import com.example.broccoli.ui.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var apis: APIs) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()

    fun requestInvitation(name: String, email: String) {
        val response = apis.requestInvitation(userInfo = UserInfo(name, email))
        response.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                _errorMessage.postValue(response.body()?.errorMessage)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _errorMessage.postValue(t.message)
            }
        })
    }
}
