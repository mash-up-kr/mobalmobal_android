package com.mashup.mobalmobal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.funin.base.funinbase.BuildConfig
import com.mashup.mobalmobal.data.dao.DonationDao
import com.mashup.mobalmobal.data.dao.UserDao
import com.mashup.mobalmobal.data.vo.Donation
import com.mashup.mobalmobal.data.vo.User

@Database(
    entities = [
        User::class,
        Donation::class
    ],
    version = 1
)
abstract class MobalDatabase : RoomDatabase() {

    companion object {
        private const val IS_CLEAR_ALL = false

        private const val DM_NAME = "mobal.db"

        @Volatile
        private var INSTANCE: MobalDatabase? = null

        fun getInstance(context: Context): MobalDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): MobalDatabase =
            Room.databaseBuilder(context, MobalDatabase::class.java, DM_NAME)
                .fallbackToDestructiveMigration()
                .apply {
                    if (BuildConfig.DEBUG && IS_CLEAR_ALL) {
                        addCallback(CALLBACK_CLEAR_ALL)
                    }
                }
                .build()

        private val CALLBACK_CLEAR_ALL = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                db.delete("users", null, arrayOf())
                db.delete("donations", null, arrayOf())
            }
        }
    }

    abstract fun donationDao(): DonationDao
    abstract fun userDao(): UserDao
}