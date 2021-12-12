package org.mk.playlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.BuildConfig
import org.mk.playlist.R
import org.mk.playlist.databinding.ActivityMainBinding
import org.mk.playlist.helpers.createTokenRequest
import org.mk.playlist.helpers.getArtistByID
import org.mk.playlist.helpers.getArtistTopTracks
import org.mk.playlist.main.MainApp
import timber.log.Timber
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {
    private val clientID = BuildConfig.SPOTIFY_CLIENT_ID
    private val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET
    private lateinit var app: MainApp
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val queue = Volley.newRequestQueue(this)
        app = application as MainApp
        val postRequest = createTokenRequest(clientID, clientSecret, app)
        queue?.add(postRequest)
        binding.getToken.setOnClickListener() {
            val getTopTracksRequest = getArtistTopTracks("6PfSUFtkMVoDkx4MQkzOi3", app.accessToken, app.tracks)
            queue?.add(getTopTracksRequest)
            for(track in app.tracks.findAll()){
                i("$track")
            }
        }
    }

}

