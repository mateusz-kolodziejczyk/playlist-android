package org.mk.playlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment.findNavController
import org.mk.playlist.R
import org.mk.playlist.fragments.TrackListFragment
import org.mk.playlist.fragments.TrackListFragmentDirections

class TrackListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

