package com.android.custview.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.custview.R;
import com.android.custview.layout.LinearLayoutManagerExtend;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.GsonUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class Learn05Activity extends BaseActivity {
    private RecyclerView rl1;
    private RecyclerView head_rv;
    private MyAdapter adapter;
    private MyAdapter2 adapter2;
    private LinearLayoutManagerExtend llm;

    @Override
    public int getLayout() {
        return R.layout.activity_learn05;
    }

    @Override
    public void initView() {
        llm = new LinearLayoutManagerExtend(this);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(true);
        rl1 = findViewById(R.id.rl1);
        adapter = new MyAdapter();
        rl1.setLayoutManager(llm);
        rl1.setAdapter(adapter);
        rl1.setHasFixedSize(true);
        rl1.setNestedScrollingEnabled(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.attach_layout, rl1, false);
        head_rv = headView.findViewById(R.id.head_rv);
        head_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new MyAdapter2();
        head_rv.setAdapter(adapter2);
    }

    @Override
    public void initData() {
        Single.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf(integer);
                    }
                }).delay(2, TimeUnit.SECONDS)
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        java8Tools();
    }

    private void java8Tools(){
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            LocalDate today = LocalDate.now();
            int dayOfMonth = today.getDayOfMonth();
            int dayOfWeek = today.getDayOfWeek().getValue();
            int dayOfYear = today.getDayOfYear();
            KLog.logI("当月第" + dayOfMonth + "天");
            KLog.logI("本周周中第" + dayOfWeek + "天");
            KLog.logI("当年中第" + dayOfYear + "天");

            List<String> list = Stream.of("one", "two", "three", "four")
                    .filter(e -> e.length() > 3)
                    .peek(e -> KLog.logI("Filtered value : " + e))
                    .map(String::toUpperCase)
                    .peek(e -> KLog.logI("Mapped value ： " + e))
                    .collect(Collectors.toList());
            KLog.logI("List size -> " + list.size());
            KLog.logI("List -> " + GsonUtils.toJson(list));
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        KLog.logI("Learn05Activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        KLog.logI("Learn05Activity onTouchEvent");
        return true;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        private String[] datas = {"A", "B", "C", "D", "E", "F"};

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, null);

            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tv.setText(datas[position]);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public TextView tv;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_text);

            }
        }
    }

    private class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyHolder> {

        private String[] datas = {"A", "B", "C"};

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, null);

            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tv.setText(datas[position]);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            public TextView tv;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_text);

            }
        }
    }
}