package com.example.apiproject.network

import com.example.apiproject.model.Meal
import com.example.apiproject.model.MealsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MealApi(private val client: HttpClient) {

  suspend fun fetchMeals(): List<Meal>? {
    return client.get(ApiRoutes.SEARCH) { parameter("s", "") }.body<MealsResponse>().meals
  }

  suspend fun fetchMealByName(name: String): Meal {
    return client
        .get(ApiRoutes.SEARCH) { parameter("s", name) }
        .body<MealsResponse>()
        .meals!!
        .first()
  }

  suspend fun fetchMealById(id: String): Meal {
    return client.get(ApiRoutes.LOOKUP) { parameter("i", id) }.body<MealsResponse>().meals!!.first()
  }

  suspend fun fetchMealsByRegion(region: String): List<Meal>? {
    return client.get(ApiRoutes.FILTER) { parameter("a", region) }.body<MealsResponse>().meals
  }
}
