package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.databinding.CardPlaylistBinding
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.PlaylistModel
import java.util.*
import kotlin.collections.ArrayList

class PlaylistAdapter constructor(private var playlists: List<PlaylistModel>,
                                  private val onClickFunction: (PlaylistModel) -> Unit) :
    RecyclerView.Adapter<PlaylistAdapter.MainHolder>(), Filterable {
    val originalList = playlists
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlaylistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playlist = playlists[holder.adapterPosition]
        holder.bind(playlist, onClickFunction)
    }

    override fun getItemCount(): Int = playlists.size
    // Filter Code taken from https://stackoverflow.com/a/37735562
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                playlists = results.values as List<PlaylistModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<PlaylistModel?>? = null
                if (constraint.isEmpty()) {
                    filteredResults = originalList
                } else {
                    filteredResults = getFilteredResults(constraint.toString()
                        .lowercase(Locale.getDefault()))
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }
        }
    }
    private fun getFilteredResults(constraint: String): List<PlaylistModel> {
        val results: MutableList<PlaylistModel> = ArrayList()
        for (artist in originalList) {
            if (artist.name.lowercase(Locale.getDefault()).contains(constraint)) {
                results.add(artist)
            }
        }
        return results
    }
    inner class MainHolder(private val binding : CardPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: PlaylistModel, onClickFunction: (PlaylistModel) -> Unit) {
            binding.playlistName.text = playlist.name
            binding.root.setOnClickListener { onClickFunction(playlist) }
        }
    }
}
