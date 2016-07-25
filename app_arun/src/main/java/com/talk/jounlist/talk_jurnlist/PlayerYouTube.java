package com.talk.jounlist.talk_jurnlist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by anil on 7/18/2016.
 */
public class PlayerYouTube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;
    private YouTubePlayer player;
    String video_url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_tube_player);
        video_url = getIntent().getStringExtra("video_link");

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyCfbaZUSVCiN27hwHRLp5Q3wLW4PZEOOvY", PlayerYouTube.this);

        if(isNetworkConnected())
        {
            playerStateChangeListener = new MyPlayerStateChangeListener();
            playbackEventListener = new MyPlaybackEventListener();
        }
        else
        {
            Toast.makeText(PlayerYouTube.this , "You Are Not Connected with Internet!!" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored)
    {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored)
        {
            player.cueVideo(video_url); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError())
        {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        }
        else
        {
            //String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize("AIzaSyCfbaZUSVCiN27hwHRLp5Q3wLW4PZEOOvY", this);
        }
    }

    protected Provider getYouTubePlayerProvider()
    {
        return youTubeView;
    }

    private void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener
    {

        @Override
        public void onPlaying()
        {
            // Called when playback starts, either due to user action or call to play().
          //  showMessage("Playing");
        }

        @Override
        public void onPaused()
        {
            // Called when playback is paused, either due to user action or call to pause().
          //  showMessage("Paused");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
         //   showMessage("Stopped");
        }

        @Override
        public void onBuffering(boolean b)
        {
            // Called when buffering starts or ends.
         //   Toast.makeText(PlayerYouTube.this , "Buffering!! Please wait" , Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSeekTo(int i)
        {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener
    {
        @Override
        public void onLoading()
        {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s)
        {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted()
        {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted()
        {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded()
        {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason)
        {
            // Called when an error occurs.
            Toast.makeText(PlayerYouTube.this , "Error Occurring!! Please Try Again" , Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo)
        {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                {
                    haveConnectedWifi = true;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                if (ni.isConnected())
                {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



}
