package com.example.androidappfrontend.model

import java.io.Serializable

/**
 * Data class representing a Habit with name and description.
 */
data class Habit(
    var name: String,
    var description: String
) : Serializable
