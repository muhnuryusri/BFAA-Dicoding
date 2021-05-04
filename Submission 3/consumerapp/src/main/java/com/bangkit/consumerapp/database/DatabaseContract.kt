package com.bangkit.consumerapp.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.bangkit.githubuser"
    const val SCHEME = "content"

    class FavColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val USER_ID = "userId"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().apply {
                scheme(SCHEME)
                authority(AUTHORITY)
                appendPath(TABLE_NAME)
            }.build()
        }
    }
}