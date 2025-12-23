package com.example.apiproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiproject.domain.Response
import com.example.apiproject.model.Meal
import com.example.apiproject.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.collect

sealed class MealsUiState {
  object Loading : MealsUiState()

  data class Success(val meals: List<Meal>) : MealsUiState()

  data class Error(val message: String) : MealsUiState()
}

class MealsViewModel(private val repository: MealsRepository) : ViewModel() {
  private val _uiState = MutableStateFlow<MealsUiState>(MealsUiState.Loading)
  val uiState: StateFlow<MealsUiState> = _uiState.asStateFlow()


    fun loadMeals() {
    viewModelScope.launch {
      try {
        val meals = repository.getMeals()
        meals.collect {
            when (it) {
                is Response.Success -> _uiState.value = MealsUiState.Success(it.data)
                is Response.Failure -> _uiState.value = MealsUiState.Error(it.e?.message ?: "Unknown error")
                is Response.Unauthorized -> _uiState.value = MealsUiState.Error("Unauthorized")
                else -> _uiState.value = MealsUiState.Error("Unknown error")
            }
        }
      } catch (e: Exception) {
        e.printStackTrace()
        _uiState.value = MealsUiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}
