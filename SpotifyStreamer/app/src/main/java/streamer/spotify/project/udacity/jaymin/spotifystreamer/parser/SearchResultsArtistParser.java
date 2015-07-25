package streamer.spotify.project.udacity.jaymin.spotifystreamer.parser;

import java.util.ArrayList;
import java.util.List;

import streamer.spotify.project.udacity.jaymin.spotifystreamer.dto.Artist;

/**
 * Created by jayminraval on 2015-07-11.
 */
public class SearchResultsArtistParser
{
    public static List<Artist> getArtists(List<kaaes.spotify.webapi.android.models.Artist> artists)
    {
        List<Artist> results = new ArrayList<>();
        Artist newArtist;
        for (kaaes.spotify.webapi.android.models.Artist artist : artists)
        {
            newArtist = new Artist();
            newArtist.setArtistName(artist.name);
            String imageUrl = "";
            if (artist.images.size() > 0)
            {
                imageUrl = artist.images.get(0).url;
            }
            newArtist.setArtistImageUrl(imageUrl);
            newArtist.setId(artist.id);
            results.add(newArtist);
        }
        return results;
    }
}
