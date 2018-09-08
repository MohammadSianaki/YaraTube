package com.yaratech.yaratube.ui.player;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.yaratech.yaratube.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";
    private static final String KEY_PRODUCT_FILE = "KEY_PRODUCT_FILE";
    private static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";
    private String videoUrl;
    private SimpleExoPlayer player;
    private long currentPosition = 0;

    @BindView(R.id.player_fragment_player_view)
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        hideStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString(KEY_PRODUCT_FILE) != null) {
            videoUrl = getIntent().getExtras().getString(KEY_PRODUCT_FILE);
        }
        initExoPlayer(videoUrl);

    }

    private void hideStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initExoPlayer(String videoUrl) {

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();
        playerView.requestFocus();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        if (videoUrl != null) {
            HlsMediaSource mediaSource = new HlsMediaSource
                    .Factory(new CacheDataSourceFactory(this))
                    .createMediaSource(Uri.parse(videoUrl));
            player.prepare(mediaSource);

        }
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
        player.seekTo(currentPosition);

    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        player.setPlayWhenReady(true);
        playerView.setPlayer(player);
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: player current pos = ["
                + player.getCurrentPosition() + "]");
        currentPosition = player.getCurrentPosition();
        outState.putLong(KEY_CURRENT_POSITION, player.getCurrentPosition());
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        // player.stop(false);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        releasePlayer();
        super.onDestroy();
    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
