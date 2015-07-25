package streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.adapter.SearchResultsAdapter;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SearchResultItemClickListener;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.parser.SearchResultsArtistParser;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.utility.ViewUtils;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class SearchResultsFragment extends BaseFragment
{
    private EditText artistSearchInput;
    private ProgressBar progressBar;
    private SearchResultsAdapter searchResultsAdapter;
    private List<Artist> searchResults = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchResultItemClickListener searchResultItemClickListener;
    private static final String BUNDLE_SEARCH_RESULTS = "SEARCH_RESULTS";

    private Callback<ArtistsPager> artistsPagerCallback = new Callback<ArtistsPager>()
    {
        @Override
        public void success(ArtistsPager artistsPager, Response response)
        {
            progressBar.setVisibility(View.GONE);
            searchResults = SearchResultsArtistParser.getArtists(artistsPager.artists.items);
            searchResultsAdapter = new SearchResultsAdapter(searchResults, searchResultItemClickListener, getActivity());
            recyclerView.setAdapter(searchResultsAdapter);
        }

        @Override
        public void failure(RetrofitError error)
        {
            progressBar.setVisibility(View.GONE);
        }
    };

    public void setSearchResultItemClickListener(SearchResultItemClickListener listener)
    {
        this.searchResultItemClickListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.search_results, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_SEARCH_RESULTS))
        {
            searchResults = savedInstanceState.getParcelableArrayList(BUNDLE_SEARCH_RESULTS);
            searchResultsAdapter = new SearchResultsAdapter(searchResults, searchResultItemClickListener, getActivity());
            recyclerView.setAdapter(searchResultsAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_SEARCH_RESULTS, (ArrayList<Artist>) searchResults);
    }

    private void initViews(View rootView)
    {
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        artistSearchInput = (EditText) rootView.findViewById(R.id.searchArtistInput);
        artistSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    searchArtist(v.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.searchResultsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void searchArtist(String query)
    {
        ViewUtils.hideSoftKeyboard(getActivity(), artistSearchInput);
        progressBar.setVisibility(View.VISIBLE);
        spotifyService.searchArtists(query, artistsPagerCallback);
    }
}