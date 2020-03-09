package com.android.custview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.custview.R;


public class SpeedLimitLayout extends RelativeLayout {

    private TextView mLimitSpeedTx;
    private TextView mintervalSpeedTx;
    private TextView mRemainMiles;
    private Context mContext;

    private View root;
    public SpeedLimitLayout(Context context) {
        this(context,null);
    }

    public SpeedLimitLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpeedLimitLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        root = LayoutInflater.from(mContext).inflate(R.layout.speed_limit_layout,this);
        initViews();
    }

    private void initViews(){
        mLimitSpeedTx = root.findViewById(R.id.speed_limit);
        mintervalSpeedTx = root.findViewById(R.id.intervalSpeed);
        mRemainMiles = root.findViewById(R.id.remain_miles);
    }

    public void updateData(String limitSpeed,String intervalSpeed,String remainMiles){
        mLimitSpeedTx.setText(limitSpeed);
        mintervalSpeedTx.setText(intervalSpeed);
        mRemainMiles.setText(remainMiles);
    }
}
