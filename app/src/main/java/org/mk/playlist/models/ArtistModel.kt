package org.mk.playlist.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistModel(var id: String = "",
                       var name: String = "",
                       var url: Uri = Uri.EMPTY,
                       var imageURL: Uri = Uri.EMPTY):Parcelable
