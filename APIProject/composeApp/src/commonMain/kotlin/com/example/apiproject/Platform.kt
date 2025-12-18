package com.example.apiproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform