package org.mk.playlist.models.data_stores

import org.wit.playlistapplication.models.TrackModel

class TrackStore : DataStore<TrackModel> {
    // ID to model
    private val tracks = HashMap<String, TrackModel>()
    override fun findAll(): ArrayList<TrackModel>{
        return ArrayList(tracks.values)
    }
    override fun create(obj: TrackModel){

    }
    override fun update(obj: TrackModel){

    }
    override fun add(obj: TrackModel){
        tracks[obj.id] = obj;
    }
}