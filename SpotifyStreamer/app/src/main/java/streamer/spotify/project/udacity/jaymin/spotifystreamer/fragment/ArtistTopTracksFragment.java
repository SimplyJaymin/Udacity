package streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.adapter.TopTracksAdapter;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SelectedTrackClickListener;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class ArtistTopTracksFragment extends BaseFragment
{
    public static final String TAG = ArtistTopTracksFragment.class.getCanonicalName();

    private List<Track> topTracks = new ArrayList<>();
    private TopTracksAdapter topTracksAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String artistId;
    public static final String BUNDLE_KEY_ARTIST_ID = "ARTIST_ID";
    private TextView infoText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        topTracksAdapter = new TopTracksAdapter(topTracks, selectedTrackClickListener, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.top_tracks, container, false);
        initViews(rootView);
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY_ARTIST_ID))
        {
            artistId = savedInstanceState.getString(BUNDLE_KEY_ARTIST_ID);
            getTopTracks();
        }
        return rootView;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState)
//    {
//        outState.putBundle(BUNDLE_KEY_SAVED_BUNDLE, getActivity().getIntent().getExtras());
//        outState = getActivity().getIntent().getExtras();
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        Bundle extras;
//        if (savedInstanceState != null)
//        {
//            extras = savedInstanceState;
//        }
//        else
//        {
//        extras = getActivity().getIntent().getExtras();
//        }

//        if (extras != null)
//        {
//            artistId = extras.getString(BUNDLE_KEY_ARTIST_ID);
//            getTopTracks();
//        }
//    }

    @Override
    public void onResume()
    {
        super.onResume();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null)
        {
            artistId = extras.getString(BUNDLE_KEY_ARTIST_ID);
            getTopTracks();
        }
    }

    private void initViews(View rootView)
    {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.topTracksRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(topTracksAdapter);
        infoText = (TextView) rootView.findViewById(R.id.infoText);
        if (isDualPane())
        {
            infoText.setText(getString(R.string.select_from_search_results_info_message));
            infoText.setVisibility(View.VISIBLE);
        }
    }

    private void getTopTracks()
    {
        if (isOnline())
        {
            progressBar.setVisibility(View.VISIBLE);
            Map<String, Object> params = new HashMap<>();
            params.put("country", "CA");
            spotifyService.getArtistTopTrack(artistId, params, tracksPagerCallback);
        }
    }

    public void displayTopTracks(String artistId)
    {
        this.artistId = artistId;
        getTopTracks();
    }

    public void playSong(Track selectedTrack)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setTrack(selectedTrack);
        mediaPlayerFragment.setTopTrackList(topTracks);

        if (isDualPane())
        {
            mediaPlayerFragment.show(fragmentManager, MediaPlayerFragment.TAG);
        }
        else
        {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(android.R.id.content, mediaPlayerFragment).addToBackStack(null).commit();
        }
    }

    private Callback<Tracks> tracksPagerCallback = new Callback<Tracks>()
    {
        @Override
        public void success(Tracks tracks, Response response)
        {
            progressBar.setVisibility(View.GONE);
            topTracks = tracks.tracks;
            topTracksAdapter.set(topTracks);
            topTracksAdapter.notifyDataSetChanged();

            if (isDualPane())
            {
                infoText.setVisibility(View.GONE);
            }

            if(topTracks.size() == 0)
            {
                infoText.setText(getString(R.string.no_tracks_found_message));
                infoText.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void failure(RetrofitError error)
        {

        }
    };

    private SelectedTrackClickListener selectedTrackClickListener = new SelectedTrackClickListener()
    {
        @Override
        public void selectedTrackForPlayback(Track selectedTrack)
        {
            playSong(selectedTrack);
        }
    };
}
