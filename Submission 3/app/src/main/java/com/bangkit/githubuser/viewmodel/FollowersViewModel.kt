package com.bangkit.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser.retrofit.FollowersResponse
import com.bangkit.githubuser.retrofit.LoggingInterceptor
import com.bangkit.githubuser.retrofit.UserResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val TAG = FollowersViewModel::class.java.simpleName
    private val listUserFollowers = MutableLiveData<ArrayList<FollowersResponse>>()

    fun setFollowersUser(query: String) {

        val userItems = LoggingInterceptor.providesHttpAdapter().create(UserResource::class.java)
        userItems.followerUser(query)
                .enqueue(object : Callback<ArrayList<FollowersResponse>> {
                    override fun onResponse(
                            call: Call<ArrayList<FollowersResponse>>,
                            response: Response<ArrayList<FollowersResponse>>
                    ) {
                        val items = response.body()
                        listUserFollowers.postValue(items)
                        Log.d(TAG, "setFollowersUser: onResponse")
                    }

                    override fun onFailure(call: Call<ArrayList<FollowersResponse>>, t: Throwable) {
                        Log.d(TAG, "setFollowersUser: onFailure = $t")
                    }
                })
    }

    fun getFollowersUser(): LiveData<ArrayList<FollowersResponse>> {
        Log.d(TAG, "getFollowersUser")
        return listUserFollowers
    }
}