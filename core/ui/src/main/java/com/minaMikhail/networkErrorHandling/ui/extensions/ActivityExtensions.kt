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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minaMikhail.networkErrorHandling.ui.R
import com.tapadoo.alerter.Alerter
import java.util.concurrent.TimeUnit

fun AppCompatActivity.handleApiError(
  noDataAction: (() -> Unit)? = null,
  noInternetAction: (() -> Unit)? = null
) {
//  when (failure.failureStatus) {
//    FailureStatus.NO_INTERNET -> {
//      noInternetAction?.invoke()
//      showNoInternetAlert()
//    }
//
//    FailureStatus.API_FAIL, FailureStatus.OTHER, FailureStatus.SERVER_SIDE_EXCEPTION -> {
//      noDataAction?.invoke()
//      showErrorAlert(
//        title = resources.getString(R.string.error),
//        message = failure.message ?: resources.getString(R.string.some_error)
//      )
//    }
//
//    FailureStatus.TOKEN_EXPIRED -> {
//    }
//
//    FailureStatus.EMPTY_LOCAL_LIST -> {
//      noDataAction?.invoke()
//    }
//
//    FailureStatus.LOCAL_ITEM_NOT_FOUND -> {
//      noDataAction?.invoke()
//    }
//
//    FailureStatus.ROW_ALREADY_EXIST -> {
//    }
//  }
}

fun AppCompatActivity.showErrorAlert(title: String?, message: String?) {
  Alerter.create(this)
    .setTitle(title ?: getString(R.string.error))
    .setText(message ?: getString(R.string.some_error))
    .setIcon(R.drawable.ic_help)
    .setBackgroundColorRes(R.color.red)
    .enableClickAnimation(true)
    .setDuration(TimeUnit.SECONDS.toMillis(7))
    .enableSwipeToDismiss()
    .show()
}

fun <T> Activity.openActivity(
  targetScreen: Class<T>,
  clearStack: Boolean = false,
  closePreviousScreen: Boolean = false,
  data: Pair<String, Int>? = null,
  bundleData: Bundle? = null
) {
  val intent = Intent(this, targetScreen).apply {
    if (clearStack) {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
  }

  data?.let {
    intent.putExtra(it.first, it.second)
  }

  bundleData?.let {
    intent.putExtras(it)
  }

  startActivity(intent)
  if (clearStack || closePreviousScreen) {
    finish()
  }
}