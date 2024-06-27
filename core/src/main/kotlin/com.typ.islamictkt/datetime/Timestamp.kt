/*
 * This app is developed by AHMED SLEEM
 *
 * Copyright (c) 2021.  TYP INC. All Rights Reserved
 */
package com.typ.islamictkt.datetime

import com.typ.islamictkt.annotations.IntRange
import com.typ.islamictkt.datetime.PatternFormatter.DateTimeFull
import java.util.*

/**
 * Class that holds and does all operations that has time and date on it.
 * It can...
 * 1.Get or Set a field.
 * 2.Get timestamp as Date object.
 * 3.Get timestamp as Calendar instance.
 * 4.Get formatted timestamp according to given pattern.
 * 5.Compare to another timestamp in date or time or both
 */
class Timestamp internal constructor() {

    private val cal: Calendar = Calendar.getInstance()

    constructor(date: Date?) : this() {
        cal.time = date
    }

    constructor(timestampInMillis: Long) : this() {
        cal.timeInMillis = timestampInMillis
    }

    constructor(year: Int, month: Int, day: Int) : this() {
        cal[year, month] = day
    }

    constructor(year: Int, month: Int, day: Int, hour: Int, minutes: Int, seconds: Int) : this(year, month, day) {
        cal[Calendar.HOUR_OF_DAY] = hour
        cal[Calendar.MINUTE] = minutes
        cal[Calendar.SECOND] = seconds
    }

    @get:IntRange(from = 0, to = 60)
    val second: Int
        get() = cal[Calendar.SECOND]

    @get:IntRange(from = 0, to = 59)
    val minute: Int
        get() = cal[Calendar.MINUTE]

    @get:IntRange(from = 0, to = 23)
    val hour: Int
        get() = cal[Calendar.HOUR_OF_DAY]

    @get:IntRange(from = 1, to = 31)
    val day: Int
        get() = cal[Calendar.DATE]

    @get:IntRange(from = 1, to = 12)
    val month: Int
        get() = cal[Calendar.MONTH] + 1
    val year: Int
        get() = cal[Calendar.YEAR]

    fun toMillis(): Long = cal.timeInMillis

    fun asCalendar(): Calendar = cal

    fun asDate(): Date = asCalendar().time

    @get:IntRange(from = 1, to = 7)
    val dayOfWeek: Int
        /**
         * @apiNote First day of the week is SUNDAY by default.
         */
        get() {
            val cal = Calendar.getInstance()
            cal.timeInMillis = toMillis()
            return cal[Calendar.DAY_OF_WEEK]
        }

    fun hasSameDayOf(another: Timestamp?) = if (another == null) false else day == another.day

    fun hasSameMonthOf(another: Timestamp?) = if (another == null) false else month == another.month

    fun hasSameYearOf(another: Timestamp?) = if (another == null) false else year == another.year

    fun matches(another: Timestamp?) = if (another == null) false else dateMatches(another) && timeMatches(another)

    fun dateMatches(another: Timestamp?) =
        if (another == null) false else day == another.day && month == another.month && year == another.year

    fun timeMatches(another: Timestamp?): Boolean {
        return if (another == null) false else minute == another.minute &&
                hour == another.hour
    }

    fun isAfter(another: Timestamp?) = if (another == null) false else isAfter(another.toMillis())

    /**
     * Checks if the timestamp represented by this object is after the specified timestamp.
     *
     * This method compares the current timestamp, represented by the object calling the method,
     * with the provided timestamp in milliseconds since the epoch. It returns `true` if the current
     * timestamp is later (greater) than the specified timestamp, and `false` otherwise.
     *
     * @param timestamp The timestamp to compare with, specified in milliseconds since the epoch.
     * @return `true` if the current timestamp is later (greater) than the specified timestamp,
     *         `false` otherwise.
     */
    fun isAfter(timestamp: Long) = toMillis() > timestamp

    fun isBefore(another: Timestamp?) = if (another == null) false else isBefore(another.toMillis())

    fun isBefore(timestamp: Long) = toMillis() < timestamp

    fun getFormatted(pattern: PatternFormatter) = pattern.format(this)

    fun roll(field: Int, amount: Int) = cal.add(field, amount)

    operator fun set(field: Int, newValue: Int) {
        cal[field] = newValue
    }

    val isLastMonth: Boolean
        get() {
            val lastMonthTimestamp = now().apply { roll(Calendar.MONTH, -1) }
            return hasSameYearOf(lastMonthTimestamp) && hasSameMonthOf(lastMonthTimestamp)
        }
    val isInThisMonth: Boolean
        get() = hasSameYearOf(now()) && hasSameMonthOf(now())
    val isNextMonth: Boolean
        get() {
            val nextMonthTimestamp = now().apply { roll(Calendar.MONTH, 1) }
            return hasSameYearOf(nextMonthTimestamp) && hasSameMonthOf(nextMonthTimestamp)
        }
    val isYesterday: Boolean
        get() = dateMatches(yesterday())
    val isToday: Boolean
        get() = dateMatches(now())
    val isTomorrow: Boolean
        get() = dateMatches(tomorrow())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (other == null || javaClass != other.javaClass) false else matches(other as Timestamp?)
    }

    operator fun get(field: Int): Int {
        return cal[field]
    }

    fun clone(): Timestamp {
        return Timestamp(toMillis())
    }

    override fun hashCode() = Objects.hash(minute, hour, day, month, year)

    override fun toString() = "Timestamp{ " + getFormatted(DateTimeFull()) + " }"

    companion object {
        fun yesterday() = now().apply { roll(Calendar.DATE, -1) }
        fun now() = Timestamp()
        fun tomorrow() = now().apply { roll(Calendar.DATE, 1) }
    }
}
