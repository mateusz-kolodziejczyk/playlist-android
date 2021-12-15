package org.mk.playlist.fragments.tracks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
import org.mk.playlist.adapters.ArtistAdapter
import org.mk.playlist.adapters.TrackAdapter
import org.mk.playlist.adapters.TrackListener
import org.mk.playlist.databinding.FragmentListBinding
import org.mk.playlist.fragments.playlists.SharedViewModel
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

class TrackListFragment : Fragment(), TrackListener {
    private lateinit var binding: FragmentListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private val model: SharedViewModel by navGraphViewModels(R.id.main_graph)

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

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TrackAdapter(app.tracks.findAll(),app.artists.findAll(), this)

        registerRefreshCallback()
    }
    // Taken from https://stackoverflow.com/a/52018980
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
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
        searchView.setOnClickListener {view ->  }
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }

    override fun onTrackClick(track: TrackModel) {
        model.selectTrack(track)
        navigateToTrackDetails(track)
    }

    private fun navigateToTrackDetails(track: TrackModel){
        val directions = TrackListFragmentDirections.actionViewTrackDetails(track)
        findNavController(this).navigate(directions)
    }
}