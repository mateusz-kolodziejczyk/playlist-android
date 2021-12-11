package org.mk.playlist.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    var accessToken = ""

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Playlist App started")
//        placemarks.add(PlacemarkModel("One", "About one..."))
//        placemarks.add(PlacemarkModel("Two", "About two..."))
//        placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}