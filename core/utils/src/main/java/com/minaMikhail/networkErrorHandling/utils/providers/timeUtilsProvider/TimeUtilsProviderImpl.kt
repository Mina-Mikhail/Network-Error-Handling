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

package com.minaMikhail.networkErrorHandling.utils.providers.timeUtilsProvider

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TimeUtilsProviderImpl @Inject constructor() : TimeUtilsProvider {

  override fun currentTimeMillis(): Long = System.currentTimeMillis()

  override fun changeDateFormat(
    date: String?,
    oldFormat: DateTimeFormat,
    newFormat: DateTimeFormat
  ): String? {
    date?.let {
      val oldFormatView = SimpleDateFormat(oldFormat.format, getLocale())
      val newFormatView = SimpleDateFormat(newFormat.format, getLocale())

      var dateObj: Date? = null

      try {
        dateObj = oldFormatView.parse(date)
      } catch (e: ParseException) {
        e.printStackTrace()
      }

      return if (dateObj != null) {
        newFormatView.format(dateObj)
      } else {
        null
      }
    } ?: run {
      return null
    }
  }

  private fun getLocale(): Locale? {
    return when (Locale.getDefault().language) {
      "en" -> {
        Locale.ENGLISH
      }

      "ar" -> {
        Locale("ar")
      }

      else -> {
        Locale.ENGLISH
      }
    }
  }
}