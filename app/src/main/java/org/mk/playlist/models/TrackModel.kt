package org.mk.playlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val id: String,
    var name: String = "",
    var artistName: String = "") : Parcelable
