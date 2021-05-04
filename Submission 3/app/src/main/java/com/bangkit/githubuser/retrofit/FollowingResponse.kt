package com.bangkit.githubuser.retrofit

import com.google.gson.annotations.SerializedName

data class FollowingResponse(
    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatar: String
)