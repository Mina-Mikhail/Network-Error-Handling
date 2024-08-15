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
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.minaMikhail.networkErrorHandling.ui.navigation.Navigator
import com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider.ResourceProvider
import java.util.Locale
import javax.inject.Inject

abstract class BasicFragment<VB : ViewBinding>(
  open val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {

  // To navigate between modules(Activities)
  @Inject
  lateinit var navigator: Navigator.Provider

  // View Binding Instance
  private var _binding: VB? = null
  open val binding get() = checkNotNull(_binding)

  private val loadingDialogDelegate = lazy { LoadingDialog(activity) }
  private val loadingDialog by loadingDialogDelegate

  val currentLanguage: String
    get() = Locale.getDefault().language

  @Inject
  lateinit var resourceProvider: ResourceProvider

  override fun onResume() {
    super.onResume()

    binding.registerListeners()
  }

  override fun onPause() {
    binding.unRegisterListeners()

    super.onPause()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = bindingInflater(layoutInflater)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    getFragmentArguments()
    binding.initializeUI()
    setupObservers()
  }

  open fun VB.registerListeners() {}

  open fun VB.unRegisterListeners() {}

  open fun getFragmentArguments() {}

  open fun VB.initializeUI() {}

  open fun setupObservers() {}

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