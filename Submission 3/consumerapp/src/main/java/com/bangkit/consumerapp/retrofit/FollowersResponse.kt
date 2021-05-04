package com.bangkit.consumerapp.retrofit

import com.google.gson.annotations.SerializedName

data class FollowersResponse(
    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatar: String
)