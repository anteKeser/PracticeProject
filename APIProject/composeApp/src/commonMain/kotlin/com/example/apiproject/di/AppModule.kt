package com.example.apiproject.di

import com.example.apiproject.network.HttpClientEngineFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AppModule {

  @Single
  fun httpClient(engine: HttpClientEngine): HttpClient =
      HttpClient(engine) {
        install(Logging) { level = LogLevel.ALL }

        install(ContentNegotiation) { json(json = Json { ignoreUnknownKeys = true }) }
      }

  @Factory fun httpClientEngine(): HttpClientEngine = HttpClientEngineFactory().getHttpEngine()
}
