package streamer.spotify.project.udacity.jaymin.spotifystreamer.activity;

import android.os.Bundle;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;

public class TopTracksActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        // If should be in two-pane mode, finish to return to main activity
        if (getResources().getBoolean(R.bool.has_two_panes))
        {
            finish();
            return;
        }
    }
}
