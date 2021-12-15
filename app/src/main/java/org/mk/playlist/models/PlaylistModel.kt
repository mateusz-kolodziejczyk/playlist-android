package org.mk.playlist.models

import java.util.*
import kotlin.collections.ArrayList


data class PlaylistModel(val id: UUID, var name: String = "", var tracks: List<TrackModel> = ArrayList<TrackModel>())
