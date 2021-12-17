package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.mk.playlist.R
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import java.lang.IndexOutOfBoundsException
import java.time.LocalDate
import kotlin.collections.LinkedHashSet

class TrackSelectorAdapter (private var tracks: List<TrackModel>,
) :
    RecyclerView.Adapter<TrackSelectorAdapter.MainHolder>() {
    private var selectedTrackID = ""
    var displayedTracks = ArrayList<TrackModel>()
    // These are artists that the tracks reference. This is stored here so that they can be later added to the main store if a song is added.
    var associatedArtists = HashMap<String, ArtistModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    fun getSelectedTrack() : TrackModel?{
        return try{
            displayedTracks.filter { track -> track.id == selectedTrackID }[0]
        } catch(e: IndexOutOfBoundsException){
            null
        }
    }

    fun getAssociatedArtists(artistIDs: LinkedHashSet<String>) : List<ArtistModel>{
        val artists = ArrayList<ArtistModel>()
        for(id in artistIDs){
            // Only add an artist if it was found
            associatedArtists[id]?.let {
                artists.add(it)
            }
        }
        return artists
    }
    override fun getItemCount(): Int {
        return displayedTracks.filter{ track -> !tracks.any{it.id == track.id}}.size
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        // This will filter out any tracks already added to the app.
        displayedTracks = ArrayList(displayedTracks.filter{ track -> !tracks.any{it.id == track.id}})
        val track = displayedTracks[holder.adapterPosition]
        holder.bind(track, track.id == selectedTrackID)
    }
    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackModel, isActivated: Boolean) {
            binding.root.isActivated = isActivated
            binding.trackName.text = track.name
            val context = binding.root.context
            val s = artistIDsToArtistString(track.artistIDs, ArrayList(associatedArtists.values))
            binding.artistName.text = s
            // Change color if its activated, or not.
            if(isActivated){
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.selected_background))
            }else{
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }

            binding.root.setOnClickListener {
                selectedTrackID = track.id
                notifyDataSetChanged()
            }
        }
    }

}
