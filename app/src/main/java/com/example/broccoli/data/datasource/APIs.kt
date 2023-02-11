package com.example.broccoli.data.datasource

import com.example.broccoli.data.response.BaseResponse
import com.example.broccoli.ui.model.UserInfo
import com.example.broccoli.util.Constants
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIs {
    @POST(Constants.FAKEAUTH)
    fun requestInvitation(@Body userInfo: UserInfo): Call<BaseResponse>
}
