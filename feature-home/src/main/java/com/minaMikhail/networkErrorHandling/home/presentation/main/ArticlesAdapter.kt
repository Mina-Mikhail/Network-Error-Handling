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

package com.minaMikhail.networkErrorHandling.home.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minaMikhail.networkErrorHandling.home.databinding.ItemArticleBinding
import com.minaMikhail.networkErrorHandling.home.domain.model.Article
import com.minaMikhail.networkErrorHandling.ui.extensions.addRippleEffect

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ParticipationStatusViewHolder>(DIFF_CALLBACK) {

  companion object {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
      override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }

      override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ParticipationStatusViewHolder = ParticipationStatusViewHolder(
    ItemArticleBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
  )

  override fun onBindViewHolder(
    holder: ParticipationStatusViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }

  inner class ParticipationStatusViewHolder(
    private val binding: ItemArticleBinding
  ) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: Article

    fun bind(item: Article) {
      currentItem = item

      binding.apply {
        container.addRippleEffect()
        tvTitle.text = currentItem.title
        tvDescription.text = currentItem.description
        tvDate.text = currentItem.publishedAt
      }
    }
  }
}