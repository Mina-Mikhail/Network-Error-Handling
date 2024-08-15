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

import com.minaMikhail.networkErrorHandling.network.call.ResultCallDelegate
import com.minaMikhail.networkErrorHandling.network.responseParser.ResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.di.FailureResponseParser
import com.minaMikhail.networkErrorHandling.network.responseParser.di.SuccessResponseParser
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

internal class ApiCallAdapter<T : Any>(
  private val successType: Type,
  private val resourceProvider: ResourceProvider,
  @SuccessResponseParser private val successResponseParser: ResponseParser,
  @FailureResponseParser private val failureResponseParser: ResponseParser
) : CallAdapter<T, Call<Result<T>>> {

  override fun responseType(): Type = successType

  override fun adapt(call: Call<T>): Call<Result<T>> {
    return ResultCallDelegate(
      delegate = call,
      resourceProvider = resourceProvider,
      successResponseParser = successResponseParser,
      failureResponseParser = failureResponseParser
    )
  }
}