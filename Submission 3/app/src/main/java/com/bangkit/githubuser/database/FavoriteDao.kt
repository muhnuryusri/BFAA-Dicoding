package com.bangkit.githubuser.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.githubuser.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(fav: Favorite): Long

    @Query("DELETE FROM favorite WHERE userId = :userId")
    fun deleteUser(userId: Int): Int

    @Query("SELECT * FROM favorite ORDER BY username ASC")
    fun getUsers(): Cursor

    @Query("SELECT * FROM favorite WHERE userId = :userId")
    fun getUserById(userId: Int): Cursor

    @Query("DELETE FROM favorite")
    suspend fun deleteAll()
}