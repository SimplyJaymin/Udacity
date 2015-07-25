package streamer.spotify.project.udacity.jaymin.spotifystreamer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SearchResultItemClickListener;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.viewholder.SearchResultArtistViewHolder;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultArtistViewHolder>
{
    private List<Artist> searchResults;
    private Context context;
    private SearchResultItemClickListener searchResultItemClickListener;

    public SearchResultsAdapter(List<Artist> results, SearchResultItemClickListener searchResultItemClickListener, Context context)
    {
        this.searchResults = results;
        this.searchResultItemClickListener = searchResultItemClickListener;
        this.context = context;
    }

    @Override
    public int getItemCount()
    {
        return searchResults.size();
    }

    @Override
    public SearchResultArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_individual_item, viewGroup, false);
        SearchResultArtistViewHolder searchResultArtistViewHolder = new SearchResultArtistViewHolder(v);
        return searchResultArtistViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultArtistViewHolder searchResultArtistViewHolder, int i)
    {
        Artist artist = searchResults.get(i);
        searchResultArtistViewHolder.setSearchResultItemClickListener(searchResultItemClickListener);
        searchResultArtistViewHolder.bindEntity(artist);
        searchResultArtistViewHolder.getArtistName().setText(artist.getArtistName());
        if (!TextUtils.isEmpty(artist.getArtistImageUrl()))
        {
            Glide.with(context)
                    .load(artist.getArtistImageUrl())
                    .centerCrop()
                    .placeholder(R.drawable.music)
                    .crossFade()
                    .into(searchResultArtistViewHolder.getArtistImage());
        }
        else
        {
            searchResultArtistViewHolder.getArtistImage().setImageResource(R.drawable.music);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void add(List<Artist> results)
    {
        searchResults.addAll(results);
    }

    public void set(List<Artist> results)
    {
        searchResults = results;
    }
}
