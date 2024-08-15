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

package com.minaMikhail.networkErrorHandling.home.data.entity

import com.google.gson.annotations.SerializedName

data class ArticleDto(
  @SerializedName("author") val author: String? = null,
  @SerializedName("title") val title: String? = null,
  @SerializedName("description") val description: String? = null,
  @SerializedName("urlToImage") val urlToImage: String? = null,
  @SerializedName("content") val content: String? = null,
  @SerializedName("publishedAt") val publishedAt: String? = null
)