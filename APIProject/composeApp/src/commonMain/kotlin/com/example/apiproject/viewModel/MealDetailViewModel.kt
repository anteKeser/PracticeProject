package com.example.apiproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiproject.domain.Response
import com.example.apiproject.model.Meal
import com.example.apiproject.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MealDetailUiState() {
  object Loading : MealDetailUiState()

  data class Success(val meal: Meal) : MealDetailUiState()

  data class Error(val message: String) : MealDetailUiState()
}

class MealDetailViewModel(private val repository: MealsRepository) : ViewModel() {

  private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Loading)
  val uiState: StateFlow<MealDetailUiState> = _uiState.asStateFlow()

  fun loadMealById(mealId: String) {
    viewModelScope.launch {
      _uiState.value = MealDetailUiState.Loading
      try {
        val meal = repository.getMealById(mealId)
        meal.collect {
          when (it) {
            is Response.Success -> _uiState.value = MealDetailUiState.Success(it.data)
            is Response.Failure ->
                _uiState.value = MealDetailUiState.Error(it.e?.message ?: "Unknown error")
            is Response.Unauthorized -> _uiState.value = MealDetailUiState.Error("Unauthorized")
            else -> _uiState.value = MealDetailUiState.Error("Unknown error")
          }
        }
      } catch (e: Exception) {
        _uiState.value = MealDetailUiState.Error(e.message ?: "")
      }
    }
  }
}
