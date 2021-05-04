package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class ChargeInfoDto(
    @SerializedName("is_charge") val isCharge: Int,
    @SerializedName("charge_id") val chargeId: Int,
    @SerializedName("amount") val amount: Int,
    @SerializedName("user_name") val userName: String,
    @SerializedName("charged_at") val chargedAt: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("createdAt") val createdAt: String
)
