package streamer.spotify.project.udacity.jaymin.spotifystreamer.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SearchResultItemClickListener;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class SearchResultArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private CardView searchResultItemCard;
    private TextView artistName;
    private ImageView artistImage;
    private SearchResultItemClickListener searchResultItemClickListener;
    private Artist artist;

    public SearchResultArtistViewHolder(View itemView)
    {
        super(itemView);
        searchResultItemCard = (CardView) itemView.findViewById(R.id.searchResultCardItem);
        searchResultItemCard.setOnClickListener(this);
        artistName = (TextView) itemView.findViewById(R.id.cardArtistName);
        artistImage = (ImageView) itemView.findViewById(R.id.cardArtistImage);
    }

    public CardView getSearchResultItemCard()
    {
        return searchResultItemCard;
    }

    public TextView getArtistName()
    {
        return artistName;
    }

    public ImageView getArtistImage()
    {
        return artistImage;
    }

    public void bindEntity(Artist artist)
    {
        this.artist = artist;
    }

    public void setSearchResultItemClickListener(SearchResultItemClickListener searchResultItemClickListener)
    {
        this.searchResultItemClickListener = searchResultItemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        searchResultItemClickListener.searchItemSelected(artist);
    }
}
