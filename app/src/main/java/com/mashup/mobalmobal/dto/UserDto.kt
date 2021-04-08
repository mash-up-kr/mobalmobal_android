package com.mashup.mobalmobal.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("nick_name")
    val nickName: String,
    @SerializedName("profile_image")
    val profileUrl: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("account_number")
    val account: String?,
    @SerializedName("bank_name")
    val bankName: String?,
    @SerializedName("cash")
    val cash: Int?,
    @SerializedName("provider")
    val provider: String?,
    @SerializedName("firestore_id")
    val fireStoreId: String?,
    @SerializedName("create_at")
    val createAt: String?,
    @SerializedName("update_at")
    val updateAt: String?,
    @SerializedName("deleted_at")
    val deleteAt: String?
)