package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class ChargeDto(
    @SerializedName("charge") val charge: ChargeInfoDto
)
