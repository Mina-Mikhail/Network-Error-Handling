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

package com.minaMikhail.networkErrorHandling.network.enums

sealed interface NetworkError {
  enum class Network : NetworkError {
    UNAUTHORIZED,
    GATEWAY_TIMEOUT,
    NO_INTERNET_CONNECTION,
    SERVER_ERROR
  }

  enum class Other : NetworkError {
    ILLEGAL_JSON_BODY_ERROR,
    OTHER_ERROR
  }
}