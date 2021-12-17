package org.mk.playlist.fragments.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.fragments.playlists.TrackViewModel
import org.mk.playlist.helpers.artistIDsToArtistString
import org.mk.playlist.helpers.getArtistByIDAndPut
import org.mk.playlist.helpers.getTrackByIDAndPut
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

private const val ARG_TRACK = "track"

class TrackDetailFragment : Fragment() {
    private var track: TrackModel? = null
    private lateinit var binding: FragmentTrackDetailBinding
    private val model: TrackViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getParcelable(ARG_TRACK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding.artistName.text = track?.artistIDs
        val app = activity?.application as MainApp
        track?.let { trackModel ->
            binding.trackName.text = trackModel.name
            binding.artistName.text =
                artistIDsToArtistString(trackModel.artistIDs, app.artists.findAll())
            binding.url.text = trackModel.url.toString()
            binding.buttonUpdate.setOnClickListener {
                val updateRequest = getTrackByIDAndPut(trackModel.id, app, app.accessToken)
                app.queue.add(updateRequest)
            }
            binding.buttonDelete.setOnClickListener {
                app.tracks.delete(trackModel)
                app.playlists.removeTrackFromAll(trackModel.id)
                navigateToList()
            }
            model.selectTrack(trackModel)
        }
    }

    private fun navigateToList() {
        val directions = TrackDetailFragmentDirections.actionTrackDetailFragmentToTrackListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }


    companion object {
        @JvmStatic
        fun newInstance(track: TrackModel) =
            TrackDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_TRACK, track)
                }
            }
    }
}