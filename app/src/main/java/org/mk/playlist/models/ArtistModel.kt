package org.mk.playlist.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistModel(override val id: String = "",
                       var name: String = "",
                       var url: Uri = Uri.EMPTY,
                       var imageURL: Uri = Uri.EMPTY):Parcelable, Identifiable<String>
