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

package com.minaMikhail.networkErrorHandling.network.call

import com.minaMikhail.networkErrorHandling.network.model.ErrorResponse
import com.minaMikhail.networkErrorHandling.network.responseParser.failure.FailureResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.success.SuccessResponseParser
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.lang.reflect.Type
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class NetworkResultCall<S, F : ErrorResponse>(
  private val apiCall: Call<S>,
  private val successBodyType: Type,
  private val errorBodyConverter: Converter<ResponseBody, F>,
  private val resourceProvider: ResourceProvider,
  private val successResponseParser: SuccessResponseParser,
  private val failureResponseParser: FailureResponseParser
) : Call<NetworkResult<S, F>> {

  override fun enqueue(callback: Callback<NetworkResult<S, F>>) {
    apiCall.enqueue(object : Callback<S> {
      override fun onResponse(call: Call<S>, response: Response<S>) {
        callback.onResponse(
          this@NetworkResultCall,
          Response.success(parseResult(response))
        )
      }

      override fun onFailure(call: Call<S>, throwable: Throwable) {
        callback.onResponse(
          this@NetworkResultCall,
          Response.success(failureResponseParser.parse(errorBodyConverter, throwable))
        )
      }
    })
  }

  override fun execute(): Response<NetworkResult<S, F>> = Response.success(parseResult(apiCall.execute()))

  override fun isExecuted(): Boolean = apiCall.isExecuted

  override fun cancel() = apiCall.cancel()

  override fun isCanceled(): Boolean = apiCall.isCanceled

  override fun clone(): Call<NetworkResult<S, F>> = NetworkResultCall(
    apiCall = apiCall,
    successBodyType = successBodyType,
    errorBodyConverter = errorBodyConverter,
    resourceProvider = resourceProvider,
    successResponseParser = successResponseParser,
    failureResponseParser = failureResponseParser
  )

  override fun request(): Request = apiCall.request()

  override fun timeout(): Timeout = apiCall.timeout()

  private fun <S> parseResult(response: Response<S>): NetworkResult<S, F> {
    return if (response.isSuccessful) {
      successResponseParser.parse(successBodyType, response)
    } else {
      failureResponseParser.parse(errorBodyConverter, response)
    }
  }
}