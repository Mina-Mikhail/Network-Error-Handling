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

package com.minaMikhail.networkErrorHandling.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.minaMikhail.networkErrorHandling.ui.extensions.observe

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
  override val bindingInflater: (LayoutInflater) -> VB
) : BasicFragment<VB>(bindingInflater) {

  protected abstract val viewModel: VM

  /**
   * override this property if you want identify another viewGroup
   *  This will be useful in case the full screen error view overlap the root view
   * **/
  open val fullScreenViewGroup: ViewGroup
    get() = binding.root as ViewGroup

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setUpBaseObservers()
  }

  private fun setUpBaseObservers() {
    observe(viewModel.showLoading) { showLoading ->
      if (showLoading) {
        showLoading()
      } else {
        hideLoading()
      }
    }
  }
}