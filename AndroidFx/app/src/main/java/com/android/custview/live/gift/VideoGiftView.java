package com.android.custview.live.gift;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.android.custview.R;
import com.android.custview.live.gift.module.ConfigModel;
import com.android.custview.live.gift.player.ExoPlayerImpl;
import com.android.custview.live.gift.util.JsonUtil;
import com.android.zp.base.KLog;
import com.ss.ugc.android.alpha_player.IMonitor;
import com.ss.ugc.android.alpha_player.IPlayerAction;
import com.ss.ugc.android.alpha_player.controller.IPlayerController;
import com.ss.ugc.android.alpha_player.controller.PlayerController;
import com.ss.ugc.android.alpha_player.model.AlphaVideoViewType;
import com.ss.ugc.android.alpha_player.model.Configuration;
import com.ss.ugc.android.alpha_player.model.DataSource;

import java.io.File;

public class VideoGiftView extends FrameLayout {

    private RelativeLayout mVideoContainer;

    private IPlayerController mPlayerController;


    public VideoGiftView(@NonNull Context context) {
        this(context, null);
    }

    public VideoGiftView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoGiftView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(getResourceLayout(), this);
        mVideoContainer = findViewById(R.id.video_view);
    }

    public void initPlayerController(Context context, LifecycleOwner owner, IPlayerAction playerAction, IMonitor monitor) {
        Configuration configuration = new Configuration(context, owner);
        configuration.setAlphaVideoViewType(AlphaVideoViewType.GL_TEXTURE_VIEW);
        mPlayerController = PlayerController.Companion.get(configuration, new ExoPlayerImpl(context));
        mPlayerController.setPlayerAction(playerAction);
        mPlayerController.setMonitor(monitor);
    }

    public void startVideoGift(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        ConfigModel configModel = JsonUtil.parseConfigModel(filePath);
        DataSource dataSource = new DataSource()
                .setPortraitPath(configModel.portraitItem.path, configModel.portraitItem.alignMode)
                .setLandscapePath(configModel.landscapeItem.path, configModel.landscapeItem.alignMode);
        dataSource.baseDir = filePath.endsWith(File.separator) ? filePath : (filePath + File.separator);
        startDataSource(dataSource);
    }

    private void startDataSource(DataSource dataSource) {
        if (!dataSource.isValid()) {
            KLog.logE("startDataSource: dataSource is invalid.");
        }
        mPlayerController.start(dataSource);
    }

    public void attachView() {
        mPlayerController.attachAlphaView(mVideoContainer);
    }

    public void detachView() {
        mPlayerController.detachAlphaView(mVideoContainer);
    }

    public void releasePlayerController() {
        mPlayerController.detachAlphaView(mVideoContainer);
        mPlayerController.release();
    }


    private @LayoutRes
    int getResourceLayout() {
        return R.layout.view_video_gift;
    }
}
