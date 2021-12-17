package org.mk.playlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.mk.playlist.R
import org.mk.playlist.databinding.CardArtistBinding
import org.mk.playlist.databinding.CardTrackBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import java.lang.IndexOutOfBoundsException

class ArtistSelectorAdapter (private var artists: List<ArtistModel>,
) :
    RecyclerView.Adapter<ArtistSelectorAdapter.MainHolder>() {
    private var selectedArtistID = ""
    var displayedArtists = ArrayList<ArtistModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardArtistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    fun getSelectedArtist() : ArtistModel?{
        return try{
            displayedArtists.filter { artist -> artist.id == selectedArtistID }[0]
        } catch(e: IndexOutOfBoundsException){
            null
        }
    }

    override fun getItemCount(): Int {
        return displayedArtists.filter{ artist -> !artists.any{it.id == artist.id}}.size
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        // This will filter out any artists already added to the app.
        displayedArtists = ArrayList(displayedArtists.filter{ artist -> !artists.any{it.id == artist.id}})
        val track = displayedArtists[holder.adapterPosition]
        holder.bind(track, track.id == selectedArtistID)
    }
    // Using inner class to access artists list
    inner class MainHolder(private val binding : CardArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistModel, isActivated: Boolean) {
            binding.root.isActivated = isActivated
            binding.artistName.text = artist.name
            Picasso.get().load(artist.imageURL).resize(200,200).into(binding.image)

            val context = binding.root.context

            // Change color if its activated, or not.
            if(isActivated){
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.selected_background))
            }else{
                binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }

            binding.root.setOnClickListener {
                selectedArtistID = artist.id
                notifyDataSetChanged()
            }
        }
    }

}
