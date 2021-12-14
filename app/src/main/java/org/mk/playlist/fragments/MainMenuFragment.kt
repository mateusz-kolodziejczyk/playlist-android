package org.mk.playlist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.mk.playlist.R
import org.mk.playlist.databinding.FragmentMainMenuBinding
import org.mk.playlist.helpers.getArtistTopTracks
import org.mk.playlist.main.MainApp
import timber.log.Timber
class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val queue : RequestQueue? = Volley.newRequestQueue(activity?.applicationContext)
        val app = activity?.application as MainApp
        // Only set the listener if the activity exists
        queue?.let {
            binding.getToken.setOnClickListener() {
                val getTopTracksRequest = getArtistTopTracks("6PfSUFtkMVoDkx4MQkzOi3", app.accessToken, app)
                queue.add(getTopTracksRequest)
                for(track in app.tracks.findAll()){
                    Timber.i("$track")
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val directions = MainMenuFragmentDirections.actionMainMenuTrackList()
                NavHostFragment.findNavController(this).navigate(directions)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainMenuFragment()
    }
}