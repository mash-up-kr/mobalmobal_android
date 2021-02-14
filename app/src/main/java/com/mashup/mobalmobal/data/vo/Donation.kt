package com.mashup.mobalmobal.data.vo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "donations")
data class Donation(
    @PrimaryKey @SerializedName("id") @ColumnInfo(name = "donation_id") val id: String,
    @SerializedName("author_id") @ColumnInfo(name = "donation_author_id") val authorId: String,
    @SerializedName("process_type") @ColumnInfo(name = "process_type") val processType: String,
    @SerializedName("start_date") @ColumnInfo(name = "start_date") val startDate: Double,
    @SerializedName("end_date") @ColumnInfo(name = "end_date") val endDate: Double,
    @SerializedName("donation_price") @ColumnInfo(name = "donation_price") val price: Int,
    @SerializedName("current_price") @ColumnInfo(name = "current_price") val currentPrice: Int,
    @SerializedName("product_url") @ColumnInfo(name = "product_url") val productUrl: String?
) : Serializable