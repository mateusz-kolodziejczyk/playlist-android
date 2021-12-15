package org.mk.playlist.fragments.artists

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
import org.mk.playlist.activities.MainActivity
import org.mk.playlist.adapters.ArtistAdapter
import org.mk.playlist.adapters.TrackAdapter
import org.mk.playlist.databinding.FragmentListBinding
import org.mk.playlist.fragments.tracks.TrackListFragmentDirections
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel


class ArtistListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

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
        binding.recyclerView.adapter = ArtistAdapter(app.artists.findAll(), navigateToArtistDetails)

        registerRefreshCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }

//    override fun onTrackClick(track: TrackModel) {
//        navigateToTrackDetails(track)
//    }

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
                val adapter = binding.recyclerView.adapter as ArtistAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnClickListener { view -> }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                navigateToArtistAdd()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val navigateToArtistDetails = { artist: ArtistModel ->
        val directions = ArtistListFragmentDirections.actionArtistListFragmentToArtistDetailsFragment(artist)
        NavHostFragment.findNavController(this).navigate(directions)
    }

    private fun navigateToArtistAdd(){
        val directions = ArtistListFragmentDirections.actionArtistListFragmentToArtistAddFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArtistListFragment()
    }
}