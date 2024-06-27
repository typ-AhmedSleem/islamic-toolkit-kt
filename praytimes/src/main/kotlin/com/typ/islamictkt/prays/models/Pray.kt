package com.typ.islamictkt.prays.models

import com.typ.islamictkt.datetime.PatternFormatter
import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.prays.enums.PrayStatus
import com.typ.islamictkt.prays.enums.PrayType
import com.typ.islamictkt.locale.LocaleManager
import java.util.*

/**
 * Model class of Pray which holds all Pray Item data.
 *
 * NOTE: This class can be extended to add more fields as you need.
 */
open class Pray(
    /**
     * Used only to indicate what this pray is by enum ordinal
     */
    @JvmField val type: PrayType,
    /**
     * Timestamp of this pray time
     */
    @JvmField val time: Timestamp
) {

    constructor(type: PrayType, timeInMillis: Long) : this(type, Timestamp(timeInMillis))

    val status: PrayStatus
        get() {
            return if (time.isBefore(System.currentTimeMillis())) PrayStatus.PASSED
            else PrayStatus.UPCOMING
        }

    val passed: Boolean
        get() = (status == PrayStatus.PASSED)

    val upcoming: Boolean
        get() = (status == PrayStatus.UPCOMING)

    val formattedTime: String
        get() = PatternFormatter.PrayTimes().format(time)

    fun getFormattedTime(formatter: PatternFormatter, locale: Locale): String {
        return formatter.format(time, locale)
    }

    fun getFormattedTime(formatter: PatternFormatter): String {
        return formatter.format(time, LocaleManager.getDefault())
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        return if (other !is Pray) false else type === other.type && time.matches(other.time)
    }

    override fun hashCode() = type.hashCode() + (time.toMillis() * 0.001).toInt()
    override fun toString(): String {
        return buildString {
            append("Pray(type=")
            append(type)
            append(", time=")
            append(formattedTime)
            append(", status=")
            append(status)
            append(", passed=")
            append(passed)
            append(", upcoming=")
            append(upcoming)
            append(", datetime='")
            append(time.getFormatted(PatternFormatter.DateTimeFull()))
            append("')")
        }
    }


}
