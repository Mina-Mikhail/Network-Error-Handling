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

package com.minaMikhail.networkErrorHandling.home.data.repository

import com.minaMikhail.networkErrorHandling.home.data.dataSource.remote.HomeServices
import com.minaMikhail.networkErrorHandling.home.data.dto.ArticlesErrorResponse
import com.minaMikhail.networkErrorHandling.home.data.mapper.ArticleDtoMapper
import com.minaMikhail.networkErrorHandling.home.domain.model.Article
import com.minaMikhail.networkErrorHandling.home.domain.repository.HomeRepository
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
  private val apiServices: HomeServices,
  private val articleDtoMapper: ArticleDtoMapper
) : HomeRepository {

  override suspend fun getArticles(
    pageSize: Int,
    page: Int,
    query: String
  ): NetworkResult<List<Article>, ArticlesErrorResponse> {
    return when (val response = apiServices.getArticles(pageSize, page, query)) {
      is NetworkResult.Success -> {
        NetworkResult.Success(
          data = articleDtoMapper.mapToDomainList(response.data.results)
        )
      }

      is NetworkResult.Failure -> {
        NetworkResult.Failure(
          errorBody = response.errorBody,
          throwable = response.throwable,
          code = response.code,
          errorType = response.errorType,
          errorMessage = response.errorMessage
        )
      }
    }
  }
}