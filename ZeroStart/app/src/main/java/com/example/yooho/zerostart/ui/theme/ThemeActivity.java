package com.example.yooho.zerostart.ui.theme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yooho.zerostart.R;

import java.util.ArrayList;

/**
 * 通过setTheme() + reCreate() + SP/boundInstance的方式刷新主题
 *
 *
 */
public class ThemeActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    EditText edInput;
    TextView tvShow;
    RecyclerView rvShow;
    static final String SP_TAG = "demo_theme";
    static final String BOUND_STRING_ARRAY = "bound_string_array";
    static final String BOUND_STRING_SHOW = "bound_string_show";
    ArrayList<String> list;
    MyAdapter myAda;
    String showStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (list == null) list = new ArrayList<>();
        if (savedInstanceState != null) {
            list = savedInstanceState.getStringArrayList(BOUND_STRING_ARRAY);
            showStr = savedInstanceState.getString(BOUND_STRING_SHOW);
        }

        sp = getSharedPreferences("SP", MODE_PRIVATE);
        edit = sp.edit();
        int themeId = sp.getInt(SP_TAG, R.style.DemoTheme);
        setTheme(themeId);
        setContentView(R.layout.activity_theme_demo);
        initData();

        MyListener listener = new MyListener();
        findViewById(R.id.btn_day).setOnClickListener(listener);
        findViewById(R.id.btn_night).setOnClickListener(listener);
        findViewById(R.id.create_data).setOnClickListener(listener);

        tvShow = findViewById(R.id.tv_show);
        edInput = findViewById(R.id.et_input);
        rvShow = findViewById(R.id.rv_theme_demo);
        myAda = new MyAdapter();

        rvShow.setLayoutManager(new LinearLayoutManager(this));
        rvShow.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvShow.setAdapter(myAda);


        if (!TextUtils.isEmpty(showStr)) {
            tvShow.setText(showStr);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(BOUND_STRING_ARRAY, list);
        outState.putString(BOUND_STRING_SHOW, showStr);
    }

    private void initData() {
        if (!list.isEmpty()) return;

        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_day) {
                edit.putInt(SP_TAG, R.style.DemoTheme_Day);
                edit.commit();
            } else if (v.getId() == R.id.btn_night) {
                edit.putInt(SP_TAG, R.style.DemoTheme_Night);
                edit.commit();
                recreate();
            } else if (v.getId() == R.id.create_data){
                showStr = edInput.getText().toString();
                tvShow.setText(showStr);
                for (int i=0; i <list.size(); i++) {
                    list.set(i, list.get(i) + "  add  ");
                }
                myAda.notifyDataSetChanged();
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_text, parent, false);
            Holder holder = new Holder(inflate);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (!(holder instanceof Holder)) {
                return;
            }
            Holder hol = (Holder) holder;
            hol.onBindView(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tv;
        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_simple_tv);
        }

        public void onBindView(int position) {
            tv.setText(list.get(position) + "    init");
        }
    }
}
