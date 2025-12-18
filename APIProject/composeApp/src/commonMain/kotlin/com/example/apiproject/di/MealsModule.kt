package com.example.apiproject.di

import com.example.apiproject.network.MealApi
import com.example.apiproject.repository.MealsRepository
import com.example.apiproject.viewModel.MealsViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
class MealsModule {

    @Single
    fun mealsApi(httpClient: HttpClient) = MealApi(httpClient)

    @Factory
    fun mealsRepository(mealApi: MealApi) = MealsRepository(mealApi)

    @KoinViewModel
    fun mealsViewModel(mealsRepository: MealsRepository) = MealsViewModel(mealsRepository)

}
