package com.example.yooho.zerostart.ui.view.listview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yooho.zerostart.R;
import com.example.yooho.zerostart.tools.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yooho on 2016/11/22.
 */
public class MyExpandListActivity extends FragmentActivity{

    private ExpandableListView mExpandLv;
    private List<ExpandModel> mExpandModelList;
    private MyExpandableAdapter myExpandableAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_list);

        initData();
        initView();
    }

    private void initData() {
        int groupNum = 4;
        mExpandModelList = new ArrayList<>();
        for (int i=0; i<groupNum; i++) {
            ExpandModel expandModel = new ExpandModel();
            expandModel.title = "title : " + i;
            expandModel.id = "0" + i;
            expandModel.isCkeched = (i%2 == 0);
            mExpandModelList.add(expandModel);
        }


        myExpandableAdapter = new MyExpandableAdapter();
    }

    private void initView() {
        mExpandLv = (ExpandableListView) findViewById(R.id.expand_expand_listview);
        mExpandLv.setAdapter(myExpandableAdapter);
        mExpandLv.setGroupIndicator(null);

//        mExpandLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                MyUtils.showToast("group " + groupPosition + "  id " + id);
//                return false;
//            }
//        });

        mExpandLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MyUtils.showToast("group " + groupPosition + "  , child " + childPosition + "  id " + id);
                return false;
            }
        });
    }

    class ExpandModel {
        String title;
        String id;
        boolean isCkeched;

    }


    class MyExpandableAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mExpandModelList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groupPosition + 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mExpandModelList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mExpandModelList.get(groupPosition).id;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            String id = mExpandModelList.get(groupPosition).id;
            return Long.getLong(id);
        }

        // 用来确定刷新时候,firstView是否仍是刷新时当时的位置
        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View inflate = View.inflate(MyExpandListActivity.this, R.layout.view_expand_gruop, null);
            ((TextView)inflate.findViewById(R.id.group_title)).setText(groupPosition + " " + isExpanded);
            ((TextView)inflate.findViewById(R.id.group_id)).setText("group id" + groupPosition);
            if (groupPosition%2 == 0) {
                ((ImageView)inflate.findViewById(R.id.group_status)).setImageResource(R.mipmap.w8);
            } else {
                ((ImageView)inflate.findViewById(R.id.group_status)).setImageResource(R.mipmap.w0);
            }
            if (isExpanded) {
                inflate.setBackgroundColor(Color.GREEN);
            } else {
                inflate.setBackgroundColor(Color.WHITE);
            }
            return inflate;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View inflate = View.inflate(MyExpandListActivity.this, R.layout.view_expand_group_item, null);
            ((TextView)inflate.findViewById(R.id.item_title)).setText(mExpandModelList.get(groupPosition).title + " child position " + childPosition);
            ((TextView)inflate.findViewById(R.id.item_id)).setText(mExpandModelList.get(groupPosition).id);
            ((CheckBox)inflate.findViewById(R.id.item_check)).setChecked(mExpandModelList.get(groupPosition).isCkeched);
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtils.showToast("group " + groupPosition + "  , child " + childPosition + "  id ");
                }
            });
            return inflate;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
