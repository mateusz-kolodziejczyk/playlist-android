package org.mk.playlist.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackModel(
    val id: String,
    var name: String = "",
    var url: Uri = Uri.EMPTY,
    internal val artistIDs: LinkedHashSet<String> = LinkedHashSet()) : Parcelable
