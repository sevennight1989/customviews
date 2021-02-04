package com.android.custview.live;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.custview.R;

public class InputDialog extends DialogFragment implements View.OnClickListener {

    private InputMethodManager imm;
    private FrameLayout viewContainer;
    private EditText etContent;
    private TextView tvSend;
    private int maxNumber = 30;

    // 窗口的默认可见高度
    private int windowDefaultVisibleHeight = 0;

    private Rect outRect = new Rect();
    // 用于跳出回调事件
    private boolean isKeyboardShow = false;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                if (textSendListener != null) {
                    textSendListener.onChange(etContent.getText().toString());
                    etContent.setText("");
                    keyboardListener.onChange(false, 0);
                    dismiss();
                }
                break;

            case R.id.view_container:
                isKeyboardShow = false;
                keyboardListener.onChange(false, 0);
                dismiss();
                break;
        }
    }


    interface TextSendListener {
        void onChange(String str);
    }

    interface KeyboardListener {
        void onChange(boolean a, int margin);
    }

    private TextSendListener textSendListener;

    private KeyboardListener keyboardListener;

    public void setOnTextSendListener(TextSendListener listener) {
        this.textSendListener = listener;
    }

    public void setKeyboardListener(KeyboardListener listener) {
        this.keyboardListener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.InputDialog);
        imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreWindow();
        return inflater.inflate(R.layout.fragment_input_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        showSoftInput();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        keyboardListener = null;
    }

    private void restoreWindow() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            if(window != null){
                window.getDecorView().setPadding(0,0,0,0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.gravity = Gravity.BOTTOM;
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
            }

        }

    }

    private void initView(View view) {
        viewContainer = view.findViewById(R.id.view_container);
        etContent = view.findViewById(R.id.et_content);
        tvSend = view.findViewById(R.id.tv_send);
        etContent.getBackground().setColorFilter(new PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR));
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > maxNumber) {
                    etContent.setText(s.subSequence(0, maxNumber - 1));
                    etContent.setSelection(etContent.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvSend.setEnabled(!TextUtils.isEmpty(s));
            }
        });
        tvSend.setOnClickListener(this);
        viewContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (windowDefaultVisibleHeight == 0) {
                    windowDefaultVisibleHeight = getWindowVisibleHeight();
                }
                if (isKeyboardShow() && !isKeyboardShow) {
                    isKeyboardShow = true;
                    if (keyboardListener != null) {
                        keyboardListener.onChange(true, getBottomMargin());
                    }
                }
                if (!isKeyboardShow() && isKeyboardShow) {
                    isKeyboardShow = false;
                    if (keyboardListener != null) {
                        keyboardListener.onChange(false, 0);
                    }
                    if (getDialog().isShowing()) {
                        dismiss();
                    }
                }
            }
        });
        viewContainer.setOnClickListener(this);
    }

    private void showSoftInput() {
        etContent.post(new Runnable() {
            @Override
            public void run() {
                etContent.requestFocus();
                imm.showSoftInput(etContent, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    // 获取输入框距离屏幕底部的距离
    private int getBottomMargin() {
        return isKeyboardShow() ? getKeyboardHeight() + etContent.getHeight() : 0;
    }

    // 键盘的高度
    private int getKeyboardHeight() {
        return windowDefaultVisibleHeight - getWindowVisibleHeight();
    }

    // 键盘是否弹起
    private boolean isKeyboardShow() {
        return windowDefaultVisibleHeight - getWindowVisibleHeight() > 0;
    }

    private int getWindowVisibleHeight() {
        getDialog().getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.bottom;
    }
}
