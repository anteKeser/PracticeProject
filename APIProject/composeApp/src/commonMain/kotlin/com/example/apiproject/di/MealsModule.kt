package com.example.apiproject.di

import com.example.apiproject.network.MealApi
import com.example.apiproject.repository.MealsRepository
import com.example.apiproject.viewModel.MealDetailViewModel
import com.example.apiproject.viewModel.MealsViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val MealsModule = module {
  single { MealApi(get<HttpClient>()) }

  factory { MealsRepository(get<MealApi>()) }

  // ViewModels
  viewModel { MealsViewModel(get<MealsRepository>()) }
  viewModel { MealDetailViewModel(get<MealsRepository>()) }
}
