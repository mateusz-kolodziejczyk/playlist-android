package org.mk.playlist.models

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashSet


data class PlaylistModel(override val id: UUID, var name: String = "", var trackIDs: LinkedHashSet<String> = LinkedHashSet()) :  Identifiable<UUID>
