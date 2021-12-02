package org.wit.playlistapplication.models

import org.wit.playlistapplication.models.interfaces.TrackHolder

data class PlaylistModel(var name: String = "", override var tracks: List<TrackModel>) : TrackHolder
