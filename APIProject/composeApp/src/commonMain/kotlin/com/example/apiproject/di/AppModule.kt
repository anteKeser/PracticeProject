package com.example.apiproject.di

import com.example.apiproject.network.HttpClientEngineFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

val AppModule: Module = module {
  single<HttpClientEngine> { HttpClientEngineFactory().getHttpEngine() }

  single {
    HttpClient(get()) { // 'get()' injects the HttpClientEngine
      install(Logging) { level = LogLevel.ALL }

      install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }

      install(DefaultRequest) {
        url {
          protocol = URLProtocol.HTTPS
          host = "www.themealdb.com"
        }
      }
    }
  }
}
