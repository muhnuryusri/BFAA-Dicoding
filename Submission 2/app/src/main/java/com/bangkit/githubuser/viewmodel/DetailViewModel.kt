package com.bangkit.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.githubuser.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName
    private val listDetailUser = MutableLiveData<User>()

    fun setDetailUser(username: String) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/${username}"
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    val responseObject = JSONObject(result)

                    val userItems = User()
                    userItems.name = responseObject.getString("name")
                    userItems.username = responseObject.getString("login")
                    userItems.location = responseObject.getString("location")
                    userItems.following = responseObject.getString("following")
                    userItems.followers = responseObject.getString("followers")
                    userItems.repository = responseObject.getString("public_repos")
                    userItems.avatar = responseObject.getString("avatar_url")

                    listDetailUser.postValue(userItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getDetailUser() : LiveData<User> {
        return listDetailUser
    }
}