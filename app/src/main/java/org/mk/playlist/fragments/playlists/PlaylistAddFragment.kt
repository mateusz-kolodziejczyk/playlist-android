package org.mk.playlist.fragments.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.adapters.PlaylistTrackSelectorAdapter
import org.mk.playlist.databinding.FragmentPlaylistAddBinding
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel


class PlaylistAddFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPlaylistAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        binding.trackRecyclerView.layoutManager = layoutManager
        val adapter = PlaylistTrackSelectorAdapter(app.tracks.findAll(), app.artists.findAll())
        binding.trackRecyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener{ currentView ->
            val playlistToAdd = PlaylistModel(
                id = java.util.UUID.randomUUID(),
                name = binding.playlistName.text.toString(),
                trackIDs = adapter.selectedTracks
            )
            if (playlistToAdd.name.isEmpty()) {
                Snackbar.make(currentView, R.string.error_no_playlist_name, Snackbar.LENGTH_LONG)
                    .show()
            }
            else{
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