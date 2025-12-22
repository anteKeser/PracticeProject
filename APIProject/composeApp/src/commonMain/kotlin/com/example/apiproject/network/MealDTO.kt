package com.example.apiproject.network

import kotlinx.serialization.Serializable

@Serializable
data class MealDTO(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strInstructions: String? = null,
)
