package com.android.custview.live;

import android.os.Bundle;
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
import com.android.zp.base.BaseFragment;

public class LiveFragment01 extends BaseFragment implements View.OnClickListener {

    private BulletView viewBullet;
    private TextView tvBullet;
    private ImageView ivGoods;
    private LiveCardView viewCard;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private FragmentManager mManager;

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
        });
        mManager = getParentFragmentManager();
    }

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
}
