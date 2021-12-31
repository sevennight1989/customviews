package com.android.custview.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.view.GalleryImageView;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GalleryShowActivity extends BaseActivity {

    private static final String GALLERY_UIR_01 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fadmin.stonesm.com%2Fupload%2F212f795f-328c-4999-a5e8-3862577d754e.jpg&refer=http%3A%2F%2Fadmin.stonesm.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642234312&t=c5e1d3bf43e546c04f12aa3f39c06e51";
    private static final String GALLERY_UIR_02 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpavo.elongstatic.com%2Fi%2FHotel870_470%2Fnw_Nm2be6WnW8.jpg&refer=http%3A%2F%2Fpavo.elongstatic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642233881&t=6b418c24b24f466bc72da6d1bd5607b1";
    private static final String GALLERY_UIR_03 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-08-28%2F5d6649239aed2.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642236189&t=219628272549e6bc0b564544bfcce90a";
    private static final String GALLERY_UIR_04 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F96%2F3f%2F51%2F963f519a2ec78f2bf983b6dba3a4a1af.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642236205&t=d3b3ab41ed6870b6016e15cc6f1dbafe";
    private static final String GALLERY_UIR_05 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.article.pchome.net%2F00%2F22%2F52%2F63%2Fpic_lib%2Fs960x639%2F2s960x639.jpg&refer=http%3A%2F%2Fimg.article.pchome.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642236557&t=f6eeea7313bdb1993e3adea3ee68619a";
    private static final String GALLERY_UIR_06 = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F210925003FH616-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642236557&t=2cc6c3cf373df08d70f0752f02549348";

    private GalleryImageView galleryImageView;
    private GalleryImageView galleryImageView2;
    private GalleryImageView galleryImageView3;
    private GalleryImageView galleryImageView4;
    private TextView member_nameTv;
    private ImageView member_status_img;
    private TextView creator_tagTv;
    private ImageView head_directionImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_gallery_show;
    }

    @Override
    public void initView() {
        galleryImageView = findViewById(R.id.gallery_view);
        galleryImageView2 = findViewById(R.id.gallery_view2);
        galleryImageView3 = findViewById(R.id.gallery_view3);
        galleryImageView4 = findViewById(R.id.gallery_view4);
        member_status_img = findViewById(R.id.member_status_img);
        creator_tagTv = findViewById(R.id.creator_tag);
        member_nameTv = findViewById(R.id.member_name);
        head_directionImg = findViewById(R.id.head_direction);

/*        member_nameTv.setPadding(40, 0, 21, 0);
        member_status_img.setVisibility(View.VISIBLE);
         head_directionImg.setRotation(135);
        creator_tagTv.setVisibility(View.GONE);*/

//        member_nameTv.setPadding(40, 0, 21, 0);
//        member_status_img.setVisibility(View.GONE);
//                head_directionImg.setRotation(90);
//        creator_tagTv.setVisibility(View.GONE);

        member_nameTv.setPadding(74, 0, 21, 0);
        member_status_img.setVisibility(View.GONE);
        creator_tagTv.setVisibility(View.VISIBLE);
        head_directionImg.setRotation(45);



        RequestOptions options = new RequestOptions();
        options.error(R.drawable.bg_02).diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this)
                .asBitmap()
                .load(GALLERY_UIR_01)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        KLog.logI("onResourceReady" + Thread.currentThread().getName());
                        galleryImageView.setSourceBitmap(resource);
                    }
                });
        Glide.with(this)
                .asBitmap()
                .load(GALLERY_UIR_05)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        KLog.logI("onResourceReady" + Thread.currentThread().getName());
                        galleryImageView2.setSourceBitmap(resource);
                    }
                });

        Glide.with(this)
                .asBitmap()
                .load(GALLERY_UIR_02)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        KLog.logI("onResourceReady" + Thread.currentThread().getName());
                        galleryImageView3.setSourceBitmap(resource);
                    }
                });
        Glide.with(this)
                .asBitmap()
                .load(GALLERY_UIR_03)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        KLog.logI("onResourceReady" + Thread.currentThread().getName());
                        galleryImageView4.setSourceBitmap(resource);
                    }
                });

    }

    @Override
    public void initData() {

    }
}