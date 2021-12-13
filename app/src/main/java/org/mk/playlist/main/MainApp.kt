package org.mk.playlist.main

import android.app.Application
import com.android.volley.toolbox.Volley
import org.mk.playlist.BuildConfig
import org.mk.playlist.helpers.createTokenRequest
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
        // App gets a new spotify access token each time it launches
        val queue = Volley.newRequestQueue(this)
        val postRequest = createTokenRequest(BuildConfig.SPOTIFY_CLIENT_ID, BuildConfig.SPOTIFY_CLIENT_SECRET, this)
        queue?.add(postRequest)
    }
}