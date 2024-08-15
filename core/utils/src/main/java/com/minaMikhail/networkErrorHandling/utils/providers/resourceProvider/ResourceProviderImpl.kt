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

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : ResourceProvider {

  override fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

  override fun getColorStateList(@ColorRes resId: Int): ColorStateList =
    AppCompatResources.getColorStateList(context, resId)

  override fun getDrawable(@DrawableRes resId: Int) = checkNotNull(ContextCompat.getDrawable(context, resId))

  override fun getText(@StringRes resId: Int, vararg values: Any) = context.getString(resId, *values)

  override fun getSpannableText(
    @StringRes beforeTextResId: Int?,
    @StringRes afterTextResId: Int?,
    spanText: String,
    @ColorRes spanColor: Int,
    addUnderline: Boolean,
    addCenterLine: Boolean,
    isSpanBold: Boolean,
    spanClickAction: (() -> Unit)?
  ): Spannable {
    val beforeString = beforeTextResId?.let { context.getString(it) } ?: run { "" }
    val afterString = afterTextResId?.let { context.getString(it) } ?: run { "" }

    val finalMessage = "$beforeString $spanText $afterString"

    val spannable = SpannableString(finalMessage).apply {
      setSpan(
        object : ClickableSpan() {
          override fun onClick(textView: View) {
            spanClickAction?.invoke()
          }
        },
        finalMessage.indexOf(spanText),
        finalMessage.indexOf(spanText) + spanText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
      )

      setSpan(
        ForegroundColorSpan(getColor(spanColor)),
        finalMessage.indexOf(spanText),
        finalMessage.indexOf(spanText) + spanText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
      )

      if (addUnderline) {
        setSpan(
          UnderlineSpan(),
          finalMessage.indexOf(spanText),
          finalMessage.indexOf(spanText) + spanText.length,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }

      if (addCenterLine) {
        setSpan(
          StrikethroughSpan(),
          finalMessage.indexOf(spanText),
          finalMessage.indexOf(spanText) + spanText.length,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }

      if (isSpanBold) {
        setSpan(
          StyleSpan(Typeface.BOLD),
          finalMessage.indexOf(spanText),
          finalMessage.indexOf(spanText) + spanText.length,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }
    }

    return spannable
  }
}