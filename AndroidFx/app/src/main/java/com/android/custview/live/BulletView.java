package com.android.custview.live;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;
import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class BulletView extends FrameLayout {

    // 列表最大展示的消息数量
    private static final int MAX_COUNT_DEFAULT = 100;

    // 弹幕公告
    private static final String DEFAULT_BULLET = "欢迎来到直播间～我们提倡绿色的直播，直播内容和评论严禁出现违法违规、低俗色情、涉政、涉恐、涉群体性事件等内容。请文明观看和互动哦，购买商品请勿轻信其他广告信息，以免上当受骗！";


    // 输入框距离底部的默认高度
    private int BULLET_DEFAULT_MARGIN = SizeUtils.dp2px(68);

    // 输入框距离底部的高度
    private float currentTranslationY = 0f;
    private boolean isKeyboardShow = false;


    private BulletRecyclerView recyclerView;
    private TextView bulletCountView;
    private LinearLayoutManager layoutManager;
    private BulletAdapter listAdapter = null;

    // 还未刷新的消息集合
    private List<Bullet> newMessageList = new ArrayList<>();


    public BulletView(@NonNull Context context) {
        this(context, null);
    }

    public BulletView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BulletView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        LayoutInflater.from(context).inflate(R.layout.layout_bullet, this, true);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.bullet_recycler);
        bulletCountView = findViewById(R.id.tv_bullet_count);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 滑动到底部有新的消息执行刷新
                    if (listAdapter != null) {
                        if (layoutManager.findLastVisibleItemPosition() == listAdapter.getListData().size() - 1 && newMessageList.size() > 0) {
                            fixUnreadMessage();
                        }
                    }
                }
            }
        });
        bulletCountView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fixUnreadMessage();
            }
        });
    }

    public void addMessage(Bullet bullet) {
        if (listAdapter != null) {
            if (listAdapter.getListData().size() == 0 || layoutManager.findLastVisibleItemPosition() == listAdapter.getListData().size() - 1) {
                // 如果当前显示最后一条，刷新消息列表
                List<Bullet> dataSource = listAdapter.getListData();
                int size = dataSource.size();
                if (size >= MAX_COUNT_DEFAULT) {
                    dataSource.add(bullet);
                    dataSource.remove(1);
                } else {
                    dataSource.add(bullet);
                }
                listAdapter.notifyDataSetChanged();
                layoutManager.scrollToPosition(dataSource.size() - 1);
            } else {
                newMessageList.add(bullet);
                setUnreadMessageCount();
            }
        }
    }

    public void setAdapter(BulletAdapter bulletAdapter) {
        listAdapter = bulletAdapter;
        // 添加弹幕公告
        Bullet bullet = new Bullet();
        bullet.setContent(DEFAULT_BULLET);
        bullet.setMsgType(Bullet.BULLET_TYPE_NOTICE);
        listAdapter.addData(bullet);
        recyclerView.setAdapter(listAdapter);
    }

    // 合并未读的消息
    private void fixUnreadMessage() {
        List<Bullet> dataSource = listAdapter.getListData();
        dataSource.addAll(newMessageList);
        if (dataSource.size() > MAX_COUNT_DEFAULT) {
            dataSource.subList(1, dataSource.size() - MAX_COUNT_DEFAULT).clear();
        }
        listAdapter.notifyDataSetChanged();
        layoutManager.scrollToPosition(listAdapter.getListData().size() - 1);
        newMessageList.clear();
        setUnreadMessageCount();
    }

    private void setUnreadMessageCount() {
        int size = newMessageList.size();
        if (size > 0) {
            bulletCountView.setVisibility(VISIBLE);
            bulletCountView.setText(size > 99 ? "99+" : size + "");
        } else {
            bulletCountView.setVisibility(GONE);
        }
    }

    public void liveCardChange(int carHeight) {
        currentTranslationY = -carHeight;
        if (isKeyboardShow) {
            return;
        }
        translation(currentTranslationY);
    }

    public void keyboardChange(int height) {
        if (height > 0) {
            isKeyboardShow = true;
            int y = BULLET_DEFAULT_MARGIN - height;
            translation(y);
        } else {
            isKeyboardShow = false;
            translation(currentTranslationY);
        }
    }

    private void translation(float y) {
        animate().translationY(y)
                .setDuration(200)
                .start();
    }
}
