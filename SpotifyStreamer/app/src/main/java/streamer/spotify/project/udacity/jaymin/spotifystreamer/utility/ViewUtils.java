package streamer.spotify.project.udacity.jaymin.spotifystreamer.utility;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by jayminraval on 2015-07-01.
 */
public class ViewUtils
{
    public static void hideSoftKeyboard(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
