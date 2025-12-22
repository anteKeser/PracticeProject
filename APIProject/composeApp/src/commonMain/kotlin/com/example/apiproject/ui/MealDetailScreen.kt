package com.example.apiproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import apiproject.composeapp.generated.resources.Res
import apiproject.composeapp.generated.resources.image_placeholder
import apiproject.composeapp.generated.resources.loading
import coil3.compose.AsyncImage
import com.example.apiproject.model.Meal
import com.example.apiproject.viewModel.MealDetailUiState
import com.example.apiproject.viewModel.MealDetailViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MealDetailScreen(
    mealId: String,
    modifier: Modifier,
) {

    val viewModel = koinViewModel<MealDetailViewModel>()
    LaunchedEffect(mealId) {
        viewModel.loadMealById(mealId)
    }

  val mealUiState by viewModel.uiState.collectAsStateWithLifecycle()
  when (mealUiState) {
    is MealDetailUiState.Loading -> LoadingScreen()
    is MealDetailUiState.Error -> ErrorScreen(retryAction = { viewModel.loadMealById(mealId) })
    is MealDetailUiState.Success -> {
      val meal = (mealUiState as MealDetailUiState.Success).meal
      MealDetailContent(meal, modifier)
    }
  }
}

@Composable
fun MealDetailContent(meal: Meal, modifier: Modifier = Modifier) {
  Column(modifier = Modifier.verticalScroll(rememberScrollState()).background(MaterialTheme.colorScheme.background)) {
    AsyncImage(
        model = meal.strMealThumb,
        error = painterResource(Res.drawable.image_placeholder),
        placeholder = painterResource(Res.drawable.loading),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(440.dp) ,
    )
    Text(
        meal.strMeal,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(10.dp),
    )
    Text(
        meal.strInstructions ?: "No instructions available",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(10.dp),
    )
  }
}
