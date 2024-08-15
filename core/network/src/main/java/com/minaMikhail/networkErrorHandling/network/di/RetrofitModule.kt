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

package com.minaMikhail.networkErrorHandling.network.di

import com.google.gson.Gson
import com.minaMikhail.networkErrorHandling.network.BuildConfig
import com.minaMikhail.networkErrorHandling.network.adapter.ApiCallAdapterFactory
import com.minaMikhail.networkErrorHandling.network.utils.Headers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REQUEST_TIME_OUT: Long = 60

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

  @Provides
  @Singleton
  fun provideHeadersInterceptor() = Interceptor { chain ->
    val newRequest = chain
      .request()
      .newBuilder()
      .addHeader(Headers.Keys.ACCEPT, Headers.Values.ACCEPT_VALUE)
      .addHeader(Headers.Keys.CONTENT_TYPE, Headers.Values.ACCEPT_VALUE)
      .addHeader(Headers.Keys.AUTHORIZATION, BuildConfig.API_TOKEN)

    chain.proceed(newRequest.build())
  }

  @Provides
  @Singleton
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun provideOkHttpClientBuilder(
    headersInterceptor: Interceptor,
    loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
      .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
      .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
      .addInterceptor(headersInterceptor)

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(loggingInterceptor)
    }

    return builder
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    okHttpClientBuilder: OkHttpClient.Builder
  ): OkHttpClient = okHttpClientBuilder.build()

  @Provides
  @Singleton
  fun provideRetrofitBuilder(
    gson: Gson,
    apiCallAdapterFactory: ApiCallAdapterFactory
  ): Retrofit.Builder = Retrofit
    .Builder()
    .addCallAdapterFactory(apiCallAdapterFactory)
    .addConverterFactory(GsonConverterFactory.create(gson))

  @Provides
  @Singleton
  fun provideRetrofit(
    retrofitBuilder: Retrofit.Builder,
    okHttpClient: OkHttpClient
  ): Retrofit = retrofitBuilder
    .client(okHttpClient)
    .baseUrl(BuildConfig.BASE_URL)
    .build()
}