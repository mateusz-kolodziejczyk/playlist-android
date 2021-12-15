package org.mk.playlist.adapters

import android.content.ClipData
import androidx.recyclerview.selection.ItemDetailsLookup
import org.mk.playlist.models.TrackModel
import android.content.ClipData.Item


class TrackItemDetail(private val adapterPosition: Int, private val selectionKey: String) :
    ItemDetailsLookup.ItemDetails<String>() {
    override fun getPosition(): Int {
        return adapterPosition
    }

    override fun getSelectionKey(): String {
        return selectionKey
    }
}