package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import java.util.*
import kotlin.collections.ArrayList


class TrackAdapter(private var tracks: List<TrackModel>,
                   private var artists: List<ArtistModel>,
                   private val onClickFunction: (TrackModel) -> Unit = {}
) :
    RecyclerView.Adapter<TrackAdapter.MainHolder>(), Filterable {
    val originalList = tracks
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        holder.bind(track, onClickFunction)
    }

    override fun getItemCount(): Int = tracks.size
    // Filter Code taken from https://stackoverflow.com/a/37735562
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                tracks = results.values as List<TrackModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<TrackModel?>? = null
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
    private fun getFilteredResults(constraint: String): List<TrackModel> {
        val results: MutableList<TrackModel> = ArrayList()
        for (artist in originalList) {
            if (artist.name.lowercase(Locale.getDefault()).contains(constraint)) {
                results.add(artist)
            }
        }
        return results
    }
    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackModel, onClickFunction: (TrackModel) -> Unit) {
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.trackName.text = track.name
            val s = artistIDsToArtistString(track.artistIDs, artists)
            binding.artistName.text = s

            binding.root.setOnClickListener { onClickFunction(track) }
        }
    }
}
