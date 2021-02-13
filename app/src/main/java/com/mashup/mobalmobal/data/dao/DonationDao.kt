package com.mashup.mobalmobal.data.dao

import androidx.room.Dao
import com.mashup.mobalmobal.data.vo.Donation

@Dao
abstract class DonationDao : BaseDao<Donation>()