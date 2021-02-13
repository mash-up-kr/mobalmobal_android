package com.mashup.mobalmobal.data.dao

import androidx.room.Dao
import com.mashup.mobalmobal.data.vo.User

@Dao
abstract class UserDao : BaseDao<User>()