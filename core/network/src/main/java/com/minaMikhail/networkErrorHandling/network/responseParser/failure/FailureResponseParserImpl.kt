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

import com.minaMikhail.networkErrorHandling.network.R
import com.minaMikhail.networkErrorHandling.network.enums.NetworkError
import com.minaMikhail.networkErrorHandling.network.exceptions.NetworkException
import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.responseParser.ResponseParser
import com.minaMikhail.networkErrorHandling.utils.providers.gsonMappingProvider.GsonMappingProvider
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.net.HttpURLConnection
import javax.inject.Inject
import retrofit2.Response

class FailureResponseParserImpl @Inject constructor(
  private val resourceProvider: ResourceProvider,
  private val gsonMappingProvider: GsonMappingProvider
) : ResponseParser {

  override fun <T> parse(response: Response<*>?): Result<T> {
    val errorBody = gsonMappingProvider.toJsonModel(
      jsonString = response?.errorBody()?.string(),
      modelClass = ErrorResponse::class.java
    )

    val networkError = parseNetworkError(response?.code())

    return Result.failure(
      exception = NetworkException(
        code = response?.code(),
        errorBody = errorBody,
        errorType = networkError.first,
        errorMessage = errorBody?.message ?: networkError.second
      )
    )
  }

  private fun parseNetworkError(errorCode: Int?): Pair<NetworkError, String> {
    return when (errorCode) {
      HttpURLConnection.HTTP_BAD_GATEWAY -> {
        Pair(
          NetworkError.Network.BAD_GATEWAY,
          resourceProvider.getText(R.string.bad_gateway_message)
        )
      }

      HttpURLConnection.HTTP_UNAUTHORIZED -> {
        Pair(
          NetworkError.Network.UNAUTHORIZED,
          resourceProvider.getText(R.string.unauthorized_message)
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