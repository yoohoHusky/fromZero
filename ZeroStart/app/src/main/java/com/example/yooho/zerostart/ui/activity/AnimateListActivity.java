package com.example.yooho.zerostart.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.ui.adapter.AnimateListAdapter;
import com.example.yooho.zerostart.ui.interf.AnimateListObserver;
import com.example.yooho.zerostart.ui.view.listview.AnimateListShow;

public class AnimateListActivity extends AppCompatActivity {
    private AnimateListShow animateList;
    private MyAnimateAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_list);

        MyListener myListener = new MyListener();
        findViewById(R.id.btn_0).setOnClickListener(myListener);
        findView();
    }

    private void findView() {
        animateList = findViewById(R.id.animate_list_view);
        adapter = new MyAnimateAdapter();
        animateList.setAdapter(adapter);
        animateList.setObserver(new MyObserver());
    }

    class MyObserver extends AnimateListObserver {
        @Override
        public void childChecked(int position) {
            Toast.makeText(AnimateListActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void confirmCheck(int position) {
            Toast.makeText(AnimateListActivity.this, "最终选则了" + position, Toast.LENGTH_SHORT).show();
        }
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_0) {
                animateList.startAnimate();
            }
        }
    }

    class MyAnimateAdapter extends AnimateListAdapter{

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public View getItemView(Context context, int position) {
            View inflate = View.inflate(context, R.layout.item_animate_list_view, null);
            TextView tv = inflate.findViewById(R.id.animate_text);
            TextView tvNum = inflate.findViewById(R.id.num_tv);
            ImageView iv = inflate.findViewById(R.id.animate_image);
            iv.setImageResource(R.drawable.animate_list_child_user_head);
            tvNum.setText(position + "");
            tv.setText("name  " + position);
            return inflate;
        }

        @Override
        public View getHeadView(Context context) {
            View inflate = View.inflate(context, R.layout.view_animate_list_head, null);
            return inflate;
        }
    }
}
