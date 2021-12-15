package org.mk.playlist.models.data_stores

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.playlist.helpers.exists
import org.mk.playlist.helpers.read
import org.mk.playlist.helpers.write
import org.mk.playlist.models.PlaylistModel
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

private const val PLAYLIST_JSON_FILE = "playlist.json"

class PlaylistStore(private val context: Context) : DataStore<PlaylistModel> {
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
    private val listType: Type = object : TypeToken<HashMap<UUID , PlaylistModel>>() {}.type
    private var playlists: HashMap<UUID, PlaylistModel> = HashMap()
    
    init {
        if (exists(context, PLAYLIST_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<PlaylistModel> {
        return ArrayList(playlists.values)
    }
    override fun create(obj: PlaylistModel){

    }
    override fun update(obj: PlaylistModel){
        val playlistToUpdate = playlists[obj.id]
        // If it finds the correct track by id, update it.
        playlistToUpdate?.let{
            playlists[obj.id] = obj
            serialize()
        }
    }
    override fun add(obj: PlaylistModel){
        playlists[obj.id] = obj
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(playlists, listType)
        write(context, PLAYLIST_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, PLAYLIST_JSON_FILE)
        playlists = gsonBuilder.fromJson(jsonString, listType)
    }
}