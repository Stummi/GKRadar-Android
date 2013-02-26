GKRadar-Android
===============

New Android-App for the "Giftköder-Radar"-Project: http://www.giftkoeder-radar.com

Prerequisites
-------------
- installed Maven
- installed Android SDK with google APIs, min. API-Level 8
- Git ;)

Setup the android sdk deployer
------------------------------
The project depends on the google map api which is not available through public maven repositories, 
so you have to install the dependencies to you local maven repository yourself.
The android-sdk-deployer is a tool which does this for you
```sh
git clone https://github.com/mosabua/maven-android-sdk-deployer
cd maven-android-sdk-deployer
mvn install
```
Setup
-----
- Checkout the project with git
- Obtain an API-Key for google maps v1: https://developers.google.com/maps/documentation/android/v1/mapkey
- Obtain an API-Key for the "Giftköder-Radar": http://www.giftkoeder-radar.com/entwickler/registrieren.html
- Fill out the res/values/api.xml
- build the app with:

```sh
cd GKRadar-Android
mvn clean compile android:dex android:apk
```

Now you find the ready-to-install apk-file in the target-folder

If you have a device plugged in on debug mode and the adb-server running, you can directly deploy the app with:
```sh
mvn android:deploy
```

What Works
----------
- Code provides the basic infrastructure to work with the API and displaying 
  the results to the Map
- Obtain the User-Location automatically

Roadmap
-------
- Widget
- Configuration dialog

Screenshots
-----------
<img src="/doc/screenshot/screenshot1.png">
<img src="/doc/screenshot/screenshot2.png">
