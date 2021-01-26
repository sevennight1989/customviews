package com.android.custview.fgstack.fg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.custview.R;
import com.android.custview.fgstack.RequestManager;
import com.android.custview.view.CustomProgressBar;
import com.android.zp.base.BaseFragment;
import com.android.zp.base.KLog;


public class FgLoadingOrError extends BaseFragment implements View.OnClickListener {

    private CustomProgressBar mCustomProgressBar;
    private ConstraintLayout mFailContainer;
    private Button mRetry;
    private Button mBack;
    private String search_key;

    @Override
    protected void onDataReceive(Bundle bundle) {
        if (bundle != null) {
            String value = bundle.getString(RequestManager.ACTION_KEY, "");
            KLog.logE("Fg04 onDataReceive: " + value);
            switch (value) {
                case RequestManager.ACTION_LOADING:
                    showLoading();
                    break;
                case RequestManager.ACTION_SUCCESS:
                    showSuccess();
                    break;
                case RequestManager.ACTION_FAILED:
                    showFailed();
                    break;

            }
            String tempSearchKey = bundle.getString("search_key", "");
            KLog.logI("tempSearchKey: " + tempSearchKey);
            if (!TextUtils.isEmpty(tempSearchKey)) {
                search_key = tempSearchKey;
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KLog.logE("onCreateView");
        return inflater.inflate(R.layout.fg_loading_or_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        KLog.logE("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        mCustomProgressBar = view.findViewById(R.id.cust_pb);
        mFailContainer = view.findViewById(R.id.fail_container);
        mRetry = view.findViewById(R.id.retry);
        mBack = view.findViewById(R.id.back);
        mRetry.setOnClickListener(this);
        mBack.setOnClickListener(this);
        showLoading();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retry:
                RequestManager.getInstance().requestPoiList(search_key);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void showLoading() {
        mCustomProgressBar.setVisibility(View.VISIBLE);
        mFailContainer.setVisibility(View.INVISIBLE);
    }

    private void showFailed() {
        mCustomProgressBar.setVisibility(View.INVISIBLE);
        mFailContainer.setVisibility(View.VISIBLE);
    }

    private void showSuccess() {
        mCustomProgressBar.setVisibility(View.INVISIBLE);
        mFailContainer.setVisibility(View.INVISIBLE);
        Bundle bundle = new Bundle();
        bundle.putString("key", "update to 02");
        finish();
        add(Fg02.class, bundle);
    }
}
