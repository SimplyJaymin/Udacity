package streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.activity.BaseActivity;

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

    protected void removeStringFromPreferences(String key)
    {
        ((BaseActivity) getActivity()).removeStringFromPreferences(key);
    }

    protected String getStringFromPreferences(String key)
    {
        return ((BaseActivity) getActivity()).getStringFromPreferences(key);
    }

    protected void saveStringInPreferences(String key, String value)
    {
        ((BaseActivity) getActivity()).saveStringInPreferences(key, value);
    }

    protected boolean isOnline()
    {
        return ((BaseActivity) getActivity()).checkIfOnline();
    }

    protected boolean isDualPane()
    {
        return ((BaseActivity) getActivity()).isDualPane();
    }
}
