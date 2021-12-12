package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.databinding.CardTrackBinding
import org.wit.playlistapplication.models.TrackModel

interface TrackListener {
    fun onTrackClick(track: TrackModel)
}

class TrackAdapter constructor(private var tracks: List<TrackModel>,
                               private val listener: TrackListener) :
    RecyclerView.Adapter<TrackAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = tracks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = tracks.size

    class MainHolder(private val binding : CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: TrackModel, listener: TrackListener) {
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.trackName.text = track.name
            binding.artistName.text = track.artistName
            binding.root.setOnClickListener { listener.onTrackClick(track) }
        }
    }
}
