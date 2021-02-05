package com.android.custview.live.gift.player;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.ss.ugc.android.alpha_player.model.VideoInfo;
import com.ss.ugc.android.alpha_player.player.AbsPlayer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ExoPlayerImpl extends AbsPlayer {

    private SimpleExoPlayer exoPlayer;
    private DefaultDataSourceFactory dataSourceFactory;
    private MediaSource videoSource;
    private int currVideoWidth = 0;
    private int currVideoHeight = 0;
    private boolean isLooping = false;

    private Context context;

    public ExoPlayerImpl(@Nullable Context context) {
        super(context);
        this.context = context;
        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "player"));
    }

    @NotNull
    @Override
    public String getPlayerType() {
        return "ExoPlayerImpl";
    }

    @NotNull
    @Override
    public VideoInfo getVideoInfo() throws Exception {
        return new VideoInfo(currVideoWidth, currVideoHeight);
    }

    @Override
    public void initMediaPlayer() throws Exception {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        exoPlayer.addListener(exoPlayerListener);
        exoPlayer.addVideoListener(exoVideoListener);
    }

    private Player.EventListener exoPlayerListener = new Player.EventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_READY:
                    if (playWhenReady) {
                        getPreparedListener().onPrepared();
                    }
                    break;

                case Player.STATE_ENDED:
                    getCompletionListener().onCompletion();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            getErrorListener().onError(0, 0, "ExoPlayer on error: " + Log.getStackTraceString(error));
        }
    };

    private VideoListener exoVideoListener = new VideoListener() {
        @Override
        public void onRenderedFirstFrame() {
            getFirstFrameListener().onFirstFrame();
        }

        @Override
        public void onSurfaceSizeChanged(int width, int height) {
            currVideoWidth = width;
            currVideoHeight = height;
        }
    };

    @Override
    public void pause() {
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void prepareAsync() {
        exoPlayer.prepare(videoSource);
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void release() {
        exoPlayer.release();
    }

    @Override
    public void reset() {
        exoPlayer.stop(true);
    }

    @Override
    public void setDataSource(@NotNull String dataPath) throws IOException {
        if (isLooping) {
            ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(dataPath));
            videoSource = new LoopingMediaSource(extractorMediaSource);
        } else {
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(dataPath));
        }
        reset();
    }

    @Override
    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    @Override
    public void setScreenOnWhilePlaying(boolean b) {

    }

    @Override
    public void setSurface(@NotNull Surface surface) {
        exoPlayer.setVideoSurface(surface);
    }

    @Override
    public void start() {
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() {
        exoPlayer.stop();
    }
}
