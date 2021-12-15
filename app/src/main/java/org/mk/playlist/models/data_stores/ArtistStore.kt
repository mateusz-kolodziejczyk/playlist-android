package org.mk.playlist.models.data_stores

import android.content.Context
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
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
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
        val playlistToUpdate = artists[obj.id]
        // If it finds the correct track by id, update it.
        playlistToUpdate?.let{
            artists[obj.id] = obj
            serialize()
        }
    }
    override fun add(obj: ArtistModel){
        artists[obj.id] = obj
        serialize()
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