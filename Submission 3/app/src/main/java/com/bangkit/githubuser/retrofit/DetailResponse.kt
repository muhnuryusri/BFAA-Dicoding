package com.bangkit.githubuser.retrofit

import com.google.gson.annotations.SerializedName

data class DetailResponse(

    @SerializedName("id")
    val userId: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("login")
    val username: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("avatar_url")
    val avatar: String
)