package com.mashup.mobalmobal.data.dao

import androidx.room.*


@Dao
abstract class BaseDao<T : Any> {

    companion object {
        private const val FAILED_CODE_INSERTION = -1L
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg objects: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(objects: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnore(vararg objects: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnore(interests: List<T>): List<Long>

    @Update
    abstract fun update(vararg objects: T)

    @Update
    abstract fun update(objects: List<T>)

    @Delete
    abstract fun delete(vararg objects: T)

    @Delete
    abstract fun delete(objects: List<T>)

    @Transaction
    open fun insertOrUpdate(vararg objects: T) {
        insertOrIgnore(*objects)
            .zip(objects)
            .mapNotNull { (result: Long, obj: T) -> obj.takeIf { result == FAILED_CODE_INSERTION } }
            .also { update(it) }
    }

    @Transaction
    open fun insertOrUpdate(objects: List<T>) {
        insertOrIgnore(objects)
            .zip(objects)
            .mapNotNull { (result: Long, obj: T) -> obj.takeIf { result == FAILED_CODE_INSERTION } }
            .also { update(it) }
    }
}