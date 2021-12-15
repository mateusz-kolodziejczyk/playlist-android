package org.mk.playlist.fragments.playlists

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
import org.mk.playlist.databinding.FragmentPlaylistAddBinding
import org.mk.playlist.databinding.FragmentTrackUpdateBinding
import org.mk.playlist.fragments.playlists.SharedViewModel
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel
import org.mk.playlist.models.TrackModel


class PlaylistAddFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPlaylistAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener{
            val playlistToAdd = PlaylistModel(
                id = java.util.UUID.randomUUID(),
                name = binding.playlistName.text.toString(),
            )
            if (playlistToAdd.name.isEmpty()) {
                Snackbar.make(it, R.string.error_no_playlist_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
                val app = activity?.application as MainApp
                app.playlists.add(playlistToAdd)
                navigateToList()
            }
        }
    }
    private fun navigateToList(){
        val directions = PlaylistAddFragmentDirections.actionPlaylistAddFragmentToPlaylistListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }
    companion object {
        @JvmStatic fun newInstance() =
            PlaylistAddFragment()
    }
}