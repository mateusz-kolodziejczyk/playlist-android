package org.mk.playlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.mk.playlist.BuildConfig
import org.mk.playlist.R
import org.mk.playlist.helpers.createTokenRequest
import timber.log.Timber
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {
    // A lot of the api code is from https://stackoverflow.com/questions/65509624/unable-to-obtain-a-spotify-access-token-by-creating-a-volley-post-request-in-kot
    val clientID = BuildConfig.SPOTIFY_CLIENT_ID
    val clientSecret = BuildConfig.SPOTIFY_CLIENT_SECRET


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        val queue = Volley.newRequestQueue(this)
        i("Placemark started please work")
        queue?.add(postRequest)
    }

    val postRequest = createTokenRequest(clientID, clientSecret)
}

