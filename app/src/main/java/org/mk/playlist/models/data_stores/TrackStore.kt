package org.mk.playlist.models.data_stores
import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.mk.playlist.helpers.exists
import org.mk.playlist.helpers.read
import org.mk.playlist.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import org.mk.playlist.models.TrackModel

const val JSON_FILE = "tracks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, URIParser())
    .create()
val listType: Type = object : TypeToken<HashMap<String , TrackModel>>() {}.type
class TrackStore(private val context: Context) : DataStore<TrackModel> {
    // ID to model
    private var tracks = HashMap<String, TrackModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<TrackModel>{
        return ArrayList(tracks.values)
    }
    override fun create(obj: TrackModel){

    }
    override fun update(obj: TrackModel){

    }
    override fun add(obj: TrackModel){
        tracks[obj.id] = obj
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(tracks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        tracks = gsonBuilder.fromJson(jsonString, listType)
    }
}

