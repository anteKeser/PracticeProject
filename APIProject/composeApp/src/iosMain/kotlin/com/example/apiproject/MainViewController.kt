package com.example.apiproject

import androidx.compose.ui.window.ComposeUIViewController
import com.example.apiproject.di.AppModule
import com.example.apiproject.di.MealsModule
import com.example.apiproject.di.initKoin

private var koinInitialized = false


fun MainViewController() = ComposeUIViewController {

    if (!koinInitialized) {
        initKoin()
        koinInitialized = true
    }

    App() }