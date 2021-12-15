package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.R
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import timber.log.Timber.i
import kotlin.collections.LinkedHashSet

class PlaylistTrackSelectorAdapter constructor(private var tracks: List<TrackModel>,
                                               private var artists: List<ArtistModel>,
                                               private var playlistTracks: LinkedHashSet<TrackModel> = LinkedHashSet()
) :
    RecyclerView.Adapter<PlaylistTrackSelectorAdapter.MainHolder>() {
    private val originalList = tracks
    val selectedTracks = LinkedHashSet<TrackModel>(playlistTracks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val track = tracks[holder.adapterPosition]
        holder.bind(track, selectedTracks.contains(track))
    }
    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackModel, isActivated: Boolean) {
            binding.root.isActivated = isActivated
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.trackName.text = track.name
            val s = artistIDsToArtistString(track.artistIDs, artists)
            binding.artistName.text = s
            val context = binding.root.context

            // Change color if its activated, or not.
            if(isActivated){
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.selected_background))
            }else{
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            binding.root.setOnClickListener {
                if (!isActivated){
                    selectedTracks.add(track)
                }
                else{
                    selectedTracks.remove(track)
                }
                selectedTracks.forEach{
                    i("$it")
                }
                notifyDataSetChanged()
            }
        }
        fun getItemDetails() : ItemDetailsLookup.ItemDetails<String>{
            return TrackItemDetail(adapterPosition, tracks[adapterPosition].id)
        }
    }

}
