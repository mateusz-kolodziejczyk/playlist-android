package org.mk.playlist.fragments.artists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.mk.playlist.R
import org.mk.playlist.adapters.ArtistSelectorAdapter
import org.mk.playlist.adapters.TrackSelectorAdapter
import org.mk.playlist.databinding.FragmentArtistAddBinding
import org.mk.playlist.helpers.artistSearchRequest
import org.mk.playlist.helpers.trackSearchRequest
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.ArtistModel


class ArtistAddFragment : Fragment() {
    private lateinit var binding: FragmentArtistAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        binding.trackRecyclerView.layoutManager = layoutManager
        val adapter = ArtistSelectorAdapter(app.artists.findAll())
        binding.trackRecyclerView.adapter = adapter

        binding.btnAdd.setText(R.string.button_add)
        binding.btnAdd.setOnClickListener {
            val artistToAdd = adapter.getSelectedArtist()
            artistToAdd?.let {
                app.artists.add(it)
                navigateToArtistList()
            }?:run{
                Snackbar.make(it, R.string.error_no_artist_selected, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.buttonSearch.setOnClickListener {
            val request = artistSearchRequest(binding.artistName.text.toString(), adapter, app.accessToken)
            app.queue.add(request)
        }
    }

    private fun navigateToArtistList() {
        val directions = ArtistAddFragmentDirections.actionArtistAddFragmentToArtistListFragment()
        NavHostFragment.findNavController(this).navigate(directions)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ArtistAddFragment()
    }
}
