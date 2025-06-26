package com.example.androidappfrontend.storage

import android.content.Context
import com.example.androidappfrontend.model.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Singleton Storage manager for Habits, using SharedPreferences and Gson.
 */
object HabitStorage {
    private const val PREFS_NAME = "habit_prefs"
    private const val HABITS_KEY = "habits_key"

    // PUBLIC_INTERFACE
    fun loadHabits(context: Context): MutableList<Habit> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(HABITS_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Habit>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    // PUBLIC_INTERFACE
    fun saveHabits(context: Context, habits: List<Habit>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(habits)
        prefs.edit().putString(HABITS_KEY, json).apply()
    }
}
