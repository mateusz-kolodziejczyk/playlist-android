package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.mk.playlist.databinding.CardArtistBinding

import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import java.util.*
import kotlin.collections.ArrayList

class ArtistAdapter constructor(private var artists: List<ArtistModel>,
                               private val clickHandler: (ArtistModel) -> Unit) :
    RecyclerView.Adapter<ArtistAdapter.MainHolder>(), Filterable {
    private val originalList: List<ArtistModel> = artists
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardArtistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val artist = artists[holder.adapterPosition]
        holder.bind(artist, clickHandler)
    }

    override fun getItemCount(): Int = artists.size

    // Filter Code taken from https://stackoverflow.com/a/37735562
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                artists = results.values as List<ArtistModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<ArtistModel?>? = null
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
    private fun getFilteredResults(constraint: String): List<ArtistModel> {
        val results: MutableList<ArtistModel> = ArrayList()
        for (artist in originalList) {
            if (artist.name.lowercase(Locale.getDefault()).contains(constraint)) {
                results.add(artist)
            }
        }
        return results
    }
    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistModel, clickHandler: (ArtistModel) -> Unit) {
            binding.root.setOnClickListener { clickHandler(artist) }
            binding.artistName.text = artist.name
            Picasso.get().load(artist.imageURL).resize(200,200).into(binding.image)

        }
    }

}
