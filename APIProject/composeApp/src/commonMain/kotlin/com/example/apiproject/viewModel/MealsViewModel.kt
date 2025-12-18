package com.example.apiproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiproject.model.Meal
import com.example.apiproject.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MealsUiState {
  object Loading : MealsUiState()

  data class Success(val meals: List<Meal>) : MealsUiState()

  data class Error(val message: String) : MealsUiState()
}

class MealsViewModel(private val repository: MealsRepository) : ViewModel() {
  private val _uiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
  val uiState: StateFlow<MealsUiState> = _uiState.asStateFlow()


  init {
    viewModelScope.launch {
      try {
        val meals = repository.getMeals()
        _uiState.value = MealsUiState.Success(meals)
      } catch (e: Exception) {
        e.printStackTrace()
        _uiState.value = MealsUiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}
