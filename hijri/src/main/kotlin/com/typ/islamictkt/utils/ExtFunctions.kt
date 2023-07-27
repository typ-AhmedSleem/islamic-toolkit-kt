package com.typ.islamictkt.utils

import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.lib.HijriCalendar

fun Timestamp.toHijri() = HijriCalendar.toHijri(this)