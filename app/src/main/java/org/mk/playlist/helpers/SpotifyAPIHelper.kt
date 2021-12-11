package org.mk.playlist.helpers

import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import org.mk.playlist.main.MainApp
import timber.log.Timber

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