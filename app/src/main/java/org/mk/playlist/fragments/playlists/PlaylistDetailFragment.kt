package org.mk.playlist.fragments.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import org.mk.playlist.R
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.fragments.tracks.TrackDetailFragment
import org.mk.playlist.fragments.tracks.TrackDetailFragmentDirections
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel
import org.mk.playlist.models.TrackModel

class PlaylistDetailFragment : Fragment() {
    private val model: PlaylistViewModel by navGraphViewModels(R.id.main_graph)
    private var track: TrackModel? = null
    private lateinit var binding: FragmentTrackDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.artistName.text = track?.artistIDs
        val app = activity?.application as MainApp
        model.getSelectedPlaylist().observe(viewLifecycleOwner, Observer<PlaylistModel> { playlist ->

        })
    }

    companion object {
        @JvmStatic fun newInstance(track: TrackModel) =
            TrackDetailFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}