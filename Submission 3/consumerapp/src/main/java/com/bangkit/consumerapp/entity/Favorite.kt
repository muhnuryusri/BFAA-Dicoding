package com.bangkit.consumerapp.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.AVATAR
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.TABLE_NAME
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USERNAME
import com.bangkit.consumerapp.database.DatabaseContract.FavColumns.Companion.USER_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = USER_ID) val userId: Int,
    @ColumnInfo(name = USERNAME) val username: String?,
    @ColumnInfo(name = AVATAR) val avatar: String?
): Parcelable