package com.example.apiproject.model

import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(val meals: List<Meal>?)
