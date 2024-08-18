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

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.minaMikhail.networkErrorHandling.home.databinding.FragmentMainBinding
import com.minaMikhail.networkErrorHandling.network.enums.NetworkError
import com.minaMikhail.networkErrorHandling.ui.base.BaseFragment
import com.minaMikhail.networkErrorHandling.ui.extensions.collect
import com.minaMikhail.networkErrorHandling.ui.extensions.hide
import com.minaMikhail.networkErrorHandling.ui.extensions.onBackPressedCustomAction
import com.minaMikhail.networkErrorHandling.ui.extensions.show
import com.minaMikhail.networkErrorHandling.ui.extensions.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(
  FragmentMainBinding::inflate
) {

  override val viewModel: MainViewModel by viewModels()

  private val articlesAdapter: ArticlesAdapter by lazy {
    ArticlesAdapter()
  }

  override fun FragmentMainBinding.initializeUI() {
    setupRecyclerView()

    loadData()

    onBackPressedCustomAction {
      requireActivity().finish()
    }
  }

  private fun setupRecyclerView() {
    binding.includedList.recyclerView.apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
      adapter = articlesAdapter
    }
  }

  private fun loadData() {
    viewModel.handleIntent(
      MainScreenIntent.LoadData(
        pageSize = 10,
        page = 1,
        query = "Bitcoin"
      )
    )
  }

  override fun setupObservers() {
    collect(viewModel.mainScreenState) {
      when (it) {
        MainScreenState.Default -> {}

        MainScreenState.Loading -> {
          showDataLoading()
        }

        is MainScreenState.Success -> {
          articlesAdapter.submitList(it.articles)
          showData()
        }

        is MainScreenState.Error -> {
          val errorTitle = "Error Happens"

          val errorMessage = StringBuilder()
            .append("Error Code : ")
            .append(it.code ?: "")
            .append("\n")
            .append("Error Type : ")
            .append(it.errorType)
            .append("\n")
            .append("Error Message : ")
            .append(it.errorMessage)

          showError(title = errorTitle, message = errorMessage.toString())

          if (it.errorType == NetworkError.Network.NO_INTERNET_CONNECTION) {
            showNoInternet()
          } else {
            showNoData()
          }
        }
      }
    }
  }

  private fun showDataLoading() {
    binding.includedList.apply {
      container.show()
      progressBar.show()
      emptyViewContainer.hide()
      internetErrorViewContainer.hide()
      recyclerView.hide()
      paginationProgressBar.hide()
    }
  }

  private fun showData() {
    binding.includedList.apply {
      recyclerView.show()
      container.hide()
      paginationProgressBar.hide()
    }
  }

  private fun showNoData() {
    binding.includedList.apply {
      container.show()
      emptyViewContainer.show()
      internetErrorViewContainer.hide()
      progressBar.hide()
      paginationProgressBar.hide()
      recyclerView.hide()
    }
  }

  private fun showNoInternet() {
    binding.includedList.apply {
      container.show()
      internetErrorViewContainer.show()
      emptyViewContainer.hide()
      progressBar.hide()
      paginationProgressBar.hide()
      recyclerView.hide()
    }
  }
}