package org.mk.playlist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.playlist.R
import org.mk.playlist.databinding.FragmentTrackDetailBinding
import org.mk.playlist.databinding.FragmentTrackListBinding
import org.mk.playlist.main.MainApp
import org.mk.playlist.models.TrackModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TRACK = "track"

/**
 * A simple [Fragment] subclass.
 * Use the [TrackDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackDetailFragment : Fragment() {
    private var track: TrackModel? = null
    private lateinit var binding: FragmentTrackDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            track = it.getParcelable(ARG_TRACK)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.artistName.text = track?.artistName
        binding.trackName.text = track?.name

    }

    companion object {
        @JvmStatic fun newInstance(track: TrackModel) =
                TrackDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_TRACK, track)
                    }
                }
    }
}