package com.mashup.mobalmobal.data.dto

import com.google.gson.annotations.SerializedName

data class DonateDto(
    @SerializedName("donate")
    val donate: DonateInfoDto
)