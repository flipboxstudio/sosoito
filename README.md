# Sosoito
Sosoito is layout library for Android that can be used to simplify basic needs for loading/progressing.

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
  compile 'com.github.suhafer:sosoito:latest-release'
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
