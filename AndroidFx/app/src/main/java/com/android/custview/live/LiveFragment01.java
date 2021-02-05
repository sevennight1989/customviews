package com.android.custview.live;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.android.custview.R;
import com.android.custview.live.gift.VideoGiftView;
import com.android.zp.base.BaseFragment;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.ToastUtils;
import com.ss.ugc.android.alpha_player.IMonitor;
import com.ss.ugc.android.alpha_player.IPlayerAction;
import com.ss.ugc.android.alpha_player.model.ScaleType;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LiveFragment01 extends BaseFragment implements View.OnClickListener {

    private BulletView viewBullet;
    private TextView tvBullet;
    private ImageView ivGoods;
    private LiveCardView viewCard;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private FragmentManager mManager;
    private VideoGiftView video_gift_view;
    private String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        video_gift_view.detachView();
        video_gift_view.releasePlayerController();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void onDataReceive(Bundle bundle) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live_layout_01, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBullet = view.findViewById(R.id.view_bullet);
        viewCard = view.findViewById(R.id.view_card);
        tvBullet = view.findViewById(R.id.tv_bullet);
        ivGoods = view.findViewById(R.id.iv_goods);
        tvBullet.setOnClickListener(this);
        ivGoods.setOnClickListener(this);
        viewBullet.setAdapter(new BulletAdapter());
        viewCard.setOnVisibilityListener(new LiveCardView.Listener() {
            @Override
            public void onCall(int visible) {
                if (visible == View.VISIBLE) {
                    viewBullet.liveCardChange(viewCard.getCardHeight());
                } else {
                    viewBullet.liveCardChange(0);
                }
            }

            @Override
            public void onGiftSend() {
                String testPath = getResourcePath();
                KLog.logE("onGiftSend: " + testPath);
                if ("".equals(testPath)) {
                    ToastUtils.showShort("please run 'gift_install.sh gift/demoRes' for load alphaVideo resource.");
                }
                video_gift_view.startVideoGift(testPath);
            }
        });
        video_gift_view = view.findViewById(R.id.video_gift_view);
        mManager = getParentFragmentManager();
        initVideoGiftView();
        video_gift_view.attachView();
    }

    private void initVideoGiftView() {
        video_gift_view.initPlayerController(mContext, this, playerAction, monitor);
    }

    private IPlayerAction playerAction = new IPlayerAction() {
        @Override
        public void onVideoSizeChanged(int videoWidth, int videoHeight, @NotNull ScaleType scaleType) {
            KLog.logI("call onVideoSizeChanged(), videoWidth = " + videoWidth + " ,videoHeight = " + videoHeight + " , scaleType = " + scaleType);
        }

        @Override
        public void startAction() {
            KLog.logI("startAction");
        }

        @Override
        public void endAction() {
            KLog.logI("endAction");
        }
    };

    private IMonitor monitor = new IMonitor() {
        @Override
        public void monitor(boolean state, @NotNull String playType, int what, int extra, @NotNull String errorInfo) {
            KLog.logE("call monitor(), state: " + state + ", playType = " + playType + " what = " + what + " extra = " + extra + " errorInfo = " + errorInfo);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bullet:
                showInputDialog();
                break;
            case R.id.iv_goods:
                addCard();
                break;

        }
    }

    private void addCard() {
        if (viewCard.getVisibility() == View.VISIBLE) {
            viewCard.dismissCard();
            mHandler.removeCallbacks(null);
        } else {
            viewCard.showCard();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewCard.dismissCard();
                }
            }, 5000);

        }
    }

    private void showInputDialog() {
        InputDialog inputDialog = new InputDialog();
        inputDialog.setKeyboardListener(new InputDialog.KeyboardListener() {
            @Override
            public void onChange(boolean a, int margin) {
                viewBullet.keyboardChange(margin);
            }
        });
        inputDialog.setOnTextSendListener(new InputDialog.TextSendListener() {
            @Override
            public void onChange(String str) {
                Bullet bullet = new Bullet();
                bullet.setUserName("wangzai");
                bullet.setMsgType(Bullet.BULLET_TYPE_NORMAL);
                bullet.setContent(str);
                viewBullet.addMessage(bullet);
            }
        });
        inputDialog.show(mManager, "InputDialog");
    }

    private String getResourcePath() {
        String dirPath = basePath + File.separator + "gift" + File.separator;
        KLog.logI("dirPath : " + dirPath);
        File dirFile = new File(dirPath);
        if (dirFile.exists() && dirFile.listFiles() != null) {
            return dirFile.listFiles()[0].getAbsolutePath();
        }
        return "";
    }
}
