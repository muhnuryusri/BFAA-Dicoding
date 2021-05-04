package com.bangkit.consumerapp.database

import android.database.Cursor
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.AVATAR
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USERNAME
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USER_ID
import com.bangkit.consumerapp.entity.Favorite

object DatabaseHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val userId = getInt(getColumnIndexOrThrow(USER_ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                favoriteList.add(Favorite(userId, username, avatar))
            }
        }
        return favoriteList
    }
}