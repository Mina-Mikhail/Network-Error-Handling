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

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.minaMikhail.networkErrorHandling.ui.databinding.ProgressDialogBinding
import com.minaMikhail.networkErrorHandling.ui.extensions.hide
import com.minaMikhail.networkErrorHandling.ui.extensions.show

class LoadingDialog(
  private val activity: Activity?
) {

  private var _binding: ProgressDialogBinding? = null
  private val binding get() = checkNotNull(_binding)

  private var _dialog: Dialog? = null
  private val dialog get() = checkNotNull(_dialog)

  init {
    initViewBinding()
    initDialog()
  }

  private fun initViewBinding() {
    _binding = ProgressDialogBinding.inflate(LayoutInflater.from(activity))
  }

  private fun initDialog() {
    _dialog = Dialog(checkNotNull(activity)).apply {
      window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      setContentView(binding.root)
      setCancelable(false)
      setCanceledOnTouchOutside(false)
    }
  }

  fun showDialog(hint: String? = null) {
    if (activity != null && !activity.isFinishing) {
      hideDialog()

      bindHintText(hint)
      dialog.show()
    }
  }

  private fun bindHintText(hint: String?) {
    hint?.let {
      if (it.isNotEmpty()) {
        apply {
          binding.tvHint.show()
          binding.tvHint.text = it
        }
      } else {
        binding.tvHint.hide()
      }
    } ?: run { binding.tvHint.hide() }
  }

  fun hideDialog() {
    if (activity != null && !activity.isFinishing && dialog.isShowing) {
      dialog.dismiss()
    }
  }

  fun clean() {
    dialog.dismiss()
    _binding = null
    _dialog = null
  }
}