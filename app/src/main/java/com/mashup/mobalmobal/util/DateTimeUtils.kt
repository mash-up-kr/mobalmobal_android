package com.mashup.mobalmobal.util

import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private const val MOBAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun calculateBetweenDates(source: String?, destination: String?): Long? {
        if (source == null || destination == null) return null
        val mobalmobalDateFormat =
            SimpleDateFormat(MOBAL_DATE_FORMAT, Locale.getDefault())
        val sourceDate = mobalmobalDateFormat.parse(source)
        val destinationDate = mobalmobalDateFormat.parse(destination)
        return calculateBetweenDates(sourceDate, destinationDate)
    }

    fun calculateBetweenDates(source: Date?, destination: Date?): Long? {
        if (source == null || destination == null) return null
        val zoneId = DateTimeUtils.toZoneId(TimeZone.getDefault())
        val from = LocalDateTime.ofInstant(Instant.ofEpochMilli(source.time), zoneId)
        val to = LocalDateTime.ofInstant(Instant.ofEpochMilli(destination.time), zoneId)
        return ChronoUnit.DAYS.between(from, to)
    }

    fun calculateDecimalDayText(source: String?, destination: String?): String? {
        val betweenDates = calculateBetweenDates(source, destination) ?: return null
        return betweenDates.toDecimalDayText()
    }

    fun calculateDecimalDayText(source: Date?, destination: Date?): String? {
        val betweenDates = calculateBetweenDates(source, destination) ?: return null
        return betweenDates.toDecimalDayText()
    }

    private fun Long.toDecimalDayText(): String = if (this >= 0) {
        "D-${kotlin.math.abs(this)}"
    } else {
        "D+${kotlin.math.abs(this)}"
    }
}