/*
 * Copyright 2024 Mina Mikhail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minaMikhail.networkErrorHandling.network.responseParser.failure

import com.google.gson.JsonSyntaxException
import com.minaMikhail.networkErrorHandling.network.R
import com.minaMikhail.networkErrorHandling.network.enums.NetworkError
import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response

class FailureResponseParserImpl @Inject constructor(
  private val resourceProvider: ResourceProvider
) : FailureResponseParser {

  override fun <S, F : ErrorResponse> parse(
    errorBodyConverter: Converter<ResponseBody, F>,
    response: Response<S>
  ): NetworkResult<S, F> {
    val (networkError, errorMessage) = parseNetworkError(response.code())

    val errorBody: ResponseBody = response.errorBody()
      ?: return NetworkResult.Failure(
        errorType = networkError,
        errorMessage = errorMessage
      )

    return try {
      val convertedBody = errorBodyConverter.convert(errorBody)

      NetworkResult.Failure(
        errorBody = convertedBody as F,
        code = response.code(),
        errorType = networkError,
        errorMessage = convertedBody.message ?: errorMessage
      )
    } catch (error: Throwable) {
      NetworkResult.Failure(
        errorType = networkError,
        errorMessage = errorMessage
      )
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <S, F : ErrorResponse> parse(
    errorBodyConverter: Converter<ResponseBody, F>,
    throwable: Throwable
  ): NetworkResult<S, F> {
    return when (throwable) {
      is ConnectException,
      is SocketTimeoutException,
      is UnknownHostException -> {
        NetworkResult.Failure(
          errorType = NetworkError.Network.NO_INTERNET_CONNECTION,
          errorMessage = resourceProvider.getText(R.string.no_internet_error_message)
        )
      }

      is HttpException -> {
        val response = throwable.response()

        if (response == null) {
          val (networkError, errorMessage) = parseNetworkError(throwable.code())

          NetworkResult.Failure(
            code = throwable.code(),
            errorType = networkError,
            errorMessage = errorMessage
          )
        } else {
          return parse(errorBodyConverter, response) as NetworkResult<S, F>
        }
      }

      is JsonSyntaxException -> {
        NetworkResult.Failure(
          errorType = NetworkError.Other.ILLEGAL_JSON_BODY_ERROR,
          errorMessage = throwable.message ?: resourceProvider.getText(R.string.json_body_error_message)
        )
      }

      else -> {
        NetworkResult.Failure(
          throwable = throwable,
          errorType = NetworkError.Other.OTHER_ERROR,
          errorMessage = resourceProvider.getText(R.string.general_network_error_message)
        )
      }
    }
  }

  private fun parseNetworkError(errorCode: Int?): Pair<NetworkError, String> {
    return when (errorCode) {
      HttpURLConnection.HTTP_UNAUTHORIZED -> {
        Pair(
          NetworkError.Network.UNAUTHORIZED,
          resourceProvider.getText(R.string.unauthorized_message)
        )
      }

      HttpURLConnection.HTTP_INTERNAL_ERROR -> {
        Pair(
          NetworkError.Network.SERVER_ERROR,
          resourceProvider.getText(R.string.server_internal_error)
        )
      }

      HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
        Pair(
          NetworkError.Network.GATEWAY_TIMEOUT,
          resourceProvider.getText(R.string.server_timeout_message)
        )
      }

      else -> {
        Pair(
          NetworkError.Other.OTHER_ERROR,
          resourceProvider.getText(R.string.general_network_error_message)
        )
      }
    }
  }
}