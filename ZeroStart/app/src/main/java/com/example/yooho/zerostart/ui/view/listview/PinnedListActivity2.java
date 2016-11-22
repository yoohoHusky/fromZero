package com.example.yooho.zerostart.ui.view.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.ui.view.listview.data.Contact;

import java.util.ArrayList;

/**
 * Created by yooho on 2016/11/22.
 */
public class PinnedListActivity2 extends FragmentActivity {
    private PinnedListView2 mPinnedLv;
    private ArrayList<Contact> contactList;
    private int mItemHeight = 55;
    private int mPinnedHeight = 55;

    private String[] strs = {"abc", "sakjdn", "sadjn", "gdq", "akjsbdjk", "adnjg", "ugnj", "siwg"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned_list);

        initData();
        initView();
    }


    private void initData() {
        int itemSize = 50;
        contactList = new ArrayList<>();
        for (int i = 0; i < itemSize; i++) {
            Contact contact = new Contact(i, strs[i % strs.length] + "" + i);
            if (i % 6 == 0 && i != 0) {
                contact.isSection = true;
            } else {
                contact.isSection = false;
            }
            contactList.add(contact);
        }
    }

    //
    private void initView() {
        mPinnedLv = (PinnedListView2) findViewById(R.id.pinned_list);
        mPinnedLv.setAdapter(new ListViewAdapter2(contactList));

    }

    //
    private class ListViewAdapter2 extends BaseAdapter implements PinnedInterface2 {

        private ArrayList<Contact> mDatas;
        private static final int TYPE_CATEGORY_ITEM = 0;
        private static final int TYPE_ITEM = 1;

        public ListViewAdapter2(ArrayList<Contact> datas) {
            mDatas = datas;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            // 异常情况处理
            if (null == mDatas || position < 0 || position > getCount()) {
                return true;
            }

            Contact item = mDatas.get(position);
            if (item.isSection) {
                return false;
            }

            return true;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
            // 异常情况处理
            if (null == mDatas || position < 0 || position > getCount()) {
                return TYPE_ITEM;
            }

            Contact item = mDatas.get(position);
            if (item.isSection) {
                return TYPE_CATEGORY_ITEM;
            }
            return TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public Object getItem(int position) {
            return (position >= 0 && position < mDatas.size()) ? mDatas.get(position) : 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int itemViewType = getItemViewType(position);
            Contact data = (Contact) getItem(position);
            TextView itemView;

            switch (itemViewType) {
                case TYPE_ITEM:
                    if (null == convertView) {
                        itemView = new TextView(PinnedListActivity2.this);
                        itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                80));
                        itemView.setTextSize(16);
                        itemView.setPadding(10, 0, 0, 0);
                        itemView.setGravity(Gravity.CENTER_VERTICAL);
                        //itemView.setBackgroundColor(Color.argb(255, 20, 20, 20));
                        convertView = itemView;
                    }

                    itemView = (TextView) convertView;
                    itemView.setText(data.toString());
                    break;

                case TYPE_CATEGORY_ITEM:
                    if (null == convertView) {
                        convertView = getHeadView(position);
                    }
                    itemView = (TextView) convertView;
                    itemView.setText(data.sortLetter);
                    break;
            }

            return convertView;
        }


        @Override
        public View getHeadView(int position) {
            TextView itemView = new TextView(PinnedListActivity2.this);
            itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    mPinnedHeight));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setBackgroundColor(Color.WHITE);
            itemView.setTextSize(20);
            itemView.setTextColor(Color.GRAY);
            itemView.setBackgroundColor(Color.YELLOW);
            itemView.setPadding(10, 0, 0, itemView.getPaddingBottom());
            return itemView;
        }

        @Override
        public int getHeadViewState(int position) {
            if (position < 6) {
                return PINNED_HEADER_GONE;
            }

            Contact item = (Contact) getItem(position);
            Contact itemNext = (Contact) getItem(position + 1);
            boolean isSection = item.isSection;
            boolean isNextSection = (null != itemNext) ? itemNext.isSection : false;
            if (!isSection && isNextSection) {
                return PINNED_HEADER_PUSHED_UP;
            }
            return PINNED_HEADER_VISIBLE;
        }

        @Override
        public void updateHeadView(View header, int position, float ratio) {
            Contact item = (Contact) getItem(position);
            if (null != item) {
                if (header instanceof TextView) {
                    TextView tv = (TextView)header;
                    tv.setText(mDatas.get(position).sortLetter);
                    Log.e("SS", "" + ratio);
                    tv.setAlpha(ratio);
                }
            }
        }
    }
}
