package com.bangkit.githubuser.retrofit

import com.google.gson.annotations.SerializedName

data class MainResponse(
    @SerializedName("items")
    val items: ArrayList<Items>
)

data class Items(
    @SerializedName("login")
    val username: String,

    @SerializedName("avatar_url")
    val avatar: String
)