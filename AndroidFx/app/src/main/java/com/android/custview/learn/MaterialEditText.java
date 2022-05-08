package com.android.custview.learn;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.android.custview.R;
import com.android.zp.base.KLog;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class MaterialEditText extends AppCompatEditText {

    private static final float TEXT_SIZE = CommonUtils.dp2Px(10);

    private static final float TEXT_MARGIN = CommonUtils.dp2Px(10);

    private static final float TEXT_VERTICAL_OFFSET = CommonUtils.dp2Px(20);
    private static final float TEXT_HORIZONTAL_OFFSET = CommonUtils.dp2Px(5);
    private static final float TEXT_ANIMATION_OFFSET = CommonUtils.dp2Px(16);

    private boolean floatingLabelShow = false;

    private ObjectAnimator animator1;
    private final Rect backgroundPadding = new Rect();

    public boolean isUseFloatingLabel() {
        return useFloatingLabel;
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            onUseFloatingLabelChange();
        }
    }

    private void onUseFloatingLabelChange() {
        getBackground().getPadding(backgroundPadding);
        if (useFloatingLabel) {
            setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
        }
    }

    private boolean useFloatingLabel = true;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    private float floatingLabelFraction = 0;

    public MaterialEditText(@NonNull Context context) {
        this(context, null);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                KLog.logI("attrs: " + attrs.getAttributeName(i) + ", " + attrs.getAttributeValue(i));
            }
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true);
        typedArray.recycle();
        init();
    }

    private void init() {
        onUseFloatingLabelChange();
        paint.setTextSize(TEXT_SIZE);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (useFloatingLabel) {
                    if (floatingLabelShow && TextUtils.isEmpty(s)) {
                        floatingLabelShow = false;
                        getAnimator().reverse();

                    } else if (!floatingLabelShow && !TextUtils.isEmpty(s)) {
                        floatingLabelShow = true;
                        getAnimator().start();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private ObjectAnimator getAnimator() {
        if (animator1 == null) {
            animator1 = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0, 1);
        }
        return animator1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (useFloatingLabel) {
            paint.setAlpha((int) (0xff * floatingLabelFraction));
            float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
            canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset, paint);
        }
    }
}
