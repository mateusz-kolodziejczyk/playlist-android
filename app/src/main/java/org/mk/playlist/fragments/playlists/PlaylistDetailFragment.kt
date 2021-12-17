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
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
import org.mk.playlist.adapters.PlaylistAdapter
import org.mk.playlist.adapters.TrackAdapter
import org.mk.playlist.databinding.FragmentPlaylistDetailBinding
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.fragments.tracks.TrackDetailFragment
import org.mk.playlist.fragments.tracks.TrackDetailFragmentDirections
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.helpers.trackIDsToTracks
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel
import org.mk.playlist.models.TrackModel

class PlaylistDetailFragment : Fragment() {
    private val model: PlaylistViewModel by navGraphViewModels(R.id.main_graph)
    private lateinit var binding: FragmentPlaylistDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.artistName.text = track?.artistIDs
        val app = activity?.application as MainApp
        model.getSelectedPlaylist().observe(viewLifecycleOwner, Observer<PlaylistModel> { playlist ->
            binding.playlistName.text = playlist.name
            val layoutManager = LinearLayoutManager(view.context)

            binding.playlistRecyclerView.layoutManager = layoutManager
            binding.playlistRecyclerView.adapter = TrackAdapter(trackIDsToTracks(ArrayList(playlist.trackIDs), app.tracks.findAll()), app.artists.findAll())
            binding.buttonUpdate.setOnClickListener{
                val directions = PlaylistDetailFragmentDirections.actionPlaylistDetailFragmentToPlaylistUpdateFragment()
                NavHostFragment.findNavController(this).navigate(directions)
            }
        })

    }

    companion object {
        @JvmStatic fun newInstance() =
            PlaylistDetailFragment()
    }
}