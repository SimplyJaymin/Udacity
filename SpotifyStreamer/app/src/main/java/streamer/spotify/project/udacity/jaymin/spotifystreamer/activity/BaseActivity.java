package streamer.spotify.project.udacity.jaymin.spotifystreamer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class BaseActivity extends AppCompatActivity
{
    protected SpotifyService mSpotifyService;
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isDualPane = getResources().getBoolean(R.bool.has_two_panes);
        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

    public boolean isDualPane()
    {
        return isDualPane;
    }
}
