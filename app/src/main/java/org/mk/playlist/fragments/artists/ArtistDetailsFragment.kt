package org.mk.playlist.fragments.artists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.playlist.databinding.FragmentArtistDetailsBinding
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel


private const val ARG_ARTIST = "artist"

class ArtistDetailsFragment : Fragment() {
    private var artist: ArtistModel? = null
    private lateinit var binding: FragmentArtistDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artist = it.getParcelable(ARG_ARTIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArtistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        artist?.let {
            binding.artistName.text = it.name
        }
    }

    companion object {
        @JvmStatic fun newInstance(artist: ArtistModel) =
            ArtistDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ARTIST, artist)
                }
            }
    }
}