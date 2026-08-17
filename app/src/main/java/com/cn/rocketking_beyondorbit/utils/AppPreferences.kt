package com.cn.rocketking_beyondorbit.utils


import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    //==========================================================
    // Preference Keys
    //==========================================================

    const val KEY_BEST_SCORE = "best_score"
    const val KEY_BEST_DISTANCE = "best_distance"

    const val KEY_SOUND = "sound_enabled"
    const val KEY_MUSIC = "music_enabled"
    const val KEY_VIBRATION = "vibration_enabled"


    //==========================================================
    // Preferences
    //==========================================================

    private lateinit var preferences: SharedPreferences


    //==========================================================
    // Initialization
    //==========================================================

    fun init(context: Context) {

        preferences =
            context.getSharedPreferences(
                "orbit_score",
                Context.MODE_PRIVATE
            )
    }


    //==========================================================
    // String
    //==========================================================

    fun saveString(
        key: String,
        value: String
    ) {

        preferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getString(
        key: String
    ): String {

        return preferences
            .getString(key, "") ?: ""
    }


    //==========================================================
    // Int
    //==========================================================

    fun saveInt(
        key: String,
        value: Int
    ) {

        preferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    fun getInt(
        key: String
    ): Int {

        return preferences
            .getInt(key, 0)
    }


    //==========================================================
    // Boolean
    //==========================================================

    fun saveBoolean(
        key: String,
        value: Boolean
    ) {

        preferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean {

        return preferences
            .getBoolean(
                key,
                defaultValue
            )
    }


    //==========================================================
    // Remove
    //==========================================================

    fun remove(key: String) {

        preferences
            .edit()
            .remove(key)
            .apply()
    }


    //==========================================================
    // Contains
    //==========================================================

    fun contains(key: String): Boolean {

        return preferences.contains(key)
    }


    //==========================================================
    // Reset Best Results
    //==========================================================

    fun resetBestResults() {

        preferences
            .edit()
            .remove(KEY_BEST_SCORE)
            .remove(KEY_BEST_DISTANCE)
            .apply()
    }
}