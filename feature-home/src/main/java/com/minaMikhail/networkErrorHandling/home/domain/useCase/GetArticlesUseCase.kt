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

package com.minaMikhail.networkErrorHandling.home.domain.useCase

import com.minaMikhail.networkErrorHandling.home.data.dto.ArticlesErrorResponse
import com.minaMikhail.networkErrorHandling.home.domain.model.Article
import com.minaMikhail.networkErrorHandling.home.domain.repository.HomeRepository
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
  private val repository: HomeRepository
) {

  suspend operator fun invoke(
    pageSize: Int,
    page: Int,
    query: String
  ): NetworkResult<List<Article>, ArticlesErrorResponse> {
    return repository.getArticles(pageSize, page, query)
  }
}