# WeatherApp

This repository contains a sample Weather app that implements MVVM architecture using ViewModel, Repositories, Room and LiveData.

<img src="https://github.com/kk-amit/WeatherApp/blob/development/screenshot/app.gif">

**The app has following packages:**

1. **database**: It contains all the database related classes i.e. Room DB, Dao and entity.
2. **service**: It contains all the modle classes and repository.
3. **view**: It contains all the view classes.
3. **viewmodel**: It contains all the viewmodel components.

**World Weather API Security:**

In World Weather app, I have added NDK based implementation to save the World Weather API.  In the native languages like in C or C++, the Android Native Development Kit(NDK) compiles the code to .so file. The benefit with this .so file is that it contains binary numbers i.e. in the form of 0s and 1s. So, even after reverse engineering, you will get 0s and 1s and it is very difficult to identify what is written in the form of 0s and 1s.

[Securing API Keys using Android NDK (Native Development Kit)](https://blog.mindorks.com/securing-api-keys-using-android-ndk)