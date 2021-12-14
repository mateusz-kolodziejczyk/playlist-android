package org.mk.playlist.fragments.artists

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.playlist.R
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

    private val navigateToArtistDetails = { artist: ArtistModel ->
        val directions = ArtistListFragmentDirections.actionArtistListFragmentToArtistDetailsFragment(artist)
        NavHostFragment.findNavController(this).navigate(directions)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArtistListFragment()
    }
}