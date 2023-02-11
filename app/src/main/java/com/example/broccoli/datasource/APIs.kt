package com.example.broccoli.datasource

import com.example.broccoli.util.Constants
import retrofit2.Response
import retrofit2.http.POST

interface APIs {
  @POST(Constants.FAKEAUTH)
  suspend fun getPopularVideos(): Response<BaseResponse<TVShowDTO>>
}
