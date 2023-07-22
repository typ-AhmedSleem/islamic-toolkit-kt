package com.typ.islamictkt.locale

import java.util.*

/**
 * Locale Manager
 *
 * The `LocaleManager` object provides utility functions and management for handling locales in your application.
 * The object can be used to access locale-related functionality throughout the application.
 */
object LocaleManager {

    /**
     * Get the Default Locale.
     *
     * This function returns the default locale used in the system. The default locale
     * is determined based on the language and country settings of the underlying operating system.
     *
     * @return The default `Locale` used in the system.
     *
     * @see Locale.getDefault for more details on how it works
     */
    fun getDefault(): Locale = Locale.getDefault()

    /**
     * This function creates a new custom locale with given language code.
     *
     * @param language Construct a locale from a language code.
     * This constructor of Locale(code) normalizes the language value to lowercase.
     *
     * @return a new Locale with the given language code.
     */
    fun custom(language: String) = Locale(language)

    /**
     * Locales
     *
     * The `Locales` object provides an easy way to get locales that are not in `java.util.Locale`
     * such as: Arabic
     */
    object Locales {
        /**
         * Locale object for Arabic
         */
        val ARABIC = custom("ar")
    }

}