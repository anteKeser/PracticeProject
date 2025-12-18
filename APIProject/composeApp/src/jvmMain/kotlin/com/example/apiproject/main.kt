package com.example.apiproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.apiproject.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "APIProject",
        ) {
            App()
        }
    }
}