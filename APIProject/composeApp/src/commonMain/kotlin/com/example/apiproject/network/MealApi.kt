package com.example.apiproject.network

import com.example.apiproject.domain.Response
import com.example.apiproject.model.Meal
import com.example.apiproject.model.MealsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MealApi(private val client: HttpClient) {

  suspend fun fetchMeals(): Response<List<MealDTO>> {
      return safeApiCall {
          client.get(ApiRoutes.SEARCH) { parameter("s", "") }
      }

  }

  suspend fun fetchMealByName(name: String): Meal {
     return safeApiCall {
        client.get(ApiRoutes.SEARCH) { parameter("s", name) }
    }
  }

  suspend fun fetchMealById(id: String): Response<MealDTO> {
       return safeApiCall {
          client.get(ApiRoutes.SEARCH) { parameter("s", id) }
      }
  }

  suspend fun fetchMealsByRegion(region: String): List<Meal>? {
    return client.get(ApiRoutes.FILTER) { parameter("a", region) }.body<MealsResponse>().meals
  }
}
