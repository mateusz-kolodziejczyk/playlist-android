package org.mk.playlist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
import org.mk.playlist.activities.TrackListActivity
import org.mk.playlist.adapters.TrackAdapter
import org.mk.playlist.adapters.TrackListener
import org.mk.playlist.main.MainApp
import java.lang.Exception
import androidx.navigation.fragment.findNavController
import org.mk.playlist.databinding.FragmentTrackListBinding
import org.mk.playlist.models.TrackModel

class TrackListFragment : Fragment(), TrackListener {
    private lateinit var binding: FragmentTrackListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = TrackAdapter(app.tracks.findAll(),this)

        registerRefreshCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }

    override fun onTrackClick(track: TrackModel) {
        navigateToTrackDetails(track)
    }

    fun navigateToTrackDetails(track: TrackModel){
        val directions = TrackListFragmentDirections.actionViewTrackDetails(track)
        findNavController(this).navigate(directions)
    }
}