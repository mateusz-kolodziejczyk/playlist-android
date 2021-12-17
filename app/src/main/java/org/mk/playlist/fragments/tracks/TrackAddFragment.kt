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
import org.mk.playlist.databinding.FragmentTrackUpdateBinding
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel


class TrackAddFragment : Fragment() {
    private lateinit var binding: FragmentTrackUpdateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener{
            val trackToAdd = TrackModel(
                id = java.util.UUID.randomUUID().toString(),
                name = binding.trackName.text.toString(),
                url = binding.url.text.toString().toUri(),
                artistIDs = LinkedHashSet()
            )
            if (trackToAdd.name.isEmpty()) {
                Snackbar.make(it, R.string.error_no_track_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                val app = activity?.application as MainApp
                app.tracks.add(trackToAdd)
                navigateToTrackList()
            }
        }
    }
    private fun navigateToTrackList(){
        val directions = TrackAddFragmentDirections.actionTrackAddFragmentToTrackListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }
    companion object {
        @JvmStatic fun newInstance() =
            TrackAddFragment()
    }
}