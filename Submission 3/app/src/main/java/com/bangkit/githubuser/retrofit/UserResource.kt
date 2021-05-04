package com.bangkit.githubuser.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserResource {

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String?
    ): Call<MainResponse>

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
