package com.example.broccoli.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("errorMessage") val errorMessage: String
)
