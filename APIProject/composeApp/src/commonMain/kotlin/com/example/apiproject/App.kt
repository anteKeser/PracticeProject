package com.example.apiproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.apiproject.ui.MealsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      MealsScreen(modifier = Modifier.padding(innerPadding))
    }
  }
}
