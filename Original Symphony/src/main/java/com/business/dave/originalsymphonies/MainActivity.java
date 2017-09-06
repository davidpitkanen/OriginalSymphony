package com.business.dave.originalsymphonies;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{


    static final int PICK_SONG_REQUEST = 1;  // The request code\
    static final int RECORD_AUDIO_REQUEST = 2;
    static final String queryString = "FIND_STRING";

    private static final int REQUEST_CAMERA = 225;

    private int progress_keeper = 1;
    private String songLocation = "";
    private String recordSampleLocation = "";
    private MediaFormat globalFormat;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to

/*        if (requestCode == PICK_SONG_REQUEST && resultCode == RESULT_OK) {

            String newText = data.getStringExtra(queryString);
            songLocation = newText;
            File outputFile = new File(songLocation);
            String fileName = outputFile.getName();

            Toast.makeText(this, "You Picked a song" , Toast.LENGTH_SHORT).show();

            Button b = (Button)findViewById(R.id.button);
            b.setText("Load Lyrics");


            TextView text_advice = (TextView) findViewById(R.id.editText);
            text_advice.setText("Currently Selected:" + fileName);


            // set the new text for the text box

            Button link_button = (Button) findViewById(R.id.playButton);
            link_button.setText("Reset");


            progress_keeper = 1;


        }
        else */
        if (requestCode == RECORD_AUDIO_REQUEST && resultCode == RESULT_OK )
        {

            progress_keeper = 2;

            String newText = data.getStringExtra(queryString);
            recordSampleLocation = newText;

            Button b = (Button)findViewById(R.id.button);
            b.setText("Get Song");

            Button link_button = (Button) findViewById(R.id.playButton);
            link_button.setText("Reset");


        }
        else{

            //System.out.println("Returned the right result_ok but the activity selection is not working");
        // Make sure the request was successful
        //if (resultCode == RESULT_OK) {
            //String newText = data.getStringExtra(queryString);


            //songLocation = newText;
            //File outputFile = new File(songLocation);
            //recordSampleLocation = outputFile.getParent();
            //recordSampleLocation = recordSampleLocation.substring(1) + "/recordedSong.3gp";

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("Permission was proceeding");
        if (requestCode == REQUEST_CAMERA) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            //Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Camera permission has been granted, preview can be displayed

              //  takePicture();

            } else {
                //Permission not granted
                Toast.makeText(MainActivity.this,"You need to grant these permissions to use record, store and write audio",Toast.LENGTH_LONG).show();
            }

        }
    }



    public static boolean isAudioFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        System.out.println(mimeType);
        return mimeType != null && mimeType.startsWith("audio");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void launchFinder(View view) {
        if (checkPermissions()) {
            switch(progress_keeper) {
             /*   case 0 :
                    Intent pickSongIntent = new Intent(this, ChooseActivity.class);
                    // pickSongIntent.setType("text/plain");
                    startActivityForResult(pickSongIntent, PICK_SONG_REQUEST);
                    break; // optional
*/
                case 1 :
                    Intent recordAudioIntent = new Intent(this, CaptureAudioActivity.class);
                    // pickSongIntent.setType("text/plain");
                    startActivityForResult(recordAudioIntent, RECORD_AUDIO_REQUEST);
                    break; // optional
                case 2:


                    Intent youtubeIntent = new Intent(this, YouTubeActivity.class);
                    String message = null;
                    youtubeIntent.putExtra("message", recordSampleLocation);
                    // pickSongIntent.setType("text/plain");
                    startActivity(youtubeIntent);
            /*
            Intent intent = new Intent(this, CaptureAudioActivity.class);
            String message = null;
            intent.putExtra("message", recordSampleLocation);
            startActivity(intent);

                /*
                    isAudioFile(recordSampleLocation);
                    isAudioFile(songLocation);
                    Intent intent = new Intent(this, playMusicActivity.class);
                    String message = null;
                    intent.putExtra("message", recordSampleLocation + "," + songLocation);
                    startActivity(intent);
                    break;

                // You can have any number of case statements.
                default : // Optional
                    // Statements
                    */

            }

        }
    }

    private boolean checkPermissions()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                Toast.makeText(getBaseContext(), "Without the read/write and record permissions no song can be made.", Toast.LENGTH_LONG).show();

                //showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
/*
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CAMERA);// MY_PERMISSIONS_REQUEST_READ_CONTACTS);
*/
            }
            else
            {

                // No explanation needed, we can request the permission.


                System.out.println("About to make the call");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET},
                        REQUEST_CAMERA);
            }
            return false;

        }
        else
        {
            return true;
        }

    }


    //@TargetApi(16)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void launchMaker(View view)
    {
        if(checkPermissions()) {
            // here you have to make sure that a song has been selected. Q How to do this.
            //File locationFile = new File(songLocation.substring(1));
            //String newLocation = locationFile.getParent();

            isAudioFile(recordSampleLocation);
            isAudioFile(songLocation);
            Intent intent = new Intent(this, playMusicActivity.class);
            String message = null;
            intent.putExtra("message", recordSampleLocation + "," + songLocation);
            startActivity(intent);
        }

    }

    public void gotoGooglePlay(View view) {
        if (checkPermissions()) {
            switch (progress_keeper) {
              /*  case 0:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://search?q=Barbara+Streisand+Duck+Sauce"));
                    startActivity(intent);
                    break; // optional
               */
                case 1:
                    // in this case reset everything
                    this.finish();
                case 2:
                    resetSettings();

                    break;
                default:

            }

        }
    }

    private void resetSettings()
    {
        progress_keeper = 1;
        songLocation = "";
        recordSampleLocation = "";

        Button b = (Button)findViewById(R.id.button);
        b.setText("Load Lyrics");


        // set the new text for the text box

        Button link_button = (Button) findViewById(R.id.playButton);
        link_button.setText("Exit");
    }


    public void youtubePlay(View view) {


        if (checkPermissions()) {

            Intent youtubeIntent = new Intent(this, YouTubeActivity.class);
            String message = null;
            youtubeIntent.putExtra("message", recordSampleLocation);
            // pickSongIntent.setType("text/plain");
            startActivity(youtubeIntent);
            /*
            Intent intent = new Intent(this, CaptureAudioActivity.class);
            String message = null;
            intent.putExtra("message", recordSampleLocation);
            startActivity(intent);
            */
        }
        // what you could do is try to play the audio from here once the activity ends

        // where to store this?
    }


    public void launchAudioCapture(View view) {


        if (checkPermissions()) {

            Intent recordAudioIntent = new Intent(this, CaptureAudioActivity.class);
            // pickSongIntent.setType("text/plain");
            startActivityForResult(recordAudioIntent, RECORD_AUDIO_REQUEST);
            /*
            Intent intent = new Intent(this, CaptureAudioActivity.class);
            String message = null;
            intent.putExtra("message", recordSampleLocation);
            startActivity(intent);
            */
        }
        // what you could do is try to play the audio from here once the activity ends

        // where to store this?
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.business.dave.originalsymphonies/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.business.dave.originalsymphonies/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}