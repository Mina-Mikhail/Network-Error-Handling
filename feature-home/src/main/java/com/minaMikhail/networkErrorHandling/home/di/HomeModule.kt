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

package com.minaMikhail.networkErrorHandling.home.di

import com.minaMikhail.networkErrorHandling.home.data.dataSource.remote.HomeServices
import com.minaMikhail.networkErrorHandling.home.data.repository.HomeRepositoryImpl
import com.minaMikhail.networkErrorHandling.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProvidingHomeModule {

  @Provides
  @Singleton
  fun provideApiServices(
    retrofit: Retrofit
  ): HomeServices = retrofit.create(HomeServices::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
fun interface BindingHomeModule {

  @Binds
  @Singleton
  fun bindRepository(
    homeRepositoryImpl: HomeRepositoryImpl
  ): HomeRepository
}