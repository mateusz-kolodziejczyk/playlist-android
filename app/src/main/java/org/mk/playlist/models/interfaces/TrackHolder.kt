package org.wit.playlistapplication.models.interfaces

import org.wit.playlistapplication.models.TrackModel

interface TrackHolder {
    var tracks: List<TrackModel>
}