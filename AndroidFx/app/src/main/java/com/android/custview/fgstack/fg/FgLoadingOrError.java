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
import com.android.custview.fgstack.RequestParam;
import com.android.custview.view.CustomProgressBar;
import com.android.zp.base.BaseFragment;
import com.android.zp.base.KLog;


public class FgLoadingOrError extends BaseFragment implements View.OnClickListener {

    private CustomProgressBar mCustomProgressBar;
    private ConstraintLayout mFailContainer;
    private Button mRetry;
    private Button mBack;
    private RequestParam requestParam;

    @Override
    protected void onDataReceive(Bundle bundle) {
        if (bundle != null) {
            parseBundle(bundle);
        }
    }

    private void parseBundle(Bundle bundle) {
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
        if (bundle.containsKey("data")) {
            requestParam = bundle.getParcelable("data");
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retry:
                //重试回根据不同业务场景
                if (TextUtils.equals(RequestParam.REQUEST_TYPE_POI_LIST, requestParam.getRequestType())) {
                    String search_key = requestParam.getParams()[0];
                    RequestManager.getInstance().requestPoiList(search_key, new RequestManager.RequestCallBack() {
                        @Override
                        public void onRequestEnd(Bundle bundle) {
                            parseBundle(bundle);
                        }
                    });
                }
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

    @Override
    public void onDestroy() {
        RequestManager.getInstance().cancelRequest();
        super.onDestroy();
    }
}
