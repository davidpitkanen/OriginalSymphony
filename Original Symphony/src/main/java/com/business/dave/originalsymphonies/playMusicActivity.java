package com.business.dave.originalsymphonies;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class playMusicActivity extends AppCompatActivity {

    //private MediaPlayer m1Player;
    //private MediaPlayer m2Player;
    //private Thread h;

    private boolean started = false;
    private String locations1 = "";
    private String locations2 = "";
    DownloadFilesTask ttask = new DownloadFilesTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String locations = intent.getExtras().getString("message");
        locations1 = locations.split(",")[0];
        locations2 = locations.split(",")[1].substring(1);

        setContentView(R.layout.activity_play_music);
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
    }

    private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... songTitle) {
            int[] barbLocations = new int[]{-3, 12, 28, 50, 80, 110, 126, 141, 176};
            String songLocation = songTitle[1];
            String recordSampleLocation = songTitle[0];
            for (int i = 0; i < barbLocations.length - 1; i++) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                MediaPlayer mediaPlayer2 = new MediaPlayer();

                playSong(songLocation.substring(1), mediaPlayer, barbLocations[i] + 3);
                try {

                    for(int j = 0; j <  (barbLocations[i + 1] - barbLocations[i] - 3);j++ ) {

                        System.out.println(mediaPlayer.getCurrentPosition());

                        if (isCancelled()) {
                            mediaPlayer.stop();
                            //System.out.println("Trying to stop the player");
                            break;
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stopSong(mediaPlayer);
                if (isCancelled()) break;
                playSong(recordSampleLocation, mediaPlayer2, 0);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (isCancelled()) break;
                stopSong(mediaPlayer2);

            }
            return null;
        }

        private void playSong(String songKey, MediaPlayer mPlayer, int seekTo)
        {
            System.out.println(locations1 + "  " + locations2);

            Uri myUri = Uri.fromFile(new File(songKey));

            try {
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(getApplicationContext(), myUri);
                mPlayer.prepare();
                // mediaPlayer.start();
                mPlayer.seekTo(1000*seekTo);
                mPlayer.start();
            }
            catch(IOException e)
            {
                e.toString();
            }

        }

        private void stopSong(MediaPlayer mPlayer)
        {
            mPlayer.stop();
        }


        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

    public void playSong(View view) {

            if (ttask.getStatus() != AsyncTask.Status.RUNNING) {
                ttask.execute(new String[]{locations1, locations2});
            }

    }

    public void stopSong(View view) {
     stopCall();
    }

    private void stopCall()
    {
        ttask.cancel(true);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
       stopCall();
    }

}
