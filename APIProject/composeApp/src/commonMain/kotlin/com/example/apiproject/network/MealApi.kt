package com.example.apiproject.network

import com.example.apiproject.model.Meal
import com.example.apiproject.model.MealsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MealApi(private val client: HttpClient) {
  suspend fun fetchMeals(): List<Meal>? {
    return client
        .get("https://www.themealdb.com/api/json/v1/1/search.php?s=")
        .body<MealsResponse>()
        .meals
  }

  suspend fun fetchMealByName(name: String): Meal {
    return client
        .get("https://www.themealdb.com/api/json/v1/1/search.php?s=$name")
        .body<MealsResponse>()
        .meals!!
        .first()
  }

  suspend fun fetchMealById(id: String): Meal {
    return client
        .get("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id")
        .body<MealsResponse>()
        .meals!!
        .first()
  }

  suspend fun fetchMealsByRegion(region: String): List<Meal>? {
    return client
        .get("https://www.themealdb.com/api/json/v1/1/filter.php?a=$region")
        .body<MealsResponse>()
        .meals
  }
}
