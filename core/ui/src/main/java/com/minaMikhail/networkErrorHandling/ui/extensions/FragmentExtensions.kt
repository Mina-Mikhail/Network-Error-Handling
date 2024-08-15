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

package com.minaMikhail.networkErrorHandling.ui.extensions

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.minaMikhail.networkErrorHandling.ui.R
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun Fragment.handleApiError(
  noDataAction: (() -> Unit)? = null,
  noInternetAction: (() -> Unit)? = null
) {
  (this.requireActivity() as? AppCompatActivity)?.handleApiError(noDataAction, noInternetAction)
}

fun Fragment.showMessage(message: String?) {
  Toast.makeText(
    requireContext(),
    message ?: resources.getString(R.string.some_error),
    Toast.LENGTH_SHORT
  ).show()
}

fun Fragment.showError(
  title: String? = null,
  message: String?
) = (this.requireActivity() as? AppCompatActivity)?.showErrorAlert(title, message)

fun Fragment.onBackPressedCustomAction(action: () -> Unit) {
  requireActivity().onBackPressedDispatcher.addCallback(
    viewLifecycleOwner,
    object : OnBackPressedCallback(true) {
      override
      fun handleOnBackPressed() {
        action()
      }
    }
  )
}

fun <T> Fragment.observe(liveData: LiveData<T>, block: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner) {
    block.invoke(it)
  }
}

fun <T> Fragment.collect(sharedFlow: SharedFlow<T>, block: (T) -> Unit) {
  viewLifecycleOwner.lifecycleScope.launch {
    sharedFlow
      .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED) // Same as repeatOnLifeCycle()
      .collect {
        block(it)
      }
  }
}