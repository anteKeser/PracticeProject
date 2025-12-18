package com.example.apiproject.network

import io.ktor.client.engine.HttpClientEngine

expect class HttpClientEngineFactory() {
  fun getHttpEngine(): HttpClientEngine
}
