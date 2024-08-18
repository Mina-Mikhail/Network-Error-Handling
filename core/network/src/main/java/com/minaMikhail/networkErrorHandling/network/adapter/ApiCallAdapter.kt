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

import com.minaMikhail.networkErrorHandling.network.call.NetworkResultCall
import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.responseParser.failure.FailureResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.success.SuccessResponseParser
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.lang.reflect.Type
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter

internal class ApiCallAdapter<S, F : ErrorResponse>(
  private val successBodyType: Type,
  private val errorBodyConverter: Converter<ResponseBody, F>,
  private val resourceProvider: ResourceProvider,
  private val successResponseParser: SuccessResponseParser,
  private val failureResponseParser: FailureResponseParser
) : CallAdapter<S, Call<NetworkResult<S, F>>> {

  override fun responseType(): Type = successBodyType

  override fun adapt(call: Call<S>): Call<NetworkResult<S, F>> {
    return NetworkResultCall(
      apiCall = call,
      successBodyType = successBodyType,
      errorBodyConverter = errorBodyConverter,
      resourceProvider = resourceProvider,
      successResponseParser = successResponseParser,
      failureResponseParser = failureResponseParser
    )
  }
}