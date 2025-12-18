package com.example.apiproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import apiproject.composeapp.generated.resources.Res
import apiproject.composeapp.generated.resources.app_name
import apiproject.composeapp.generated.resources.arrow_back
import com.example.apiproject.ui.MealDetailScreen
import com.example.apiproject.ui.MealsScreen
import com.example.apiproject.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  AppTheme() {
    val navController = rememberNavController()

      val showArrowBack = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
          MealsTopAppBar(onClick = { navController.popBackStack() }, showArrowBack = showArrowBack.value)
        }
    ) { innerPadding ->
      NavHost(navController = navController, startDestination = "meals") {
        composable("meals") {
            LaunchedEffect(Unit) {
                showArrowBack.value = false
            }
          MealsScreen(
              onMealClick = { mealId -> navController.navigate("mealDetail/$mealId") },
              modifier = Modifier.padding(innerPadding),
          )
        }

        composable("mealDetail/{mealId}") { backStackEntry ->
            LaunchedEffect(Unit) {
                showArrowBack.value = true
            }
          val mealId = backStackEntry.savedStateHandle.get<String>("mealId") ?: "52772"

          MealDetailScreen(mealId = mealId, modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsTopAppBar(onClick: () -> Unit, modifier: Modifier = Modifier, showArrowBack: Boolean) {
  CenterAlignedTopAppBar(
      modifier = modifier.background(MaterialTheme.colorScheme.secondary),
      navigationIcon = {
        if (showArrowBack)
            IconButton(onClick = onClick) {
              Icon(painterResource(Res.drawable.arrow_back), contentDescription = null)
            }
      },
      title = {
        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
        )
      },
  )
}
