package org.mk.playlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistModel(var id: String = "",
                       var name: String = ""):Parcelable
