package com.mashup.mobalmobal.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey @SerializedName("id") @ColumnInfo(name = "user_id") val id: String,
    @SerializedName("nickname") @ColumnInfo(name = "nickname") val nickname: String,
    @SerializedName("phone_number") @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @SerializedName("profile_image_url") @ColumnInfo(name = "profile_image_url") val profileImageUrl: String?,
    val email: String?,
)