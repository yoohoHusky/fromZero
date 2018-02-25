package com.example.yooho.zerostart.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoou on 2018/2/23.
 */

public class RecycleViewActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SparseArray mDataArray;
    private ArrayMap mDataMap;
    private ArrayList mDataList;
    private Button addBtn;
    private Button reduceBtn;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_demo);

        initData();
        initView();
        initEvent();


    }

    private void initEvent() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList.add("测试增加");
                recyclerAdapter.notifyItemInserted(2);
            }
        });

        reduceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList.remove(mDataList.size()-1);
                recyclerAdapter.notifyItemRemoved(5);
            }
        });
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        addBtn = (Button) findViewById(R.id.func_add);
        reduceBtn = (Button) findViewById(R.id.func_reduce);

        // 附加部分 --  设置分割器
//        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this, 4);
//        mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        recyclerAdapter = new RecyclerAdapter(mDataList);
        mRecycleView.setAdapter(recyclerAdapter);


    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mDataList.add("" + i );
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter {

        private List<String> adaList;

        public RecyclerAdapter(List<String> list) {
            this.adaList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_demo1, parent, false);
            MyRecyclerViewHolder myRecyclerViewHolder = new MyRecyclerViewHolder(inflate);
//            myRecyclerViewHolder.getTextView1().
            return myRecyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (!(holder instanceof MyRecyclerViewHolder)) {
                return;
            }
            MyRecyclerViewHolder myHolder = (MyRecyclerViewHolder) holder;
//            myHolder.getTextView1().setTag(position);
            myHolder.getTextView1().setText("" + adaList.get(position));
            myHolder.getTextView2().setText("" + position);

            myHolder.getTextView1().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(RecycleViewActivity.this, "toast: " + position, Toast.LENGTH_SHORT).show();
                    adaList.remove(position);
                    recyclerAdapter.notifyItemRemoved(position);
                    Log.e("SS", adaList.toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.adaList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }


    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView1;
        private final TextView textView2;

        public MyRecyclerViewHolder(View itemView) {
            super(itemView);

            textView1 = (TextView) itemView.findViewById(R.id.item_tv1);
            textView2 = (TextView) itemView.findViewById(R.id.item_tv2);
        }

        public TextView getTextView1() {
            return textView1;
        }

        public TextView getTextView2() {
            return textView2;
        }
    }



}
