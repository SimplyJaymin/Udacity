package streamer.spotify.project.udacity.jaymin.spotifystreamer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment.ArtistTopTracksFragment;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment.SearchResultsFragment;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SearchResultItemClickListener;


public class MainActivity extends BaseActivity
{
    private SearchResultsFragment searchResultsFragment;
    private ArtistTopTracksFragment topTracksFragment;

    private SearchResultItemClickListener searchResultItemClickListener = new SearchResultItemClickListener()
    {
        @Override
        public void searchItemSelected(Artist artistClicked)
        {
            if (isDualPane())
            {
                topTracksFragment = (ArtistTopTracksFragment) getSupportFragmentManager().findFragmentByTag(ArtistTopTracksFragment.TAG);
                topTracksFragment.displayTopTracks(artistClicked.getId());
            }
            else
            {
                Intent topTracksIntent = new Intent(MainActivity.this, TopTracksActivity.class);
                Bundle extras = new Bundle();
                extras.putString(ArtistTopTracksFragment.BUNDLE_KEY_ARTIST_ID, artistClicked.getId());
                topTracksIntent.putExtras(extras);
                startActivity(topTracksIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchResultsFragment = (SearchResultsFragment) getSupportFragmentManager().findFragmentById(R.id.searchResultsFragment);
        searchResultsFragment.setSearchResultItemClickListener(searchResultItemClickListener);
        if(isDualPane())
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ArtistTopTracksFragment artistTopTracksFragment = new ArtistTopTracksFragment();
            fragmentManager.beginTransaction().add(R.id.topTracksFragmentContainer, artistTopTracksFragment, ArtistTopTracksFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
