package org.mk.playlist.adapters

import android.os.Parcel
import androidx.recyclerview.selection.ItemKeyProvider
import org.mk.playlist.models.TrackModel


class TrackKeyProvider(scope: Int, private val tracks: List<String>) :
    ItemKeyProvider<String>(scope) {

    override fun getKey(position: Int): String {
        return tracks[position]
    }

    override fun getPosition(key: String): Int {
        return tracks.indexOf(key)
    }
}