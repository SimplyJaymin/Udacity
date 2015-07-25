package streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;

/**
 * Created by jayminraval on 2015-07-01.
 */
public interface SearchResultItemClickListener
{
    void searchItemSelected(Artist artistClicked);
}
