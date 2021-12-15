package org.mk.playlist.fragments.artists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.activities.MainActivity
import org.mk.playlist.databinding.FragmentArtistAddBinding
import org.mk.playlist.databinding.FragmentTrackUpdateBinding
import org.mk.playlist.fragments.artists.ArtistAddFragmentDirections
import org.mk.playlist.fragments.playlists.SharedViewModel
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel


class ArtistAddFragment : Fragment() {
    private var track: TrackModel? = null
    private lateinit var binding: FragmentArtistAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setText(R.string.button_add)
        binding.btnAdd.setOnClickListener {
            // If the user adds the artist manually, assign a UUID to it.
            val artistToAdd = ArtistModel(
                id = java.util.UUID.randomUUID().toString(),
                name = binding.artistName.text.toString()
            )
            if (artistToAdd.name.isEmpty()) {
                Snackbar.make(it, R.string.error_no_artist_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val app = activity?.application as MainApp
                app.artists.add(artistToAdd)
                navigateToArtistList()
            }
        }
    }

    private fun navigateToArtistList() {
        val directions = ArtistAddFragmentDirections.actionArtistAddFragmentToArtistListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArtistAddFragment()
    }
}
