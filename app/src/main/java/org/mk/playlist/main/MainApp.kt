package org.mk.playlist.main

import android.app.Application
import android.media.session.MediaController
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.mk.playlist.BuildConfig
import org.mk.playlist.helpers.addSampleData
import org.mk.playlist.helpers.createTokenRequest
import org.mk.playlist.models.data_stores.ArtistStore
import org.mk.playlist.models.data_stores.PlaylistStore
import org.mk.playlist.models.data_stores.TrackStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    var accessToken = ""
    lateinit var tracks: TrackStore
    lateinit var playlists: PlaylistStore
    lateinit var artists: ArtistStore
    lateinit var queue: RequestQueue
    override fun onCreate() {
        val addSampleData = BuildConfig.SPOTIFY_CLIENT_ID.isEmpty()

        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Playlist App started")
        tracks = TrackStore(applicationContext)
        playlists = PlaylistStore(applicationContext)
        artists = ArtistStore(applicationContext)
        // App gets a new spotify access token each time it launches
        queue = Volley.newRequestQueue(this)
        val postRequest = createTokenRequest(BuildConfig.SPOTIFY_CLIENT_ID, BuildConfig.SPOTIFY_CLIENT_SECRET, this)
        queue.add(postRequest)

        // Add sample data to the app.
        if(addSampleData){
            addSampleData(tracks, artists)
        }
    }
}