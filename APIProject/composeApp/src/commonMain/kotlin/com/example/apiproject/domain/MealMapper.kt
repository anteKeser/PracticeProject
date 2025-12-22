package com.example.apiproject.domain

import com.example.apiproject.model.Meal
import com.example.apiproject.network.MealDTO

fun MealDTO.toDomain() =
    Meal(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strInstructions = strInstructions,
    )
