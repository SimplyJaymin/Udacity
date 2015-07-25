package streamer.spotify.project.udacity.jaymin.spotifystreamer.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kaaes.spotify.webapi.android.models.Track;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SelectedTrackClickListener;

/**
 * Created by jayminraval on 2015-07-04.
 */
public class TopTracksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private CardView topTrackItemCard;
    private TextView trackName;
    private TextView albumName;
    private ImageView albumImage;
    private SelectedTrackClickListener selectedTrackClickListener;
    private Track track;


    public TopTracksViewHolder(View itemView, SelectedTrackClickListener listener)
    {
        super(itemView);
        topTrackItemCard = (CardView) itemView.findViewById(R.id.topTracksCardItem);
        topTrackItemCard.setOnClickListener(this);
        trackName = (TextView) itemView.findViewById(R.id.topTrackCardTrackName);
        albumName = (TextView) itemView.findViewById(R.id.topTrackCardAlbumName);
        albumImage = (ImageView) itemView.findViewById(R.id.topTrackCardAlbumImage);
        selectedTrackClickListener = listener;
    }

    public CardView getTopTrackItemCard()
    {
        return topTrackItemCard;
    }

    public TextView getTrackName()
    {
        return trackName;
    }

    public TextView getAlbumName()
    {
        return albumName;
    }

    public ImageView getAlbumImage()
    {
        return albumImage;
    }

    public void bindEntity(Track track)
    {
        this.track = track;
    }

    @Override
    public void onClick(View v)
    {
        selectedTrackClickListener.selectedTrackForPlayback(track);
    }
}
