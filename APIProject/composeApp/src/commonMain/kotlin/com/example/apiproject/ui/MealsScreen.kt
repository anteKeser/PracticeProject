package com.example.apiproject.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apiproject.model.Meal
import com.example.apiproject.viewModel.MealsUiState
import com.example.apiproject.viewModel.MealsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MealsScreen(modifier: Modifier = Modifier, viewModel: MealsViewModel = koinViewModel()) {
  val mealsUiState by viewModel.uistate.collectAsStateWithLifecycle()
  when (mealsUiState) {
    is MealsUiState.Error -> ErrorScreen()
    is MealsUiState.Success -> MealsGridScreen((mealsUiState as MealsUiState.Success).meals)
    is MealsUiState.Loading -> LoadingScreen()
  }
}

@Composable
fun MealsGridScreen(meals: List<Meal>, modifier: Modifier = Modifier) {
  LazyColumn { items(meals, key = { meal -> meal.idMeal }) { meal -> Text(meal.idMeal) } }
}

@Composable
fun LoadingScreen() {
  Text("Loading....")
}

@Composable
fun ErrorScreen() {
  Text("Error happened")
}
