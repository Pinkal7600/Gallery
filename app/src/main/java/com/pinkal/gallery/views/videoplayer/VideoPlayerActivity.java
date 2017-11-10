package com.pinkal.gallery.views.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.pinkal.gallery.R;


public class VideoPlayerActivity extends AppCompatActivity {

    //General Variables
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();
    Activity mActivity = VideoPlayerActivity.this;


    //VideoView
    VideoView videoView;

    //ProgressBar
    ProgressBar pbProgress;

    // Insert your Video URL
    String VideoURL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    final int REQUEST_FULL_SCREEN_VIDEO_PLAYING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //Initialize Views
        initializeViews();

        //Initialize Data
        initializeData();


    }


    /**
     * Initialize views
     */
    private void initializeViews() {
        // TODO ... Initialize your views here

        videoView = (VideoView) findViewById(R.id.videoView);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);

        //Initialize toolbar
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setNavigationIcon(R.mipmap.icn_back_black);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //What to do on back clicked
//                finish();
//            }
//        });

    }

    /**
     * Initialize data
     */
    private void initializeData() {
        // TODO ... Initialize your data here

        playVideo();

    }

    /**
     * Playing video with specified url
     */
    public void playVideo() {
        // Show progressbar
        pbProgress.setVisibility(View.VISIBLE);

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoPlayerActivity.this);
            mediacontroller.setAnchorView(videoView);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoView.setMediaController(mediacontroller);
            videoView.setVideoURI(video);


            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    pbProgress.setVisibility(View.GONE);
                    videoView.start();
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_FULL_SCREEN_VIDEO_PLAYING) {
            if (resultCode == Activity.RESULT_OK) {

                pbProgress.setVisibility(View.VISIBLE);
                videoView.seekTo(Integer.parseInt(data.getStringExtra("pos")));
                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        pbProgress.setVisibility(View.GONE);
                        videoView.start();
                    }
                });
            }

        }
    }


    // TODO _______________________( Click Events )_______________________

    //    @OnClick(R.id.ivFullScreen)
    void onClickFullScreen() {
        Intent mIntent = new Intent(this, FullScreenVideoPlayerActivity.class);
        mIntent.putExtra("VideoURL", VideoURL);
        mIntent.putExtra("videoTimingPosition", videoView.getCurrentPosition());
        startActivityForResult(mIntent, REQUEST_FULL_SCREEN_VIDEO_PLAYING);

    }
}
