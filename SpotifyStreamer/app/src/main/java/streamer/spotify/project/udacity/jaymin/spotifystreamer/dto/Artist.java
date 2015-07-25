package streamer.spotify.project.udacity.jaymin.spotifystreamer.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jayminraval on 2015-07-11.
 */
public class Artist implements Parcelable
{
    private String id;
    private String artistName;
    private String artistImageUrl;

    public Artist()
    {

    }

    private Artist(Parcel in)
    {
        setId(in.readString());
        setArtistName(in.readString());
        setArtistImageUrl(in.readString());
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public String getArtistImageUrl()
    {
        return artistImageUrl;
    }

    public void setArtistImageUrl(String artistImageUrl)
    {
        this.artistImageUrl = artistImageUrl;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(getId());
        dest.writeString(getArtistName());
        dest.writeString(getArtistImageUrl());
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>()
    {
        public Artist createFromParcel(Parcel in)
        {
            return new Artist(in);
        }

        public Artist[] newArray(int size)
        {
            return new Artist[size];
        }
    };
}
