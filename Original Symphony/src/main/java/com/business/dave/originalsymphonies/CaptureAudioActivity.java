package com.business.dave.originalsymphonies;

import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

//  need to add something where text can be inputted get the text and then
//  just convert it to

//AIzaSyCqPkYGWK6XdvbiKTOa4IpsFgwM8kPtOUM
public class CaptureAudioActivity extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    static final String queryString = "FIND_STRING";


    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private Button exit_button = null;
    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
    private String fileLocation;
   // private TextToSpeech t1;



    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
            /*

            here is where you make the button visible

            */
          //  exit_button.setVisibility(View.VISIBLE);
            //exit_button.setVisibility(View.VISIBLE);
            //mPlayButton.setVisibility(View.VISIBLE);
            theEnd();
            this.finish();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
           // Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);

        Toast.makeText(this, "File Clicked: " + mFileName, Toast.LENGTH_SHORT).show();

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            mRecorder.prepare();
             Toast.makeText(this, "File Clicked:  YEYEYEYE", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "File Clicked: " + e.toString(), Toast.LENGTH_SHORT).show();

          //  Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(this, "File Clicked: " + e.toString(), Toast.LENGTH_SHORT).show();

        }

        System.out.println(mFileName + " Just before the end");
        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    public CaptureAudioActivity() {
      mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        System.out.println(mFileName);
      mFileName += "/audiorecordtest.3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        //Intent intent = getIntent();
        //mFileName = intent.getExtras().getString("message");

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        System.out.println(mFileName);
        mFileName += "/audiorecordtest.3gp";

        LinearLayout ll = new LinearLayout(this);

        mRecordButton = new RecordButton(this);


        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        mPlayButton.setVisibility(View.INVISIBLE);
        exit_button = new Button(this);

        ll.addView(exit_button,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        //exit_button.setVisibility(0);
        exit_button.setText("Exit");
        exit_button.setVisibility(View.INVISIBLE);
        exit_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                String nullStr = null;

                theEnd();
                finish();

            }
        });

        setContentView(ll);

    }
    private void theEnd()
    {
        this.getIntent().putExtra(queryString, mFileName);//suggest a query and then a string. o.getPath(), nullStr);
        setResult(Activity.RESULT_OK, this.getIntent());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}