package com.android.custview.ui;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.android.custview.R;
import com.android.custview.learn.CameraView;
import com.android.custview.learn.CircleView;
import com.android.custview.learn.CountryView;
import com.android.custview.learn.PointView;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;
import com.android.zp.base.utils.CommonUtils;

import java.util.List;

public class Learn02Activity extends BaseActivity {

    private ImageView avatarImg;
    private CircleView circleView;
    private CameraView cameraView;
    private PointView pointView;
    private CountryView country_view;
    private List<String> listProvinces;
    @Override
    public int getLayout() {
        return R.layout.activity_learn02;
    }

    @Override
    public void initView() {
        avatarImg = findViewById(R.id.avatar);
        circleView = findViewById(R.id.circle_view);
        cameraView = findViewById(R.id.camera_view);
        pointView = findViewById(R.id.point_view);
        country_view = findViewById(R.id.country_view);
//        avatarImg.animate()
//                .translationX(CommonUtils.dp2Px(200))
//                .translationY(CommonUtils.dp2Px(50))
//                .setStartDelay(1000)
//                .rotation(180)
//                .alpha(0.5f)
//                .start();
        ObjectAnimator animator = ObjectAnimator.ofFloat(circleView,"radius",CommonUtils.dp2Px(150));//setRadius
        animator.setStartDelay(1000);
        animator.setDuration(1000).start();

        ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 45);
        bottomFlipAnimator.setDuration(500);

        ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(cameraView, "flipRotation", 270);
        flipRotationAnimator.setDuration(1000);

        ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(cameraView, "topFlip", -45);
        topFlipAnimator.setDuration(500);

//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(bottomFlipAnimator,flipRotationAnimator,topFlipAnimator);
//        animatorSet.setStartDelay(1000);
//        animatorSet.start();

        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip",45);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation",270);
        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder, flipRotationHolder, topFlipHolder);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

//        float length = CommonUtils.dp2Px(300);
//        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
//        Keyframe keyframe4 = Keyframe.ofFloat(1.0f, 1.0f * length);
//        PropertyValuesHolder viewHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
//
//        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(avatarImg, viewHolder);
//        animator2.setDuration(2000);
//        animator2.setStartDelay(1000);
//        animator2.start();

//        avatarImg.setTranslationY(-CommonUtils.dp2Px(100));
/*        avatarImg.animate().translationY(CommonUtils.dp2Px(450))
                .setStartDelay(1000)
                .setDuration(2000)
//                .setInterpolator(new DecelerateInterpolator())
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();*/
//        Point targetPoint = new Point((int)CommonUtils.dp2Px(300),(int)CommonUtils.dp2Px(200));
//        ObjectAnimator animator2 = ObjectAnimator.ofObject(pointView,"point",new PointEvaluator(),targetPoint);
//        animator2.setStartDelay(1000);
//        animator2.setDuration(2000);
//        animator2.start();

        listProvinces = CommonUtils.getProvinces(getApplicationContext());
        KLog.logI("listProvinces size : " + listProvinces.size());

        ObjectAnimator animator2 = ObjectAnimator.ofObject(country_view, "province", new ProvinceEvaluator(), "澳门特别行政区");
        animator2.setStartDelay(1000);
        animator2.setDuration(5000);
        animator2.start();


    }

//    private class FloatValueEvaluator implements TypeEvaluator<Float>{
//
//        @Override
//        public Float evaluate(float fraction, Float startValue, Float endValue) {
//            return (endValue - startValue) * fraction + startValue;
//        }
//    }

    private class ProvinceEvaluator implements TypeEvaluator<String> {

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int startIndex = listProvinces.indexOf(startValue);
            int endIndex = listProvinces.indexOf(endValue);
            int index = (int) (startIndex + (endIndex - startIndex) * fraction);
            return listProvinces.get(index);
        }
    }

    private static class PointEvaluator implements TypeEvaluator<Point>{

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            return new Point((int)((endValue.x - startValue.x) * fraction + startValue.x),(int)((endValue.y - startValue.y) * fraction + startValue.y));
        }
    }

    @Override
    public void initData() {

    }
}