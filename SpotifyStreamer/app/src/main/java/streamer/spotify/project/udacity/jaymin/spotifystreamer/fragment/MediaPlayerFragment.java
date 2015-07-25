package streamer.spotify.project.udacity.jaymin.spotifystreamer.fragment;

import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import streamer.spotify.project.udacity.jaymin.spotifystreamer.R;

/**
 * Created by jayminraval on 2015-07-04.
 */
public class MediaPlayerFragment extends BaseDialogFragment implements View.OnClickListener
{
    public static final String TAG = MediaPlayerFragment.class.getSimpleName();
    private Track track;
    private List<Track> topTracks = new ArrayList<>();
    private ImageView mediaPlayerBackground;
    private ImageView mediaPlayerControlPlayPause;
    private ImageView mediaPlayerControlNext;
    private ImageView mediaPlayerControlPrevious;
    private TextView artistName;
    private TextView albumName;
    private TextView songName;
    private TextView songTotalDuration;
    private TextView songCurrentTime;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private SeekBar seekBar;
    private Handler durationHandler = new Handler();
    private int selectedTrackPosition;

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if (mediaPlayer != null && fromUser)
            {
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
        }
    };

    private Runnable updateSeekBarTime = new Runnable()
    {
        public void run()
        {
            int timeElapsed = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(timeElapsed);
            songCurrentTime.setText(getFormattedTimeFromMilliseconds(timeElapsed));
            //repeat yourself that again in 100 milliseconds
            durationHandler.postDelayed(this, 100);
        }
    };


    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            seekBar.setMax(mediaPlayer.getDuration());
            songTotalDuration.setText(getFormattedTimeFromMilliseconds(mediaPlayer.getDuration()));
            progressBar.setVisibility(View.GONE);
            mediaPlayerControlPlayPause.setVisibility(View.VISIBLE);
            mediaPlayerControlPlayPause.setEnabled(true);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            resetSeekBar();
            seekBar.setEnabled(true);
            bindTrackToSeekBar();
        }
    };

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            updatePlayPauseIcon();
            resetSeekBar();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        selectedTrackPosition = getSelectedTrackPosition();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(preparedListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.player, container, false);
        initViews(rootView);
        populateViews();
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v)
    {
        int viewClickedID = v.getId();
        if (viewClickedID == R.id.mediaPlayerControlPlayPause)
        {
            if (mediaPlayer.isPlaying())
            {
                mediaPlayer.pause();
            }
            else
            {
                playOrResume();
            }
            updatePlayPauseIcon();
        }
        else if (viewClickedID == R.id.mediaPlayerControlNext)
        {
            selectedTrackPosition++;
            track = topTracks.get(selectedTrackPosition);
            populateViews();
        }
        else if (viewClickedID == R.id.mediaPlayerControlPrevious)
        {
            selectedTrackPosition--;
            track = topTracks.get(selectedTrackPosition);
            populateViews();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        durationHandler.removeCallbacks(updateSeekBarTime);
    }

    private void playOrResume()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.start();
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            durationHandler.postDelayed(updateSeekBarTime, 100);
        }
    }

    private void updatePlayPauseIcon()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayerControlPlayPause.setImageResource(R.drawable.icon_pause);
        }
        else
        {
            mediaPlayerControlPlayPause.setImageResource(R.drawable.icon_play);
        }
    }

    private void initViews(View rootView)
    {
        mediaPlayerBackground = (ImageView) rootView.findViewById(R.id.mediaPlayerBackgroundImage);
        artistName = (TextView) rootView.findViewById(R.id.mediaPlayerArtistName);
        albumName = (TextView) rootView.findViewById(R.id.mediaPlayerAlbumName);
        songName = (TextView) rootView.findViewById(R.id.mediaPlayerSongName);
        songTotalDuration = (TextView) rootView.findViewById(R.id.mediaPlayerTotalTime);
        songCurrentTime = (TextView) rootView.findViewById(R.id.mediaPlayerCurrentTime);
        mediaPlayerControlPlayPause = (ImageView) rootView.findViewById(R.id.mediaPlayerControlPlayPause);
        mediaPlayerControlNext = (ImageView) rootView.findViewById(R.id.mediaPlayerControlNext);
        mediaPlayerControlPrevious = (ImageView) rootView.findViewById(R.id.mediaPlayerControlPrevious);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        seekBar = (SeekBar) rootView.findViewById(R.id.mediaPlayerSeekBar);

        mediaPlayerControlPlayPause.setOnClickListener(this);
        mediaPlayerControlNext.setOnClickListener(this);
        mediaPlayerControlPrevious.setOnClickListener(this);

        mediaPlayerControlPlayPause.setEnabled(false);
        seekBar.setEnabled(false);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void populateViews()
    {
        if (selectedTrackPosition == -1)
        {
            mediaPlayerControlNext.setEnabled(false);
            mediaPlayerControlPrevious.setEnabled(false);
        }
        else if (selectedTrackPosition == 0)
        {
            mediaPlayerControlPrevious.setEnabled(false);
        }
        else if (selectedTrackPosition == topTracks.size() - 1)
        {
            mediaPlayerControlNext.setEnabled(false);
        }

        artistName.setText(getArtistName());
        albumName.setText(track.album.name);
        songName.setText(track.name);
        String backgroundImageUrl = getImageUrl();
        if (!TextUtils.isEmpty(backgroundImageUrl))
        {
            Glide.with(getActivity())
                    .load(backgroundImageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.music)
                    .crossFade()
                    .into(mediaPlayerBackground);
        }
        else
        {
            mediaPlayerBackground.setImageResource(R.drawable.music);
        }
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(track.preview_url);
            mediaPlayer.prepareAsync();
            mediaPlayerControlPlayPause.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading track...", Toast.LENGTH_LONG).show();
            mediaPlayerControlPlayPause.setEnabled(false);
        }
    }

    private String getImageUrl()
    {
        List<Image> images = track.album.images;
        if (images.size() > 0)
        {
            return images.get(0).url;
        }
        else
        {
            return "";
        }
    }

    private String getFormattedTimeFromMilliseconds(long milliseconds)
    {
        long trackTotalDuration = milliseconds / 1000;
        StringBuilder result = new StringBuilder();
        long secondsPart = trackTotalDuration % 60;
        return result.append((int) trackTotalDuration / 60).append(":").append((secondsPart < 10) ? 0 : "").append(secondsPart).toString();
    }

    private String getArtistName()
    {
        List<ArtistSimple> artistSimpleList = track.artists;
        StringBuilder artistName = new StringBuilder();
        String multipleArtistNameSeparator = " & ";
        for (ArtistSimple artistSimple : artistSimpleList)
        {
            artistName.append(artistSimple.name).append(multipleArtistNameSeparator);
        }
        artistName.replace(artistName.lastIndexOf(multipleArtistNameSeparator), artistName.length(), "");
        return artistName.toString();
    }

    public void setTrack(Track trackToSet)
    {
        this.track = trackToSet;
    }

    public void setTopTrackList(List<Track> topTrackListToSet)
    {
        this.topTracks = topTrackListToSet;
    }

    private void resetSeekBar()
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                seekBar.setProgress(0);
            }
        });
    }

    private void bindTrackToSeekBar()
    {
        final Handler mHandler = new Handler();
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                while (mediaPlayer != null && mediaPlayer.isPlaying())
                {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    mHandler.postDelayed(this, 1000);
                }
            }
        });
    }

    private int getSelectedTrackPosition()
    {
        for (int counter = 0; counter < topTracks.size(); counter++)
        {
            if (track.id.equals(topTracks.get(counter).id))
            {
                return counter;
            }
        }
        return -1;
    }
}
