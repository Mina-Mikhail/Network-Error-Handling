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

package com.minaMikhail.networkErrorHandling.home.data.mapper

import com.minaMikhail.networkErrorHandling.home.data.entity.ArticleDto
import com.minaMikhail.networkErrorHandling.home.domain.model.Article
import com.minaMikhail.networkErrorHandling.utils.mappers.DomainMapper
import com.minaMikhail.networkErrorHandling.utils.providers.timeUtilsProvider.DateTimeFormat
import com.minaMikhail.networkErrorHandling.utils.providers.timeUtilsProvider.TimeUtilsProvider
import javax.inject.Inject

class ArticleDtoMapper @Inject constructor(
  private val timeUtilsProvider: TimeUtilsProvider
) : DomainMapper<ArticleDto?, Article?> {

  override fun mapToDomain(model: ArticleDto?): Article? {
    return model?.let {
      Article(
        author = it.author,
        title = it.title,
        description = it.description,
        urlToImage = it.urlToImage,
        content = it.content,
        publishedAt = timeUtilsProvider.changeDateFormat(
          date = it.publishedAt,
          oldFormat = DateTimeFormat.FULL_DATE_TIME_FORMAT,
          newFormat = DateTimeFormat.UI_DATE_TIME_FORMAT
        )
      )
    } ?: run {
      null
    }
  }

  override fun mapFromDomain(domainModel: Article?): ArticleDto? {
    return domainModel?.let {
      ArticleDto(
        author = it.author,
        title = it.title,
        description = it.description,
        urlToImage = it.urlToImage,
        content = it.content,
        publishedAt = it.publishedAt
      )
    } ?: run {
      null
    }
  }

  fun mapToDomainList(modelList: List<ArticleDto>?): List<Article> {
    return modelList?.let {
      it.map { articleDto -> requireNotNull(mapToDomain(articleDto)) }
    } ?: run {
      emptyList()
    }
  }
}