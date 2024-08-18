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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minaMikhail.networkErrorHandling.home.domain.useCase.GetArticlesUseCase
import com.minaMikhail.networkErrorHandling.network.utils.NetworkResult
import com.minaMikhail.networkErrorHandling.utils.coroutineDispatchers.IoDispatcher
import com.minaMikhail.networkErrorHandling.utils.coroutineDispatchers.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MainViewModel @Inject constructor(
  @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
  private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

  private val _mainScreenState = MutableStateFlow<MainScreenState>(MainScreenState.Default)
  val mainScreenState: StateFlow<MainScreenState> get() = _mainScreenState

  fun handleIntent(intent: MainScreenIntent) {
    when (intent) {
      is MainScreenIntent.LoadData -> {
        getArticles(
          pageSize = intent.pageSize,
          page = intent.page,
          query = intent.query
        )
      }
    }
  }

  private fun getArticles(
    pageSize: Int,
    page: Int,
    query: String
  ) = viewModelScope.launch(ioDispatcher) {
    withContext(mainDispatcher) {
      _mainScreenState.emit(MainScreenState.Loading)
    }

    when (val result = getArticlesUseCase(pageSize, page, query)) {
      is NetworkResult.Success -> {
        _mainScreenState.emit(
          MainScreenState.Success(
            articles = result.data
          )
        )
      }

      is NetworkResult.Failure -> {
        _mainScreenState.emit(
          MainScreenState.Error(
            errorResponse = result.errorBody,
            throwable = result.throwable,
            code = result.code,
            errorType = result.errorType,
            errorMessage = result.errorMessage
          )
        )
      }
    }
  }
}