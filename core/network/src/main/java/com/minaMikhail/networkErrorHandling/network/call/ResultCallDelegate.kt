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

import com.minaMikhail.networkErrorHandling.network.R
import com.minaMikhail.networkErrorHandling.network.enums.NetworkError
import com.minaMikhail.networkErrorHandling.network.exceptions.NetworkException
import com.minaMikhail.networkErrorHandling.network.responseParser.ResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.di.FailureResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.di.SuccessResponseParser
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ResultCallDelegate<T>(
  private val delegate: Call<T>,
  private val resourceProvider: ResourceProvider,
  @SuccessResponseParser private val successResponseParser: ResponseParser,
  @FailureResponseParser private val failureResponseParser: ResponseParser
) : Call<Result<T>> {

  override fun enqueue(callback: Callback<Result<T>>) {
    delegate.enqueue(object : Callback<T> {
      override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
          callback.onResponse(
            this@ResultCallDelegate,
            Response.success(successResponseParser.parse(response))
          )
        } else {
          callback.onResponse(
            this@ResultCallDelegate,
            Response.success(failureResponseParser.parse(response))
          )
        }
      }

      override fun onFailure(call: Call<T>, throwable: Throwable) {
        val failureResult = when (throwable) {
          is ConnectException,
          is SocketTimeoutException,
          is UnknownHostException -> {
            Result.failure<T>(
              exception = NetworkException(
                errorType = NetworkError.Network.NO_INTERNET_CONNECTION,
                errorMessage = resourceProvider.getText(R.string.no_internet_error_message)
              )
            )
          }

          is HttpException -> {
            failureResponseParser.parse(throwable.response())
          }

          else -> {
            Result.failure(throwable)
          }
        }

        callback.onResponse(this@ResultCallDelegate, Response.success(failureResult))
      }
    })
  }

  override fun execute(): Response<Result<T>> = Response.success(Result.success(delegate.execute().body()!!))

  override fun isExecuted(): Boolean = delegate.isExecuted

  override fun cancel() = delegate.cancel()

  override fun isCanceled(): Boolean = delegate.isCanceled

  override fun clone(): Call<Result<T>> = ResultCallDelegate(
    delegate = delegate.clone(),
    resourceProvider = resourceProvider,
    successResponseParser = successResponseParser,
    failureResponseParser = failureResponseParser
  )

  override fun request(): Request = delegate.request()

  override fun timeout(): Timeout = delegate.timeout()
}