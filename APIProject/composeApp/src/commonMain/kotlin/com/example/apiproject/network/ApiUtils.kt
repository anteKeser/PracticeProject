package com.example.apiproject.network

import com.example.apiproject.domain.Response
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> safeApiCall(apiCall: () -> HttpResponse): Response<T> {
  return try {
    val response = apiCall()
    when (response.status.value) {
      in 200..299 -> Response.Success(response.body() as T)
      401 -> {
        Response.Unauthorized
      }

      else -> {
        Response.Failure(
            Exception("HTTP error ${response.status.value} ${response.status.description}")
        )
      }
    }
  } catch (e: RedirectResponseException) {
    Response.Failure(e)
  } catch (e: ClientRequestException) {
    if (e.response.status.value == 401) {
      Response.Unauthorized
    } else {
      Response.Failure(e)
    }
  } catch (e: ServerResponseException) {
    Response.Failure(e)
  } catch (e: Exception) {
    Response.Failure(e)
  }
}
