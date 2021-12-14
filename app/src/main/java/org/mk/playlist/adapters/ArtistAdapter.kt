package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.databinding.CardArtistBinding
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel

interface ArtistLisener {
    fun onTrackClick(artist: ArtistModel)
}

class ArtistAdapter constructor(private var artists: List<ArtistModel>,
                               private val clickHandler: (ArtistModel) -> Unit) :
    RecyclerView.Adapter<ArtistAdapter.MainHolder>() {

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

    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistModel, clickHandler: (ArtistModel) -> Unit) {
            binding.root.setOnClickListener { clickHandler(artist) }
            binding.artistName.text = artist.name
        }
    }
}
