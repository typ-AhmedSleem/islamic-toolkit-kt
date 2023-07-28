/*
 * This product is developed by TYP Software
 * Project Head : Ahmed Sleem
 * Programmer : Ahmed Sleem
 * Pre-Release Tester : Ahmed Sleem & Ahmed Hafez
 *
 * Copyright (c) TYP Electronics Corporation.  All Rights Reserved
 */
package com.typ.islamictkt.lib.ummelqura.text

import java.util.*

/**
 * @author Mouaffak A. Sarhan.
 */
class UmmalquraFormatData_en : ListResourceBundle() {
    override fun getContents(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(
                "MonthNames", arrayOf(
                    "Muharram",
                    "Safar",
                    "Rabi' al-Awwal",
                    "Rabi' al-Thani",
                    "Jumada al-Ula",
                    "Jumada al-Akhirah",
                    "Rajab",
                    "Sha'ban",
                    "Ramadhan",
                    "Shawwal",
                    "Thul-Qi'dah",
                    "Thul-Hijjah"
                )
            ), arrayOf(
                "MonthAbbreviations", arrayOf(
                    "Muh", "Saf", "Rab-I", "Rab-II", "Jum-I", "Jum-II", "Raj", "Sha", "Ram", "Shw", "Thul-Q", "Thul-H"
                )
            )
        )
    }
}
