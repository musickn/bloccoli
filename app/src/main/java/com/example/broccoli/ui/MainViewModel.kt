package com.example.broccoli.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.broccoli.data.datasource.APIs
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
        response.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    println("success")
                    // Handle successful response
                } else {
                    println("failed")
                    // Handle unsuccessful response with error code
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("ViewModel", "Error making API call: ${t.message}")
                _errorMessage.postValue(t.message)
            }
        })
    }
}
