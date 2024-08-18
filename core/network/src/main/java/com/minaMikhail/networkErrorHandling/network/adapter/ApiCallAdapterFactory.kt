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

package com.minaMikhail.networkErrorHandling.network.adapter

import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.responseParser.failure.FailureResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.success.SuccessResponseParser
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

class ApiCallAdapterFactory @Inject constructor(
  private val resourceProvider: ResourceProvider,
  private val successResponseParser: SuccessResponseParser,
  private val failureResponseParser: FailureResponseParser
) : CallAdapter.Factory() {

  override fun get(
    returnType: Type,
    annotations: Array<out Annotation>,
    retrofit: Retrofit
  ): CallAdapter<*, *>? {
    if (returnType !is ParameterizedType) {
      return null
    }

    val argumentType = getParameterUpperBound(0, returnType)
    if (getRawType(argumentType) != NetworkResult::class.java) {
      return null
    }

    if (argumentType !is ParameterizedType) {
      return null
    }

    val successBodyType = getParameterUpperBound(0, argumentType)
    val failureBodyType = getParameterUpperBound(1, argumentType)

    val errorBodyConverter = retrofit.nextResponseBodyConverter<ErrorResponse>(
      null,
      failureBodyType,
      annotations
    )

    return when (getRawType(returnType)) {
      Call::class.java -> {
        ApiCallAdapter<Any, ErrorResponse>(
          successBodyType = successBodyType,
          errorBodyConverter = errorBodyConverter,
          resourceProvider = resourceProvider,
          successResponseParser = successResponseParser,
          failureResponseParser = failureResponseParser
        )
      }

      else -> {
        null
      }
    }
  }
}