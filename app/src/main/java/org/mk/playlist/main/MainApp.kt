package org.mk.playlist.main

import android.app.Application
import org.mk.playlist.models.data_stores.TrackStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    var accessToken = ""
    val tracks = TrackStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Playlist App started")
//        placemarks.add(PlacemarkModel("One", "About one..."))
//        placemarks.add(PlacemarkModel("Two", "About two..."))
//        placemarks.add(PlacemarkModel("Three", "About three..."))
    }
}