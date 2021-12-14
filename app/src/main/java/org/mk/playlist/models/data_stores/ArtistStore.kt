package org.mk.playlist.models.data_stores

import org.mk.playlist.models.ArtistModel


class ArtistStore : DataStore<ArtistModel> {
    // ID to model
    private val artists = HashMap<String, ArtistModel>()
    override fun findAll(): ArrayList<ArtistModel>{
        return ArrayList(artists.values)
    }
    override fun create(obj: ArtistModel){

    }
    override fun update(obj: ArtistModel){

    }
    override fun add(obj: ArtistModel){
        artists[obj.id] = obj;
    }
}