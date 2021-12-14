package org.mk.playlist.fragments.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.activities.MainActivity
import org.mk.playlist.databinding.FragmentTrackUpdateBinding
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

private const val ARG_TRACK = "track"

class TrackUpdateFragment : Fragment() {
    private var track: TrackModel? = null
    private lateinit var binding: FragmentTrackUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getParcelable(ARG_TRACK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        track?.let {
            binding.trackName.setText(it.name)
            binding.url.setText(it.url.toString())

        } ?:run{
            navigateToTrackList()
        }
        // This will only run if track isn't null
        binding.btnAdd.setOnClickListener{
            val trackToAdd = TrackModel(
                id = track!!.id,
                name = binding.trackName.text.toString(),
                url = binding.url.text.toString().toUri(),
                artistIDs = track!!.artistIDs
            )
            if (trackToAdd.name.isEmpty()) {
                Snackbar.make(it, R.string.error_no_track_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                val app = activity?.application as MainApp
                app.tracks.update(trackToAdd)
                navigateToTrackList()
            }
        }
    }
    private fun navigateToTrackList(){
        val directions = TrackUpdateFragmentDirections.actionTrackUpdateFragmentToTrackListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
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