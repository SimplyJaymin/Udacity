package streamer.spotify.project.udacity.jaymin.spotifystreamer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.interfaces.SelectedTrackClickListener;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.viewholder.TopTracksViewHolder;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksViewHolder>
{
    private List<Track> topTracks;
    private Context context;
    private SelectedTrackClickListener selectedTrackClickListener;

    public TopTracksAdapter(List<Track> tracks, SelectedTrackClickListener listener, Context context)
    {
        this.topTracks = tracks;
        this.selectedTrackClickListener = listener;
        this.context = context;
    }

    @Override
    public int getItemCount()
    {
        return topTracks.size();
    }

    @Override
    public TopTracksViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_tracks_individual_item, viewGroup, false);
        TopTracksViewHolder topTracksViewHolder = new TopTracksViewHolder(v, selectedTrackClickListener);
        return topTracksViewHolder;
    }

    @Override
    public void onBindViewHolder(TopTracksViewHolder topTracksViewHolder, int i)
    {
        Track topTrack = topTracks.get(i);
        topTracksViewHolder.bindEntity(topTrack);
        topTracksViewHolder.getTrackName().setText(topTrack.name);
        topTracksViewHolder.getAlbumName().setText(topTrack.album.name);
        if (topTrack.album.images.size() > 0)
        {
            Glide.with(context)
                    .load(topTrack.album.images.get(0).url)
                    .centerCrop()
                    .placeholder(R.drawable.music)
                    .crossFade()
                    .into(topTracksViewHolder.getAlbumImage());
        }
        else
        {
            topTracksViewHolder.getAlbumImage().setImageResource(R.drawable.music);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void set(List<Track> tracks)
    {
        topTracks = tracks;
    }
}
