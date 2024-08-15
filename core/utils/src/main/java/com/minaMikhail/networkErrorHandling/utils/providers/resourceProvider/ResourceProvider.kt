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

package com.minaMikhail.networkErrorHandling.utils.providers.resourceProvider

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Spannable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider {

  fun getColor(@ColorRes resId: Int): Int

  fun getColorStateList(@ColorRes resId: Int): ColorStateList

  fun getDrawable(@DrawableRes resId: Int): Drawable

  fun getText(@StringRes resId: Int, vararg values: Any): String

  fun getSpannableText(
    @StringRes beforeTextResId: Int? = null,
    @StringRes afterTextResId: Int? = null,
    spanText: String,
    @ColorRes spanColor: Int,
    addUnderline: Boolean = false,
    addCenterLine: Boolean = false,
    isSpanBold: Boolean = false,
    spanClickAction: (() -> Unit)? = null
  ): Spannable
}