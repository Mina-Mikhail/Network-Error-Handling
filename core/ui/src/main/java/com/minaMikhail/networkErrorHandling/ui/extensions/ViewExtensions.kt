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

import android.util.TypedValue
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.Group

fun View.show() {
  if (visibility == View.VISIBLE) return

  visibility = View.VISIBLE
  if (this is Group) {
    this.requestLayout()
  }
}

fun View.hide() {
  if (visibility == View.GONE) return

  visibility = View.GONE
  if (this is Group) {
    this.requestLayout()
  }
}

fun View.addRippleEffect() {
  this.isClickable = true
  this.isFocusable = true

  with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    foreground = AppCompatResources.getDrawable(context, resourceId)
  }
}