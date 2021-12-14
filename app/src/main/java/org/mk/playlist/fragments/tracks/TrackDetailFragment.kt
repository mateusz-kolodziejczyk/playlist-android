package org.mk.playlist.fragments.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

private const val ARG_TRACK = "track"

class TrackDetailFragment : Fragment() {
    private var track: TrackModel? = null
    private lateinit var binding: FragmentTrackDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getParcelable(ARG_TRACK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.artistName.text = track?.artistIDs
        val app = activity?.application as MainApp
        track?.let {
            binding.trackName.text = it.name
            binding.artistName.text = artistIDsToArtistString(it.artistIDs, app.artists.findAll())
        }
    }

    companion object {
        @JvmStatic fun newInstance(track: TrackModel) =
                TrackDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_TRACK, track)
                    }
                }
    }
}