package org.mk.playlist.fragments.playlists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.adapters.PlaylistTrackSelectorAdapter
import org.mk.playlist.databinding.FragmentPlaylistAddBinding
import org.mk.playlist.helpers.trackIDsToTracks
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel
import java.util.UUID
import kotlin.collections.LinkedHashSet

class PlaylistUpdateFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistAddBinding
    private val model: PlaylistViewModel by navGraphViewModels(R.id.main_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPlaylistAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp
        // Set the adapter to default adapter with no songs by default.

        model.getSelectedPlaylist().observe(viewLifecycleOwner, { selectedPlaylist ->
            binding.playlistName.setText(selectedPlaylist.name)
            val adapter = PlaylistTrackSelectorAdapter(app.tracks.findAll(), app.artists.findAll(), selectedPlaylist.trackIDs)
            binding.trackRecyclerView.layoutManager = layoutManager
            binding.trackRecyclerView.adapter = adapter
            // Set the button to say update
            binding.btnAdd.setText(R.string.button_update)
            binding.btnAdd.setOnClickListener{ currentView ->
                val updatedPlaylist = PlaylistModel(
                    id = selectedPlaylist.id,
                    name = binding.playlistName.text.toString(),
                    trackIDs = adapter.selectedTracks
                )
                if (updatedPlaylist.name.isEmpty()) {
                    Snackbar.make(currentView, R.string.error_no_playlist_name, Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.playlists.update(updatedPlaylist)
                    navigateToList()
                }
            }
        })


    }
    private fun navigateToList(){
        val directions = PlaylistUpdateFragmentDirections.actionPlaylistUpdateFragmentToPlaylistListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }
    companion object {
        @JvmStatic fun newInstance() =
            PlaylistAddFragment()
    }
}