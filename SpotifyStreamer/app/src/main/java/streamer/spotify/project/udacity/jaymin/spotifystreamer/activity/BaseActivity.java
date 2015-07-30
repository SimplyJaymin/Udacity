package streamer.spotify.project.udacity.jaymin.spotifystreamer.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.utility.NetworkUtils;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class BaseActivity extends AppCompatActivity
{
    private static final String TAG = BaseActivity.class.getCanonicalName();
    private static final String SHARED_PREFERENCES_FILE_NAME = "SpotifyStreamerPreferences";

    protected SpotifyService mSpotifyService;
    private boolean isDualPane;
    private SharedPreferences sharedPreferences;

    public static class PreferenceKeys
    {
        public static final String LATEST_SEARCH_QUERY_KEY = "LATEST_SEARCH_QUERY_KEY";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isDualPane = getResources().getBoolean(R.bool.has_two_panes);
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SpotifyApi api = new SpotifyApi();
        mSpotifyService = api.getService();
    }

    public boolean isDualPane()
    {
        return isDualPane;
    }

    public String getStringFromPreferences(String key)
    {
        return sharedPreferences.getString(key, "");
    }

    public void removeStringFromPreferences(String key)
    {
        sharedPreferences.edit().remove(key).apply();
    }

    public void saveStringInPreferences(String key, String value)
    {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public boolean checkIfOnline()
    {
        boolean isOnline = NetworkUtils.isOnline(this);
        if (!isOnline)
        {
            Toast.makeText(BaseActivity.this, getString(R.string.no_network_connection_message), Toast.LENGTH_SHORT).show();
        }
        return isOnline;
    }
}
