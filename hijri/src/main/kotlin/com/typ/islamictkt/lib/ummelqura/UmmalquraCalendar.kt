/*
 * This product is developed by TYP Software
 * Project Head : Ahmed Sleem
 * Programmer : Ahmed Sleem
 * Pre-Release Tester : Ahmed Sleem & Ahmed Hafez
 *
 * Copyright (c) TYP Electronics Corporation.  All Rights Reserved
 */
package com.typ.islamictkt.lib.ummelqura

import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.lib.ummelqura.UmmalquraGregorianConverter.getDaysInMonth
import com.typ.islamictkt.lib.ummelqura.UmmalquraGregorianConverter.toGregorian
import com.typ.islamictkt.lib.ummelqura.UmmalquraGregorianConverter.toHijri
import java.util.*

/**
 * @author Mouaffak A. Sarhan.
 */
class UmmalquraCalendar : GregorianCalendar {
    /**
     * The calendar field values for the currently set time for this calendar. This is an array of
     * `FIELD_COUNT` integers, with index values `ERA` through
     * `DST_OFFSET`.
     *
     * @serial
     */
    private var hFields: IntArray? = null

    /**
     * Constructs a `UmmalquraCalendar` based on the current time in the default time
     * zone with the given locale.
     *
     * @param aLocale the given locale.
     */
    constructor(aLocale: Locale?) : this(TimeZone.getDefault(), aLocale)
    /**
     * Constructs a `UmmalquraCalendar` based on the current time in the given time zone
     * with the given locale.
     *
     * @param zone    the given time zone.
     * @param aLocale the given locale.
     */
    /**
     * Constructs a default `UmmalquraCalendar` using the current time in the default
     * time zone with the default locale.
     */
    @JvmOverloads
    constructor(zone: TimeZone? = TimeZone.getDefault(), aLocale: Locale? = Locale.getDefault()) : super(zone, aLocale)
    /**
     * Constructs a `UmmalquraCalendar` with the given date and time set for the default
     * time zone with the default locale.
     *
     * @param year       the value used to set the `YEAR` calendar field in the calendar.
     * @param month      the value used to set the `MONTH` calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for Muharram.
     * @param dayOfMonth the value used to set the `DAY_OF_MONTH` calendar field in the
     * calendar.
     * @param hourOfDay  the value used to set the `HOUR_OF_DAY` calendar field in the
     * calendar.
     * @param minute     the value used to set the `MINUTE` calendar field in the calendar.
     * @param second     the value used to set the `SECOND` calendar field in the calendar.
     */
    /**
     * Constructs a `UmmalquraCalendar` with the given date set in the default time zone
     * with the default locale.
     *
     * @param year       the value used to set the `YEAR` calendar field in the calendar.
     * @param month      the value used to set the `MONTH` calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for Muharram.
     * @param dayOfMonth the value used to set the `DAY_OF_MONTH` calendar field in the
     * calendar.
     */
    /**
     * Constructs a `UmmalquraCalendar` with the given date and time set for the default
     * time zone with the default locale.
     *
     * @param year       the value used to set the `YEAR` calendar field in the calendar.
     * @param month      the value used to set the `MONTH` calendar field in the calendar.
     * Month value is 0-based. e.g., 0 for Muharram.
     * @param dayOfMonth the value used to set the `DAY_OF_MONTH` calendar field in the
     * calendar.
     * @param hourOfDay  the value used to set the `HOUR_OF_DAY` calendar field in the
     * calendar.
     * @param minute     the value used to set the `MINUTE` calendar field in the calendar.
     */
    @JvmOverloads
    constructor(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hourOfDay: Int = 0,
        minute: Int = 0,
        second: Int = 0
    ) {
        set(YEAR, year)
        set(MONTH, month)
        set(DAY_OF_MONTH, dayOfMonth)
        set(HOUR_OF_DAY, hourOfDay)
        set(MINUTE, minute)
        set(SECOND, second)
    }

    override fun get(field: Int): Int {
        return if (field == YEAR || field == MONTH || field == DAY_OF_MONTH) {
            hFields!![field]
        } else super.get(field)
    }

    override fun set(field: Int, value: Int) {
        if (field == YEAR || field == MONTH || field == DAY_OF_MONTH) {
            val hDateInfo = toHijri(Timestamp(getTime()))
            if (field == YEAR) hDateInfo[0] = value else if (field == MONTH) hDateInfo[1] = value else hDateInfo[2] = value
            val gDateInfo = toGregorian(hDateInfo[0], hDateInfo[1], hDateInfo[2])
            super.set(YEAR, gDateInfo[0])
            super.set(MONTH, gDateInfo[1])
            super.set(DAY_OF_MONTH, gDateInfo[2])
            complete()
        } else {
            super.set(field, value)
        }
    }

    /**
     * Returns the string representation of the calendar `field` value in the given
     * `style` and `locale`.  If no string representation is applicable,
     * `null` is returned. This method calls [get(field)][Calendar.get] to get
     * the calendar `field` value if the string representation is applicable to the given
     * calendar `field`.
     *
     *For example, if this `Calendar`'s date is
     * 1437-01-01, then the string representation of the [.MONTH] field would be "Muharram" in
     * the long style in an English locale or "Muh" in the short style. However, no string
     * representation would be available for the [.DAY_OF_MONTH] field, and this method would
     * return `null`.
     *
     *The default implementation supports the calendar fields for
     * which a [UmmalquraDateFormatSymbols] has names in the given `locale`.
     *
     * @param field  the calendar field for which the string representation is returned
     * @param style  the style applied to the string representation; one of [.SHORT] or [               ][.LONG].
     * @param locale the locale for the string representation
     * @return the string representation of the given `field` in the given
     * `style`, or `null` if no string representation is applicable.
     * @throws IllegalArgumentException if `field` or `style` is invalid, or
     * if this `Calendar` is non-lenient and any of the calendar fields have invalid
     * values
     * @throws NullPointerException     if `locale` is null
     * @since 1.6
     */
    override fun getDisplayName(field: Int, style: Int, locale: Locale): String? {
        if (field == MONTH) {
            val symbols = UmmalquraDateFormatSymbols(locale)
            val strings = getFieldStrings(field, style, symbols)
            if (strings != null) {
                val fieldValue = get(field)
                if (fieldValue < strings.size) {
                    return strings[fieldValue]
                }
            }
            return null
        }
        return super.getDisplayName(field, style, locale)
    }

    /**
     * Returns a `Map` containing all names of the calendar `field` in the
     * given `style` and `locale` and their corresponding field values. For
     * example, The returned map would contain "Muh" to [.MUHARRAM], "Saf" to [.SAFAR],
     * and so on, in the [short][.SHORT] style in an English locale.
     *
     *The values of
     * other calendar fields may be taken into account to determine a set of display names. For
     * example, if this `Calendar` is a lunisolar calendar system and the year value
     * given by the [.YEAR] field has a leap month, this method would return month names
     * containing the leap month name, and month names are mapped to their values specific for the
     * year.
     *
     *This implementation supports display names contained in a [ ]. For example, if `field` is [.MONTH] and
     * `style` is [.ALL_STYLES], this method returns a `Map` containing
     * all strings returned by [UmmalquraDateFormatSymbols.getShortMonths] and [ ][UmmalquraDateFormatSymbols.getMonths].
     *
     * @param field  the calendar field for which the display names are returned
     * @param style  the style applied to the display names; one of [.SHORT], [.LONG], or
     * [.ALL_STYLES].
     * @param locale the locale for the display names
     * @return a `Map` containing all display names in `style` and
     * `locale` and their field values, or `null` if no display names are
     * defined for `field`
     * @throws IllegalArgumentException if `field` or `style` is invalid, or
     * if this `Calendar` is non-lenient and any of the calendar fields have invalid
     * values
     * @throws NullPointerException     if `locale` is null
     */
    override fun getDisplayNames(field: Int, style: Int, locale: Locale): Map<String, Int>? {
        if (field == MONTH) {
            // ALL_STYLES
            if (style == ALL_STYLES) {
                val shortNames: MutableMap<String, Int>? = getDisplayNamesImpl(field, SHORT, locale)
                val longNames: MutableMap<String, Int>? = getDisplayNamesImpl(field, LONG, locale)

                if (shortNames == null) return longNames
                if (longNames != null) shortNames.putAll(longNames)
                return shortNames
            }

            // SHORT or LONG
            return getDisplayNamesImpl(field, style, locale)!!
        }
        return super.getDisplayNames(field, style, locale)
    }

    private fun getDisplayNamesImpl(field: Int, style: Int, locale: Locale): MutableMap<String, Int>? {
        val symbols = UmmalquraDateFormatSymbols(locale)
        val strings: Array<String>? = getFieldStrings(field, style, symbols)
        if (strings != null) {
            val names: MutableMap<String, Int> = HashMap()
            for (i in strings.indices) {
                if (strings[i].isEmpty()) continue
                names[strings[i]] = i
            }
            return names
        }
        return null
    }

    private fun getFieldStrings(field: Int, style: Int, symbols: UmmalquraDateFormatSymbols): Array<String>? {
        if (field == MONTH) {
            if (SHORT == style) {
                return symbols.getShortMonths()
            }
            if (LONG == style) {
                return symbols.getMonths()
            }
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        return other is UmmalquraCalendar && super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode() xor 622
    }

    override fun computeFields() {
        super.computeFields()
        if (hFields == null) {
            hFields = IntArray(super.fields.size)
        }
        val hDateInfo = toHijri(time)
        hFields!![YEAR] = hDateInfo[0]
        hFields!![MONTH] = hDateInfo[1]
        hFields!![DAY_OF_MONTH] = hDateInfo[2]
    }

    companion object {
        /**
         * Value of the [.MONTH] field indicating the first month of the year in the Ummalqura
         * calendar.
         */
        var MUHARRAM = 0

        /**
         * Value of the [.MONTH] field indicating the second month of the year in the Ummalqura
         * calendar.
         */
        var SAFAR = 1

        /**
         * Value of the [.MONTH] field indicating the third month of the year in the Ummalqura
         * calendar.
         */
        var RABI_AWWAL = 2

        /**
         * Value of the [.MONTH] field indicating the fourth month of the year in the Ummalqura
         * calendar.
         */
        var RABI_THANI = 3

        /**
         * Value of the [.MONTH] field indicating the fifth month of the year in the Ummalqura
         * calendar.
         */
        var JUMADA_AWWAL = 4

        /**
         * Value of the [.MONTH] field indicating the sixth month of the year in the Ummalqura
         * calendar.
         */
        var JUMADA_THANI = 5

        /**
         * Value of the [.MONTH] field indicating the seventh month of the year in the Ummalqura
         * calendar.
         */
        var RAJAB = 6

        /**
         * Value of the [.MONTH] field indicating the eighth month of the year in the Ummalqura
         * calendar.
         */
        var SHAABAN = 7

        /**
         * Value of the [.MONTH] field indicating the ninth month of the year in the Ummalqura
         * calendar.
         */
        var RAMADAN = 8

        /**
         * Value of the [.MONTH] field indicating the tenth month of the year in the Ummalqura
         * calendar.
         */
        var SHAWWAL = 9

        /**
         * Value of the [.MONTH] field indicating the eleventh month of the year in the Ummalqura
         * calendar.
         */
        var THUL_QIDAH = 10

        /**
         * Value of the [.MONTH] field indicating the twelfth month of the year in the Ummalqura
         * calendar.
         */
        var THUL_HIJJAH = 11
        /**
         * Returns the length of a Hijri month in a Hijri year.
         *
         * This returns the length of the month
         * in days.
         *
         * @param year  Hijri year
         * @param month Hijri month
         * @return the length of the month in days
         */
        /**
         * Returns the length of the month represented by this calendar.
         *
         * This returns the length of
         * the month in days.
         *
         * @return the length of the month in days
         */
        fun lengthOfMonth(year: Int, month: Int): Int {
            return getDaysInMonth(year, month)
        }
        /**
         * Returns the length of the given year.
         *
         * This returns the length of the year in days, either
         * 354 or 355.
         *
         * @param year the year to calculate day count for.
         * @return 355 if the year is leap, 354 otherwise
         */
        /**
         * Returns the length of the year represented by this calendar.
         *
         * This returns the length of
         * the year in days, either 354 or 355.
         *
         * @return 355 if the year is leap, 354 otherwise
         */
        fun lengthOfYear(year: Int): Int {
            var total = 0
            for (m in MUHARRAM..THUL_HIJJAH) {
                total += lengthOfMonth(year, m)
            }
            return total
        }

    }

}
