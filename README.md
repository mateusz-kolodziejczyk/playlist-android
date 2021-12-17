## Playlist App
#### Features
- Integration with Spotify to retrieve songs and artists.
- Add, remove playlists.

#### Running the app

The app does not have full functionality without using a spotify Client Key and Client ID Key. As a result these need to be added to the local.properties file for it to function. However, the app will load a very limited list of tracks and artists if no keys are detected.

These should be added to the local.properties file like this:
```
spotifyClientID="ClientID"
spotifyClientSecret="ClientSecret"
```
