package org.mk.playlist.helpers

import androidx.core.net.toUri
import org.mk.playlist.models.ArtistModel
import org.mk.playlist.models.TrackModel
import org.mk.playlist.models.data_stores.ArtistStore
import org.mk.playlist.models.data_stores.TrackStore

fun addSampleData(trackStore: TrackStore, artistStore: ArtistStore){
    val tracks: ArrayList<TrackModel> = arrayListOf(
        TrackModel(
            id="2hHLbkatPwOOmrNxTiD41L",
            name="I Really Like You",
            url="https://open.spotify.com/track/2hHLbkatPwOOmrNxTiD41L".toUri(),
            artistIDs = LinkedHashSet(hashSetOf<String>("6sFIWsNpZYqfjUpaCgueju"))
        ),
        TrackModel(
            id="3TGRqZ0a2l1LRblBkJoaDx",
            name="Call Me Maybe",
            url="https://open.spotify.com/track/3TGRqZ0a2l1LRblBkJoaDx".toUri(),
            artistIDs = LinkedHashSet(hashSetOf("6sFIWsNpZYqfjUpaCgueju"))
        ),
        TrackModel(
            id="0FS7B5o3QyvOD8eWjnbLoO",
            name="Run Away With Me",
            url="https://open.spotify.com/track/0FS7B5o3QyvOD8eWjnbLoO".toUri(),
            artistIDs = LinkedHashSet(hashSetOf("6sFIWsNpZYqfjUpaCgueju"))
        ),
    )
    val artists: ArrayList<ArtistModel> = arrayListOf(
        ArtistModel(
            id="6sFIWsNpZYqfjUpaCgueju",
            imageURL = "https://i.scdn.co/image/ab6761610000e5eb71fed7c1f401da1662f209cb".toUri(),
            name = "Carly Rae Jepsen",
            url = "https://open.spotify.com/artist/6sFIWsNpZYqfjUpaCgueju".toUri()
        ),
        ArtistModel(
            id="6XyY86QOPPrYVGvF9ch6wz",
            imageURL = "https://i.scdn.co/image/ab6761610000e5eb34e5aa6afc1ba147bfbb0677".toUri(),
            name = "Linkin Park",
            url = "https://open.spotify.com/artist/6XyY86QOPPrYVGvF9ch6wz".toUri()
        ),
    )
    for (artist in artists){
        artistStore.add(artist)
    }
    for (track in tracks){
        trackStore.add(track)
    }
}