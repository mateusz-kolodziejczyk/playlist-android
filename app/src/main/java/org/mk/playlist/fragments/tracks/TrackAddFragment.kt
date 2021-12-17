package org.mk.playlist.fragments.tracks

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.adapters.TrackSelectorAdapter
import org.mk.playlist.databinding.FragmentTrackAddBinding
import org.mk.playlist.helpers.trackSearchRequest
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.ArtistModel


class TrackAddFragment : Fragment() {
    private lateinit var binding: FragmentTrackAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        binding.trackRecyclerView.layoutManager = layoutManager
        val adapter = TrackSelectorAdapter(app.tracks.findAll())
        binding.trackRecyclerView.adapter = adapter

        binding.btnAdd.setOnClickListener{ currentView ->
            val trackToAdd = adapter.getSelectedTrack()
            var artistsToAdd = ArrayList<ArtistModel>()
            trackToAdd?.let { track ->
                artistsToAdd = ArrayList(adapter.getAssociatedArtists(track.artistIDs))
                for(artist in artistsToAdd){
                    app.artists.add(artist)
                }
                app.tracks.add(trackToAdd)
                navigateToTrackList()

            } ?:run{
                Snackbar.make(currentView, R.string.error_no_track_selected, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.buttonSearch.setOnClickListener {
            val request = trackSearchRequest(binding.trackName.text.toString(), adapter, app.accessToken)
            app.queue.add(request)
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