package org.mk.playlist.models.data_stores

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.playlist.helpers.exists
import org.mk.playlist.helpers.read
import org.mk.playlist.helpers.write
import org.mk.playlist.models.ArtistModel
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

private const val ARTIST_JSON_FILE = "artist.json"

class ArtistStore(private val context: Context) : DataStore<ArtistModel> {
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
        .registerTypeAdapter(Uri::class.java, URIParser())
        .create()
    private val listType: Type = object : TypeToken<HashMap<String , ArtistModel>>() {}.type
    private var artists: HashMap<String, ArtistModel> = HashMap()

    init {
        if (exists(context, ARTIST_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<ArtistModel> {
        return ArrayList(artists.values)
    }
    override fun create(obj: ArtistModel){

    }
    override fun update(obj: ArtistModel){
        val artistToUpdate = artists[obj.id]
        // If it finds the correct track by id, update it.
        artistToUpdate?.let{
            artists[obj.id] = obj
            serialize()
        }
    }
    override fun add(obj: ArtistModel){
        val existingArtist = artists[obj.id]
        existingArtist?.let {
            // If the new artist has no image, set it to the existing image url
            if(obj.imageURL == Uri.EMPTY){
                obj.imageURL = it.imageURL
            }
            // Same with the url
            if(obj.url == Uri.EMPTY){
                obj.url = it.url
            }
        }
        artists[obj.id] = obj
        serialize()
    }

    override fun delete(obj: ArtistModel) {
        artists.remove(obj.id)
        serialize()
    }

    fun findOne(obj: ArtistModel): ArtistModel?{
        return artists[obj.id]
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(artists, listType)
        write(context, ARTIST_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, ARTIST_JSON_FILE)
        artists = gsonBuilder.fromJson(jsonString, listType)
    }
}