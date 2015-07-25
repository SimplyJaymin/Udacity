package streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class BaseFragment extends Fragment
{
    protected SpotifyService spotifyService;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SpotifyApi api = new SpotifyApi();
        spotifyService = api.getService();
    }
}
