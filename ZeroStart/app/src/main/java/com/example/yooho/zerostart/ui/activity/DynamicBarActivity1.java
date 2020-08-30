package com.example.yooho.zerostart.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.tools.NewStatusBarUtil;

import java.util.ArrayList;

public class DynamicBarActivity1 extends AppCompatActivity {

    private RecyclerView recycle;
    private ArrayList<String> mList;
    private AppBarLayout lay0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_bar_1);
        recycle = findViewById(R.id.model_1_rv);
        lay0 = findViewById(R.id.lay_0);

        makeData();

        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(new MyRecycleAdapter());
        lay0.setExpanded(false);
    }

    private void makeData() {
        mList = new ArrayList();
        for (int i = 0; i < 50; i++) {
            mList.add("list : " + i);
        }
    }

    class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleHolder> {

        @NonNull
        @Override
        public MyRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_text, parent, false);
            return new MyRecycleHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleHolder holder, int position) {
            holder.tv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    class MyRecycleHolder extends RecyclerView.ViewHolder {

        TextView tv;
        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_simple_tv);
        }
    }
}
