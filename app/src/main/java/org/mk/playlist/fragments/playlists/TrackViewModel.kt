package org.mk.playlist.fragments.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.playlist.models.PlaylistModel
import org.mk.playlist.models.TrackModel

class TrackViewModel : ViewModel() {
    private val selectedTrack = MutableLiveData<TrackModel>()
    fun selectTrack(track: TrackModel){
        selectedTrack.value = track
    }
    fun getTrack(): LiveData<TrackModel>{
        return selectedTrack
    }
}