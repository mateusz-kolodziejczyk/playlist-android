package org.mk.playlist.helpers

import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import timber.log.Timber

fun createTokenRequest(clientID: String, clientSecret: String) : StringRequest{
    val APIRequestURL = "https://accounts.spotify.com/api/token"
    return object : StringRequest(
        Method.POST, APIRequestURL,
        Response.Listener { response ->
            //iVolley!!.onResponse(response.toString())
            Timber.i("Got Result $response")
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