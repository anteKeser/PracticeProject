package com.example.apiproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import apiproject.composeapp.generated.resources.Res
import apiproject.composeapp.generated.resources.cloud_off
import apiproject.composeapp.generated.resources.image_placeholder
import apiproject.composeapp.generated.resources.loading
import apiproject.composeapp.generated.resources.loading_failed
import apiproject.composeapp.generated.resources.retry
import coil3.compose.AsyncImage
import com.example.apiproject.model.Meal
import com.example.apiproject.viewModel.MealsUiState
import com.example.apiproject.viewModel.MealsViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MealsScreen(onMealClick: (String) -> Unit, modifier: Modifier = Modifier) {
  val viewModel = koinViewModel<MealsViewModel>()
  LaunchedEffect(Unit) { viewModel.loadMeals() }

  val mealsUiState by viewModel.uiState.collectAsStateWithLifecycle()
  when (mealsUiState) {
    is MealsUiState.Error -> ErrorScreen(retryAction = { viewModel.loadMeals() })
    is MealsUiState.Success ->
        MealsGridScreen(
            onMealClick,
            (mealsUiState as MealsUiState.Success).meals.toPersistentList(),
            modifier,
        )
    is MealsUiState.Loading -> LoadingScreen()
  }
}

@Composable
fun MealsGridScreen(
    onMealClick: (String) -> Unit,
    meals: ImmutableList<Meal>,
    modifier: Modifier = Modifier,
) {
  LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      verticalArrangement = Arrangement.Center,
      modifier = modifier.background(MaterialTheme.colorScheme.background),
  ) {
    items(meals, key = { meal -> meal.idMeal }) { meal -> MealCard(onMealClick, meal) }
  }
}

@Composable
fun MealCard(onClick: (String) -> Unit, meal: Meal, modifier: Modifier = Modifier) {
  Card(
      modifier = modifier.padding(10.dp).height(250.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      onClick = { onClick(meal.idMeal) },
  ) {
    Column {
      AsyncImage(
          model = meal.strMealThumb,
          error = painterResource(Res.drawable.image_placeholder),
          placeholder = painterResource(Res.drawable.loading),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxWidth().height(200.dp),
      )
      Text(meal.strMeal, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
    }
  }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
  Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(Res.drawable.loading),
        contentDescription = stringResource(Res.string.loading),
    )
  }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
  Column(
      modifier = modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
        painter = painterResource(Res.drawable.cloud_off),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
    Text(text = stringResource(Res.string.loading_failed), modifier = Modifier.padding(16.dp))
    Button(onClick = retryAction) { Text(stringResource(Res.string.retry)) }
  }
}
