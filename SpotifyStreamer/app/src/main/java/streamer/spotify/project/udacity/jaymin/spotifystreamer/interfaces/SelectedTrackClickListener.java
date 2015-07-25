package streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces;

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by jayminraval on 2015-07-04.
 */
public interface SelectedTrackClickListener
{
    void selectedTrackForPlayback(Track selectedTrack);
}
