package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel

interface TrackListener {
    fun onTrackClick(track: TrackModel)
}

class TrackAdapter constructor(private var tracks: List<TrackModel>,
                               private var artists: List<ArtistModel>,
                               private val listener: TrackListener) :
    RecyclerView.Adapter<TrackAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        holder.bind(track, listener)
    }

    override fun getItemCount(): Int = tracks.size

    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackModel, listener: TrackListener) {
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.trackName.text = track.name
            val s = artistIDsToArtistString(track.artistIDs, artists)
            binding.artistName.text = s
            binding.root.setOnClickListener { listener.onTrackClick(track) }
        }
    }
}
