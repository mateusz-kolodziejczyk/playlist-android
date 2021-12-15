package org.mk.playlist.fragments.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.playlist.models.PlaylistModel

class PlaylistViewModel : ViewModel() {
    private val selectedPlaylist = MutableLiveData<PlaylistModel>()
    fun selectPlaylist(playlist: PlaylistModel){
        selectedPlaylist.value = playlist
    }
    fun getSelectedPlaylist(): LiveData<PlaylistModel>{
        return selectedPlaylist
    }
}