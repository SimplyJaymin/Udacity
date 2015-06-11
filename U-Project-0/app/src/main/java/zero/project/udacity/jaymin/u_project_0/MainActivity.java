package zero.project.udacity.jaymin.u_project_0;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onClick(View v)
    {
        int viewIdClicked = v.getId();
        if (viewIdClicked == R.id.spotifyStreamerAppBtn)
        {
            displayMessage("This button will launch my Spotify Streamer app");
        }
        else if (viewIdClicked == R.id.scoresAppBtn)
        {
            displayMessage("This button will launch my Scores app");
        }
        else if (viewIdClicked == R.id.libraryAppBtn)
        {
            displayMessage("This button will launch my Library app");
        }
        else if (viewIdClicked == R.id.buildItBiggerAppBtn)
        {
            displayMessage("This button will launch my Build it Bigger app");
        }
        else if (viewIdClicked == R.id.xyzReaderAppBtn)
        {
            displayMessage("This button will launch my XYZ Reader app");
        }
        else if (viewIdClicked == R.id.capstoneAppBtn)
        {
            displayMessage("This button will launch my Capstone app");
        }
    }

    private void initViews()
    {
        findViewById(R.id.spotifyStreamerAppBtn).setOnClickListener(this);
        findViewById(R.id.scoresAppBtn).setOnClickListener(this);
        findViewById(R.id.libraryAppBtn).setOnClickListener(this);
        findViewById(R.id.buildItBiggerAppBtn).setOnClickListener(this);
        findViewById(R.id.xyzReaderAppBtn).setOnClickListener(this);
        findViewById(R.id.capstoneAppBtn).setOnClickListener(this);
    }

    private void displayMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
