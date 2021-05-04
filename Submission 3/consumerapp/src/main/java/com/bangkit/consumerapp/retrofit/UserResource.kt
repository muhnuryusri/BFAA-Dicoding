package com.bangkit.consumerapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserResource {

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String?
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun followerUser(
        @Path("username") username: String?
    ): Call<ArrayList<FollowersResponse>>

    @GET("users/{username}/following")
    fun followingUser(
        @Path("username") username: String?
    ): Call<ArrayList<FollowingResponse>>
}
