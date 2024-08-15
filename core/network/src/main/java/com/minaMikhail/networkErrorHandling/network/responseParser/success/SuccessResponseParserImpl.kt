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

package com.minaMikhail.networkErrorHandling.network.responseParser.success

import com.minaMikhail.networkErrorHandling.network.R
import com.minaMikhail.networkErrorHandling.network.enums.NetworkError
import com.minaMikhail.networkErrorHandling.network.exceptions.NetworkException
import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.responseParser.ResponseParser
import com.minaMikhail.networkErrorHandling.utils.providers.gsonMappingProvider.GsonMappingProvider
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import javax.inject.Inject
import retrofit2.Response

class SuccessResponseParserImpl @Inject constructor(
  private val resourceProvider: ResourceProvider,
  private val gsonMappingProvider: GsonMappingProvider
) : ResponseParser {

  @Suppress("UNCHECKED_CAST")
  override fun <T> parse(response: Response<*>?): Result<T> {
    val errorBody = gsonMappingProvider.toJsonModel(
      jsonString = response?.errorBody()?.string(),
      modelClass = ErrorResponse::class.java
    )

    return response?.body()?.let {
      Result.success(value = it as T)
    } ?: Result.failure(
      exception = NetworkException(
        code = response?.code(),
        errorBody = errorBody,
        errorType = NetworkError.Other.ILLEGAL_JSON_BODY_ERROR,
        errorMessage = errorBody?.message ?: resourceProvider.getText(R.string.json_body_error_message)
      )
    )
  }
}