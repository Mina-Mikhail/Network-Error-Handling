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
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.minaMikhail.networkErrorHandling.ui.navigation.Navigator
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(
  val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

  // To navigate between modules(Activities)
  @Inject
  lateinit var navigator: Navigator.Provider

  // View Binding Instance
  private var _binding: VB? = null
  open val binding get() = checkNotNull(_binding)

  private val loadingDialogDelegate = lazy { LoadingDialog(this) }
  private val loadingDialog by loadingDialogDelegate

  @Inject
  lateinit var resourceProvider: ResourceProvider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    _binding = bindingInflater(layoutInflater)

    setContentView(binding.root)

    binding.initializeUI()
  }

  override fun onResume() {
    super.onResume()

    binding.registerListeners()

    setUpObservers()
  }

  override fun onPause() {
    binding.unRegisterListeners()

    super.onPause()
  }

  open fun VB.registerListeners() {}

  open fun setUpObservers() {}

  open fun VB.unRegisterListeners() {}

  open fun VB.initializeUI() {}

  fun showLoading(hint: String? = null) = loadingDialog.showDialog(hint)

  fun hideLoading() = loadingDialog.hideDialog()

  override fun onDestroy() {
    _binding = null
    if (loadingDialogDelegate.isInitialized()) {
      loadingDialog.clean()
    }

    super.onDestroy()
  }
}