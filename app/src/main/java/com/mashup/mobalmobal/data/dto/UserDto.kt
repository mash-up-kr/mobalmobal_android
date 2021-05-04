package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class UserWrappingDto(@SerializedName("user") val user: UserDto)

data class UserDto(
    @SerializedName("user_id") val id: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("phone_number") val phoneNumber: String? = null,
    @SerializedName("account_number") val accountNumber: String? = null,
    @SerializedName("bank_name") val bankName: String? = null,
    @SerializedName("profile_image") val profileImage: String? = null,
    @SerializedName("cash") val cash: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("deleted_at") val deletedAt: String? = null
)