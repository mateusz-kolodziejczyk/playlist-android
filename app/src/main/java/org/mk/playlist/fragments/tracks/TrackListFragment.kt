package org.mk.playlist.fragments.tracks

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import org.mk.playlist.R
import org.mk.playlist.databinding.FragmentListBinding
import org.mk.playlist.fragments.playlists.TrackViewModel
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

import org.mk.playlist.adapters.*
import org.mk.playlist.helpers.getArtistByIDAndPut
import org.mk.playlist.helpers.getTrackByIDAndPut


class TrackListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val model: TrackViewModel by navGraphViewModels(R.id.main_graph)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        val trackAdapter = TrackAdapter(app.tracks.findAll(), app.artists.findAll(), onTrackClick)
        recyclerView.adapter = trackAdapter

    }
    // Taken from https://stackoverflow.com/a/52018980
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        setupSearchMenu(inflater, menu)
    }

    private fun setupSearchMenu(inflater: MenuInflater, menu: Menu) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = SearchView(activity as Context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val adapter = binding.recyclerView.adapter as TrackAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnClickListener { view -> }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                navigateToTrackAdd()
            }
            R.id.item_updateAll -> {
                val app = activity?.application as MainApp
                // Iterate through each track and start an api request for each one.
                // Add a 100 milisecond delay to avoid getting rate limited
                for(i in 0 until app.tracks.findAll().size) {
                    val track = app.tracks.findAll()[i]
                    Handler(Looper.getMainLooper()).postDelayed({
                        val updateRequest = getTrackByIDAndPut(track.id, app, app.accessToken)
                        app.queue.add(updateRequest)
                    }, 100 * (i.toLong() + 1))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private val onTrackClick = { track: TrackModel ->
        model.selectTrack(track)
        navigateToTrackDetails(track)
    }

    private fun navigateToTrackDetails(track: TrackModel){
        val directions = TrackListFragmentDirections.actionViewTrackDetails(track)
        findNavController(this).navigate(directions)
    }

    private fun navigateToTrackAdd(){
        val directions = TrackListFragmentDirections.actionTrackListFragmentToTrackAddFragment()
        findNavController(this).navigate(directions)
    }
}