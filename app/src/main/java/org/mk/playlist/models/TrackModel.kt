package org.mk.playlist.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*
import kotlin.collections.LinkedHashSet

@Parcelize
data class TrackModel (
    override val id: String,
    var name: String = "",
    var url: Uri = Uri.EMPTY,
    internal val artistIDs: LinkedHashSet<String> = LinkedHashSet()) : Parcelable, Identifiable<String>
