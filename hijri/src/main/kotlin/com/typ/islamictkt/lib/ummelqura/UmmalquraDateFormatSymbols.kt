/*
 * This product is developed by TYP Software
 * Project Head : Ahmed Sleem
 * Programmer : Ahmed Sleem
 * Pre-Release Tester : Ahmed Sleem & Ahmed Hafez
 *
 * Copyright (c) TYP Electronics Corporation.  All Rights Reserved
 */
package com.typ.islamictkt.lib.ummelqura

import java.util.*

/**
 * @author Mouaffak A. Sarhan.
 */
class UmmalquraDateFormatSymbols {
    /**
     * The locale which is used for initializing this DateFormatSymbols object.
     *
     * @serial
     */
    private lateinit var locale: Locale

    /**
     * Month strings. For example: "Muharram", "Safar", etc.  An array of 12 strings, indexed by
     * `UmmalquraCalendar.MUHARRAM`, `UmmalquraCalendar.SAFAR`, etc.
     *
     * @serial
     */
    private lateinit var months: Array<String>

    /**
     * Short month strings. For example: "Muh", "Saf", etc.  An array of 12 strings, indexed by
     * `UmmalquraCalendar.MUHARRAM`, `UmmalquraCalendar.SAFAR`, etc.
     *
     * @serial
     */
    private lateinit var shortMonths: Array<String>

    constructor() {
        initializeData(Locale.getDefault(Locale.Category.FORMAT))
    }

    constructor(locale: Locale) {
        initializeData(locale)
    }

    /**
     * Gets month strings. For example: "Muharram", "Safar", etc.
     *
     * @return the month strings.
     */
    fun getMonths(): Array<String> {
        return months.copyOf(months.size).requireNoNulls()
    }

    /**
     * Gets short month strings. For example: "Muh", "Saf", etc.
     *
     * @return the short month strings.
     */
    fun getShortMonths(): Array<String> {
        return shortMonths.copyOf(shortMonths.size).requireNoNulls()
    }

    private fun initializeData(desiredLocale: Locale) {
        require(
            "ar".equals(desiredLocale.language, ignoreCase = true) || "en"
                .equals(desiredLocale.language, ignoreCase = true)
        ) { "Supported locales are 'English' and 'Arabic'" }
        locale = desiredLocale

        // Initialize the fields from the ResourceBundle for locale.
        val resource = ResourceBundle
            .getBundle(
                "com.typ.islamictkt.lib.ummelqura.text.UmmalquraFormatData",
                locale
            )
        months = resource.getStringArray("MonthNames")
        shortMonths = resource.getStringArray("MonthAbbreviations")
    }
}
