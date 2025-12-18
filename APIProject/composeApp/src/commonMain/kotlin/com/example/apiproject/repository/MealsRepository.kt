package com.example.apiproject.repository

import com.example.apiproject.model.Meal
import com.example.apiproject.network.MealApi

class MealsRepository(val api: MealApi) {
  suspend fun getMeals(): List<Meal> {
    return api.fetchMeals() ?: emptyList()
  }

  suspend fun getMealByName(name: String): Meal {
    return api.fetchMealByName(name)
  }

  suspend fun getMealsByRegion(region: String): List<Meal> {
    return api.fetchMealsByRegion(region) ?: emptyList()
  }

  suspend fun getMealById(id: String): Meal {
    return api.fetchMealById(id)
  }
}
