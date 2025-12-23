package com.example.apiproject.repository

import com.example.apiproject.domain.Response
import com.example.apiproject.domain.toDomain
import com.example.apiproject.model.Meal
import com.example.apiproject.network.MealApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealsRepository(val api: MealApi) {
  suspend fun getMeals(): Flow<Response<List<Meal>>> = flow {
    emit(Response.Loading)
    try {
      when (val response = api.fetchMeals()) {
        is Response.Success -> emit(Response.Success(response.data.map { it.toDomain() }))
        is Response.Failure -> emit(response)
        is Response.Unauthorized -> emit(response)
        else -> emit(Response.Failure(Exception("Unexpected response type")))
      }
    } catch (e: Exception) {
      emit(Response.Failure(e))
    }
  }

  suspend fun getMealByName(name: String): Meal {
    return api.fetchMealByName(name)
  }

  suspend fun getMealsByRegion(region: String): List<Meal> {
    return api.fetchMealsByRegion(region) ?: emptyList()
  }

  suspend fun getMealById(id: String): Flow<Response<Meal>> = flow {
    emit(Response.Loading)
    try {
      when (val response = api.fetchMealById(id)) {
        is Response.Success -> emit(Response.Success(response.data.toDomain()))
        is Response.Failure -> emit(response)
        is Response.Unauthorized -> emit(response)
        else -> emit(Response.Failure(Exception("Unexpected response type")))
      }
    } catch (e: Exception) {
      emit(Response.Failure(e))
    }
  }
}
