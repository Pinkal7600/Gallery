package com.pinkal.gallery.views.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.pinkal.gallery.R;


public class FullScreenVideoPlayerActivity extends AppCompatActivity implements View.OnTouchListener {

    //General Variables
    private static final String TAG = FullScreenVideoPlayerActivity.class.getSimpleName();
    Activity mActivity = FullScreenVideoPlayerActivity.this;

    //VideoView
    VideoView videoView;

    //ProgressBar
    ProgressBar pbProgress;

    //SeekBar
    SeekBar seekBar;

    //LinearLayout
    LinearLayout llMediaController;
    LinearLayout llVolume;
    LinearLayout llBrightness;

    //ImageViews
    ImageView ivPlay;

    //TextViews
    TextView tvStartTime;
    TextView tvEndTime;
    TextView tvVolume;
    TextView tvBrightness;

    //String Data
    String VideoURL = "";

    //Integer Data
    int videoTimingPosition = 0, currentVolumePercentage = 0, maxVolume = 0, currentBrightnessPercentage = 100, min_distance = 200;

    //Float Data
    float maxBrightness = 1.0f, currentBrightness = 1.0f, downX = 0, downY = 0, currentX = 0, currentY = 0;

    //Audio manager for controlling volume
    AudioManager audioManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video_player);

        //Initialize Views
        initializeViews();

        //Initialize Data
        initializeData();

        //Various events of views
        events();

    }


    /**
     * Initialize views
     */
    private void initializeViews() {
        // TODO ... Initialize your views here


        videoView = (VideoView) findViewById(R.id.videoView);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        llMediaController = (LinearLayout) findViewById(R.id.llMediaController);
        llVolume = (LinearLayout) findViewById(R.id.llVolume);
        llBrightness = (LinearLayout) findViewById(R.id.llBrightness);
        ivPlay = (ImageView) findViewById(R.id.ivPlay);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvVolume = (TextView) findViewById(R.id.tvVolume);
        tvBrightness = (TextView) findViewById(R.id.tvBrightness);

        videoView.setOnTouchListener(this);

    }


    /**
     * Initialize data
     */
    private void initializeData() {
        // TODO ... Initialize your data here

        //Get data from intent
        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("VideoURL")) {
            VideoURL = b.getString("VideoURL");
            videoTimingPosition = b.getInt("position");
        }

        //Initialize Audio Manager for volume control
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //Set current volume
        currentVolumePercentage = getPercentageFromVolume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        tvVolume.setText(currentVolumePercentage + " %");

        // Make the screen full bright for this activity.
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = 1.0f;
        getWindow().setAttributes(lp);
        currentBrightnessPercentage = 100;

        //Set current brightness
        tvBrightness.setText(currentBrightnessPercentage + " %");

        //Play Video
        playVideo();
    }

    /**
     * Various events of views
     */
    private void events() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvStartTime.setText(milliSecondsToTimer(videoView.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tvStartTime.setText(milliSecondsToTimer(videoView.getCurrentPosition()));
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if (fromUser) {
                    // this is when actually seekbar has been seeked to a new videoTimingPosition
                    videoView.seekTo(progress);
                    tvStartTime.setText(milliSecondsToTimer(videoView.getCurrentPosition()));
                }
            }
        });
    }

    /**
     * Playing video with specified url
     */
    public void playVideo() {

        // Show progressbar
        pbProgress.setVisibility(View.VISIBLE);

        try {
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoView.setVideoURI(video);
            videoView.seekTo(videoTimingPosition);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {

                    // Dismiss progressbar
                    pbProgress.setVisibility(View.GONE);

                    //Set timing
                    tvStartTime.setText(milliSecondsToTimer(videoView.getCurrentPosition()));
                    tvEndTime.setText(milliSecondsToTimer(videoView.getDuration()));

                    //Initialize seek bar values
                    seekBar.setMax(videoView.getDuration());
                    seekBar.postDelayed(onEverySecond, 1000);

                    //Start Video
                    videoView.start();
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Convert milli seconds into timer
     */
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Update seekbar progress at every 1 section
     */
    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {

            tvStartTime.setText(milliSecondsToTimer(videoView.getCurrentPosition()));

            if (seekBar != null) {
                seekBar.setProgress(videoView.getCurrentPosition());
            }

            if (videoView.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        if (view.getId() == R.id.videoView) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN: {

                    downX = event.getX();
                    downY = event.getY();

//                    AppLog.debugD(TAG, "downX : " + event.getX());
//                    AppLog.debugD(TAG, "downY : " + event.getY());

                    if (llMediaController.getVisibility() == View.VISIBLE) {
                        llMediaController.setVisibility(View.GONE);
                    } else {
                        llMediaController.setVisibility(View.VISIBLE);
                    }

                    return true;
                }
                case MotionEvent.ACTION_UP: {

                    llVolume.setVisibility(View.GONE);
                    llBrightness.setVisibility(View.GONE);

                    return true;
                }
                case MotionEvent.ACTION_MOVE:

                    currentX = event.getX();
                    currentY = event.getY();

                    float deltaX = downX - currentX;
                    float deltaY = downY - currentY;

                    if (Math.abs(deltaY) > min_distance) {

                        if (currentX < centerX) {

                            //TODO.. Left side touch events on screen

                            llBrightness.setVisibility(View.VISIBLE);
                            if (deltaY < 0) {
                                //TODO do your action for Top to Bottom touch on Left Side
//                                AppLog.debugD(TAG, "Top To Bottom Left Side");

                                if (currentBrightnessPercentage > 0) {
                                    currentBrightnessPercentage--;

                                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                                    lp.screenBrightness = getBrightnessFromPercentage(currentBrightnessPercentage);
                                    getWindow().setAttributes(lp);

                                    tvBrightness.setText(currentBrightnessPercentage + " %");
                                }

                                return true;
                            }
                            if (deltaY > 0) {
                                //TODO do your action for Bottom to Top touch on Left Side
//                                AppLog.debugD(TAG, "Bottom To Top Left Side");

                                if (currentBrightnessPercentage < 100) {
                                    currentBrightnessPercentage++;

                                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                                    lp.screenBrightness = getBrightnessFromPercentage(currentBrightnessPercentage);
                                    getWindow().setAttributes(lp);

                                    tvBrightness.setText(currentBrightnessPercentage + " %");
                                }
                                return true;
                            }

                        } else {

                            //TODO.. Right side touch events on screen
                            llVolume.setVisibility(View.VISIBLE);

                            if (Math.abs(deltaY) > min_distance) {
                                if (deltaY < 0) {

                                    //TODO do your action for Top to Bottom touch on Right Side
//                                    AppLog.debugD(TAG, "Top To Bottom Right Side");


                                    // Write your code to perform an action on contineus touch move
                                    if (currentVolumePercentage > 0) {

                                        currentVolumePercentage--;
                                        tvVolume.setText(currentVolumePercentage + " %");
                                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                getVolumeFromPercentage(currentVolumePercentage), 0);
                                    }

                                    return true;
                                }
                                if (deltaY > 0) {
                                    //TODO do your action for Bottom to Top touch on Right Side
//                                    AppLog.debugD(TAG, "Bottom To Top Right Side");

                                    if (currentVolumePercentage < 100) {

                                        currentVolumePercentage++;
                                        tvVolume.setText(currentVolumePercentage + " %");
                                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                getVolumeFromPercentage(currentVolumePercentage), 0);
                                    }
                                    return true;
                                }
                            }
                        }
                    }

                    if (Math.abs(deltaX) > min_distance) {

                        if (deltaX < 0) {
                            //TODO do your action for left to right touch
//                            AppLog.debugD(TAG, "left to right");

                            return true;
                        }
                        if (deltaX > 0) {
                            //TODO do your action for right to left touch
//                            AppLog.debugD(TAG, "Right to left");

                            return true;
                        }
                    }
            }
        }

        return true;
    }

    public int getPercentageFromBrithness(float currentBrightness) {

        int percentage = (int) Math.abs(((currentBrightness * 100)));

//        AppLog.debugD(TAG, "currentBrightness" + currentBrightness);
//        AppLog.debugD(TAG, "Brightness Percentage " + percentage);


        return percentage;
    }

    public float getBrightnessFromPercentage(int percentage) {

        float currentBrightness = Math.abs(((percentage * maxBrightness) / 100));

//        AppLog.debugD(TAG, "currentBrightness " + currentBrightness);
//        AppLog.debugD(TAG, "Brightness Percentage " + percentage);


        return currentBrightness;
    }

    public int getPercentageFromVolume(int currentVolume) {

        int percentage = Math.abs(((currentVolume * 100) / maxVolume));

//        AppLog.debugD(TAG, "maxVolume " + maxVolume);
//        AppLog.debugD(TAG, "currentVolume " + currentVolume);
//        AppLog.debugD(TAG, "Volume Percentage " + percentage);


        return percentage;
    }


    public int getVolumeFromPercentage(int percentage) {

        int currentVolume = Math.abs(((percentage * maxVolume) / 100));

//        AppLog.debugD(TAG, "maxVolume " + maxVolume);
//        AppLog.debugD(TAG, "currentVolume " + currentVolume);
//        AppLog.debugD(TAG, "Volume Percentage " + percentage);


        return currentVolume;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("pos", "" + videoView.getCurrentPosition());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    // TODO _______________________( Click Events )_______________________

    //    @OnClick(R.id.ivFullScreen)
    void onClickFullScreen() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("pos", "" + videoView.getCurrentPosition());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    //    @OnClick(R.id.ivPlay)
    void onClickPlayPause() {

        if (videoView.isPlaying()) {
            ivPlay.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_play));
            videoView.pause();
        } else {
            ivPlay.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_pause));
            videoView.start();
            seekBar.postDelayed(onEverySecond, 1000);
        }

    }
}
