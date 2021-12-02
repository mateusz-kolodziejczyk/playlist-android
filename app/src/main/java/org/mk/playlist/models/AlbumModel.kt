package org.wit.playlistapplication.models

import org.wit.playlistapplication.models.interfaces.TrackHolder

data class AlbumModel(var name: String = "", override var tracks: List<TrackModel>) : TrackHolder
