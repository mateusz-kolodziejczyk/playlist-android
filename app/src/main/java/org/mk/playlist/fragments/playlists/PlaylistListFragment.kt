package org.mk.playlist.fragments.playlists

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
import org.mk.playlist.adapters.PlaylistAdapter
import org.mk.playlist.databinding.FragmentListBinding
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.PlaylistModel

class PlaylistListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val model: PlaylistViewModel by navGraphViewModels(R.id.main_graph)

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
        binding.recyclerView.adapter = PlaylistAdapter(app.playlists.findAll(), onPlaylistClick)

    }
    // Taken from https://stackoverflow.com/a/52018980
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        setupOptionsMenu(inflater, menu)
    }

    private fun setupOptionsMenu(inflater: MenuInflater, menu: Menu) {
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
                val adapter = binding.recyclerView.adapter as PlaylistAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnClickListener { view -> }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                navigateToPlaylistAdd()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val onPlaylistClick = { playlist: PlaylistModel ->
        model.selectPlaylist(playlist)
        navigateToPlaylistDetails()
    }

    private fun navigateToPlaylistAdd(){
        val directions = PlaylistListFragmentDirections.actionPlaylistListFragmentToPlaylistAddFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }
    private fun navigateToPlaylistDetails(){
        val directions = PlaylistListFragmentDirections.actionPlaylistListFragmentToPlaylistDetailFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }
}