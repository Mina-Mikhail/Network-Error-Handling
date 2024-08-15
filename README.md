<h1 align="center">
Api Error Handling in Clean Architecture 
</h1>

<p align="center">
A sample app that demonstrates usage of Retrofit Adapters to handle api response and exceptions within a clean architecture app.
</p>

<div align="center">
<a name="code_factor" href="https://www.codefactor.io/repository/github/mina-mikhail/Network-Error-Handling">
  <img src="https://www.codefactor.io/repository/github/mina-mikhail/Network-Error-Handling/badge?style=for-the-badge">
</a>  
<a name="platform">
  <img src="https://img.shields.io/badge/Platform-Android-success?style=for-the-badge">
</a>
<a name="language">
  <img src="https://img.shields.io/badge/Language-Kotlin---?style=for-the-badge">
</a>
<a name="stars">
  <img src="https://img.shields.io/github/stars/Mina-Mikhail/Network-Error-Handling?style=for-the-badge"></a>
<a name="forks">
  <img src="https://img.shields.io/github/forks/Mina-Mikhail/Network-Error-Handling?logoColor=green&style=for-the-badge">
</a>
<a name="contributions">
  <img src="https://img.shields.io/github/contributors/Mina-Mikhail/Network-Error-Handling?logoColor=green&style=for-the-badge">
</a>
<a name="last_commit">
  <img src="https://img.shields.io/github/last-commit/Mina-Mikhail/Network-Error-Handling?style=for-the-badge">
</a>
<a name="issues">
  <img src="https://img.shields.io/github/issues-raw/Mina-Mikhail/Network-Error-Handling?style=for-the-badge">
</a>
<a name="license">
  <img src="https://img.shields.io/github/license/sadanandpai/javascript-code-challenges?style=for-the-badge">
</a>
<a name="linked_in" href="https://www.linkedin.com/in/minasamirgerges/">
  <img src="https://img.shields.io/badge/Support-Recommed%2FEndorse%20me%20on%20Linkedin-yellow?style=for-the-badge&logo=linkedin" alt="Recommend me on LinkedIn"/>
</a>
</div>


:point_right: Clean Architecture:
-----------------
<div align="center">
<img src="https://github.com/Mina-Mikhail/Network-Error-Handling/blob/master/images/architecture.png">
</div>


:point_right: Domain & Data Layer:
-----------------
<div align="center">
<img src="https://github.com/Mina-Mikhail/Network-Error-Handling/blob/master/images/data_layer.png">
</div>


:point_right: Presentation Layer:
-----------------
<div align="center">
<img src="https://github.com/Mina-Mikhail/Network-Error-Handling/blob/master/images/ui_layer.png">
</div>


:point_right: Architecture:
-----------------
- Following Clean Architecture.
- MVI Architecture.
- Repository Pattern.
- Mapper.
- Use Cases.
- Applying SOLID principles, each class has a single job with separation of concerns by making classes independent
  of each other and communicating with interfaces.
- Using Kotlin-KTS & Gradle Version Catalog to handle project dependencies.


:point_right: Tech Stack & Libraries:
-----------------
- Navigation component - For navigation graph for navigating and replacing screens/fragments
- ViewBinding - Allows to more easily write code that interacts with views and replaces ```findViewById```.
- ViewModel - UI related data holder, lifecycle aware.
- Flow & StateFlow - Build data objects that notify views when the underlying database changes.
- Dagger-Hilt - For dependency injection. Object creation and scoping is handled by Hilt.
- Kotlin Coroutines - For managing background threads with simplified code and reducing needs for callbacks
- Retrofit2 & OkHttp3 - To make REST requests to the web service integrated.
- Gson - For parsing json objects.


:point_right: Project Modules:
-----------------
- Here is more information about project modules :
  - App - The main app module, contains splash screen and handle navigation across the app.
  - Core : Network - Handles network calls with it's errors.
  - Core : UI - Contains classes related to UI like BaseActivity and BaseFragment and other useful ui extensions.
  - Core : Utils - Contains utility classes and some extensoins related to business usage.
  - Feature : Home - Copntains data, domain, presentation for home feature.


:point_right: Code Style:
-----------
- Following official kotlin code style


:point_right: Local Development:
-----------
- Here are some useful Gradle commands for executing this example:
  - `./gradlew clean` - Deletes build directory.


:point_right: Contributing to Project:
-----------
- Just fork this repository and contribute back using pull requests.
- Any contributions, large or small, major features, bug fixes, are welcomed and appreciated but will be thoroughly reviewed .


:point_right: Find this project useful ? :heart:
-----------
- Support it by clicking the :star: button on the upper right of this page. :v:


:point_right: Stargazers: :star:
-----------
[![Stargazers repo roster for @sadanandpai/javascript-code-challenges](https://reporoster.com/stars/Mina-Mikhail/Network-Error-Handling)](https://github.com/Mina-Mikhail/Network-Error-Handling/stargazers)


:point_right: Forkers: :hammer_and_pick:
-----------
[![Forkers repo roster for @sadanandpai/javascript-code-challenges](https://reporoster.com/forks/Mina-Mikhail/Network-Error-Handling)](https://github.com/Mina-Mikhail/Network-Error-Handling/network/members)


:point_right: Donation:
-----------
If this project help you reduce time to develop, you can give me a cup of coffee :) 

<a href="https://www.buymeacoffee.com/mina.mikhail" target="_blank"><img src="https://bmc-cdn.nyc3.digitaloceanspaces.com/BMC-button-images/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>



:warning: License:
--------
```
   Copyright (C) 2024 MINA MIKHAIL PRIVATE LIMITED

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```