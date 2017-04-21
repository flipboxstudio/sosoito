[![](https://jitpack.io/v/flipboxstudio/sosoito.svg)](https://jitpack.io/#flipboxstudio/sosoito)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Sosoito-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/5518)

# Sosoito
Sosoito is layout library for Android that can be used to simplify basic needs for loading/progression state.

[Demo / Sample Project](https://github.com/flipboxstudio/mvvm-starter)


## Installation
Sosoito can installed by adding the following dependency to your build.gradle file:
```
repositories {
  jcenter()
  maven { url "https://jitpack.io" }
}
```
```
dependencies {
  compile 'com.github.flipboxstudio:sosoito:latest-release'
}
```
## Usage
###### Basic
You can start using sosoito by add your preferable layout in your layout.xml:
```
<id.co.flipbox.sosoito.LoadingLayout
        android:id="@+id/loadingLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

</id.co.flipbox.sosoito.LoadingLayout>
```
Then, access it from your activity/fragment to use it's feature:
```
...
LoadingLayout loadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
loadingLayout.showLoading(true);
...
```

###### Available Methods
Loading (in) layout
* `showLoading(boolean)`
* `showLoading(boolean, message)`

Progress Dialog
* `showProgressDialog()`
* `showProgressDialog(message)`
* `setProgressDialogMessage(message)`
* `hideProgressDialog()`
