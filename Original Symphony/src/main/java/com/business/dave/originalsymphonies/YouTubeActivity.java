package com.business.dave.originalsymphonies;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.io.IOException;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    Button b;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer playing;
    private String locationRecording = "";
    private HelloRunnable threadRun;
    public static final String VIDEO_ID = "WK4HHaNhcgU";
    private communicationBuffer commDevice = new communicationBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.view);
        youTubePlayerView.initialize("AIzaSyCqPkYGWK6XdvbiKTOa4IpsFgwM8kPtOUM", this);

        Intent intent = getIntent();
        String locations = intent.getExtras().getString("message");
        locationRecording = locations;

    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if(null== player) return;

        // Start buffering
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
        else this.finish();


        playing = player;
       /* playing.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onBuffering(boolean b)
            {

            }

            public void onStopped()
            {

            }
            public void onSeekTo(int i)
            {

            }

            public void onPlaying()
            {

            }

            public void onPaused()
            {

            }


        });
        */
        // here you create an asynctask and pass it the
//        threadRun.run();

    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        this.closeActivity();
    }
    @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");

        closeActivity();
    }
    private void closeActivity()
    {
        commDevice.setToStop();
        try {
            Thread.sleep(200);
        }
        catch(InterruptedException e)
        {

        }
        this.finish();
    }

    public void replaceLyric(View view)
    {
        // the below was meant to preven the device from crashing when
        //
        if (!playing.isPlaying() && commDevice.shouldStop)
        {
            // then I have to press play on the video.
            playing.play();
        }

        if (commDevice.shouldStop) {
            commDevice.setToStart();
            (new Thread(new HelloRunnable(playing, locationRecording, commDevice))).start();
            Button b = (Button)findViewById(R.id.replace_button);
            b.setText("Reset");
        }
        else
        {
            closeActivity();
        }

    }

    public class communicationBuffer
    {
        private boolean shouldStop = true;

        public boolean getStatus()
        {
            return shouldStop;
        }

        public void setToStop()
        {
            shouldStop = true;
        }

        public void setToStart()
        {
            shouldStop = false;
        }

    }

    private class HelloRunnable implements Runnable {
        private YouTubePlayer playing;
        //private int[] barbLocations = new int[]{-3, 12, 28, 50, 80, 110, 126, 141, 176};
        private int[] barbLocations = new int[]{58, 73, 88, 110, 186, 201, 216, 231};
        private String songLocation = "";
        private MediaPlayer usersVoicePlayer = new MediaPlayer();
        private communicationBuffer commBuff;

        public HelloRunnable (YouTubePlayer p1, String location, communicationBuffer buff)
        {
            commBuff = buff;
            playing = p1;
            songLocation = location;
            Uri myUri = Uri.fromFile(new File(location));
            usersVoicePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try
            {
                usersVoicePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                usersVoicePlayer.setDataSource(getApplicationContext(), myUri);
            }
            catch(IOException e)
            {
                e.toString();
            }
        }

        private boolean checkIntervals(int position)
        {
            for (int barb: barbLocations)
            {   //System.out.println( "" + (position/100 > 2 + 10* barb && position/100 < 2 + barb * 10 + 20));
                if (position/100 > -1 + 10* barb && position/100 < 2 + barb* 10 + 20 )
                {
                    return true;
                }
            }
            return false;
        }

        public void run() {

        //for (int i = 0; i < barbLocations.length - 1; i++) {
        //MediaPlayer usersVoicePlayer = new MediaPlayer();
        //playing.seekToMillis(1000*50);
        try {
            int counter = 0;
            while(true)//playing.getCurrentTimeMillis() < (4*60 + 27)*1000 || counter < 60*10*10)
            {
                if (commBuff.getStatus())
                {
                    break;
                }
                Thread.sleep(100);
                System.out.println(playing.getCurrentTimeMillis() / 1000);

                if (checkIntervals(playing.getCurrentTimeMillis()) && playing.isPlaying())
                {
                    counter++;
                    playing.pause();
                    playing.seekToMillis(playing.getCurrentTimeMillis() + 3 * 1000);
                    this.playSong(songLocation, usersVoicePlayer);
                    Thread.sleep(3000);
                    this.stopSong(usersVoicePlayer);
                    playing.play();
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    //}


    }

    private void playSong(String songKey, MediaPlayer mPlayer)
    {

        //Uri myUri = Uri.fromFile(new File(songKey));

        try {
            mPlayer.prepare();
            // mediaPlayer.start();
            //mPlayer.seekTo(1000*seekTo);
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



    }

}