package org.mk.playlist.helpers

import android.util.Base64
import androidx.core.net.toUri
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import org.mk.playlist.main.MainApp
import timber.log.Timber
import timber.log.Timber.i
import org.json.JSONException
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel


// A lot of the api code is from https://stackoverflow.com/questions/65509624/unable-to-obtain-a-spotify-access-token-by-creating-a-volley-post-request-in-kot
fun createTokenRequest(clientID: String, clientSecret: String, app: MainApp) : StringRequest{
    val APIRequestURL = "https://accounts.spotify.com/api/token"
    return object : StringRequest(
        Method.POST, APIRequestURL,
        Response.Listener { response ->
            // Turn the response into a json object
            val jsonObject = JSONObject(response)
            // Access the access token and store it.
            Timber.i("Token is: ${jsonObject.getString("access_token")}")
            app.accessToken = jsonObject.getString("access_token")

        }, Response.ErrorListener { Timber.i("OOPS") }) {

        override fun getBodyContentType(): String {
            return "application/x-www-form-urlencoded; charset=UTF-8"
        }

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            val credentials = "$clientID:$clientSecret".toByteArray()
            val encodedKey = Base64.encodeToString(credentials, Base64.NO_WRAP)

            val headers: MutableMap<String, String> = HashMap()
            headers["Authorization"] = "Basic $encodedKey" // Header authorization parameter
            //headers["-H Authorization"] = "Basic $base64EncodedCredentials" // Header authorization parameter
            //headers["Content-Type"] = "application/json; charset=UTF-8"
            return headers
        }

        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["grant_type"] = "client_credentials" // Request body parameter
            //params["-d grant-type"] = "client_credentials"
            return params
        }
    }
}

fun getArtistByID(artistID: String, accessToken: String) : StringRequest{
    val APIRequestURL = "https://api.spotify.com/v1/artists/$artistID"
    return object : StringRequest(
        Method.GET, APIRequestURL,
        Response.Listener { response ->
            // Turn the response into a json object
            val artistJSON = JSONObject(response)
            Timber.i(artistJSON.getString("name"))
        }, Response.ErrorListener { Timber.i("OOPS") }) {

        override fun getBodyContentType(): String {
            return "application/json"
        }

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            // Add access token to header
            val headers: MutableMap<String, String> = HashMap()
            headers["Authorization"] = "Bearer $accessToken" // Header authorization parameter
            return headers
        }
    }
}

fun getArtistTopTracks(artistID: String, accessToken: String, app: MainApp) : JsonObjectRequest{
    val APIRequestURL = "https://api.spotify.com/v1/artists/$artistID/top-tracks?country=IE"
    return object : JsonObjectRequest(
        Method.GET, APIRequestURL, null,
        Response.Listener{ response ->
            // Process the JSON
            try {
                val tracks = response.getJSONArray("tracks")
                // Loop through the array elements
                for (i in 0 until tracks.length()) {
                    // Get current json object
                    val track = tracks.getJSONObject(i)
                    val trackID = track.getString("id")
                    val trackName = track.getString("name")
                    val spotifyURI = track.getJSONObject("external_urls").getString("spotify")
                    val artistsJSONArray = track.getJSONArray("artists")
                    val artistIDs = LinkedHashSet<String>()
                    // Add all artist IDs to the song
                    for(j in 0 until artistsJSONArray.length()){
                        val artist = artistsJSONArray.getJSONObject(j)
                        val id = artist.getString("id")
                        val artistName = artist.getString("name")

                        artistIDs.add(id)
                        app.artists.add(ArtistModel(id=id, name=artistName))
                    }
                    app.tracks.add(TrackModel(id = trackID, name = trackName, url=spotifyURI.toUri(), artistIDs=artistIDs))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { e -> i(e) }) {

        override fun getBodyContentType(): String {
            return "application/json"
        }

        @Throws(AuthFailureError::class)
        override fun getHeaders(): Map<String, String> {
            // Add access token to header
            val headers: MutableMap<String, String> = HashMap()
            headers["Authorization"] = "Bearer $accessToken" // Header authorization parameter
            return headers
        }
    }
}

