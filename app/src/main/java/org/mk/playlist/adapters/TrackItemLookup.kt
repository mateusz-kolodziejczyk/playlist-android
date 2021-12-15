package org.mk.playlist.adapters

import androidx.recyclerview.widget.RecyclerView

import android.view.MotionEvent
import android.view.View

import androidx.recyclerview.selection.ItemDetailsLookup


class TrackItemLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<String>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<String>? {
        val view: View? = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is PlaylistTrackSelectorAdapter.MainHolder) {
                return viewHolder.getItemDetails()
            }
        }
        return null
    }
}