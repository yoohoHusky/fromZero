package com.example.yooho.zerostart.ui.view.listview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import com.example.yooho.zerostart.tools.MyMathUtil;
import com.example.yooho.zerostart.ui.adapter.AnimateListAdapter;
import com.example.yooho.zerostart.ui.bean.AnimateListChildBean;
import com.example.yooho.zerostart.ui.bean.AnimateListConfirmBean;
import com.example.yooho.zerostart.ui.interf.AnimateListObserver;

import java.util.ArrayList;


/**
 * ViewGroup特性：
 *  1. addView，addView指定view大小
 *  2. view被当做一个对象，可以设定很多东西
 *      1.
 *  3. 在onLayout时候指定指空间的位置
 *  4. child.getLayoutParams可以获得很多属性（topMargin等）
 *
 *  child.layout(l,t,r,b);  会影响child展示出来多少（如果已经测量width=400，height=400，layout（0，0，200，200）则只能展示出来1/4）
 *
 */
public class AnimateListShow extends RelativeLayout {
    private static final int HEAD_MARGIN_LEFT = 10;
    private static final int HEAD_MARGIN_RIGHT = 10;
    private static final int HEAD_MARGIN_TOP = 10;
    private static final int HEAD_MARGIN_BOTTOM = 10;

    private static final float DIMENS_CONFIRM_WIDTH_RATIO_WINDOW = 0.3f;   // confirm占window宽的最大比例
    private static final float DIMENS_CONFIRM_HEIGHT_RATIO_LINE = 0.35f;    // confirm占行高的最大比例

    private static final int NUM_COL_MAX = 5;
    private static final int NUM_ROW_MAX = 3;
    private static final int NUM_LINE1_CHILD = 5;
    private static final int NUM_LINE2_CHILD = 4;
    private static final int NUM_LINE3_CHILD = 3;
    private static final int NUM_CHILD_MAX = 12;


    private static final int ANIMATE_TOTAL_TIME = 2000;

    private static final float TIME_HEAD_DELAY_START = 0.7f;    // 距离开始的百分比
    private static final float TIME_HEAD_DELAY_END = 0.0f;      // 距离结尾的百分比
    private static final float TIME_LIST_DELAY_START = 0.0f;
    private static final float TIME_LIST_DELAY_END = 0.3f;
    private static final float TIME_CONFIRM_DELAY_START = 0.7f;
    private static final float TIME_CONFIRM_DELAY_END = 0.0f;

    private static final int DIMENS_CONFIRM_MARGIN_RIGHT = 100;
    private static final int DIMENS_CONFIRM_MARGIN_BOTTOM = 60;


    private Context mContext;
    private int mWindowWidth;
    private int mItemWidth;          // 每个框的宽
    private int mItemHeight;         // 每个框的高
    private int mLinePhase;          // 每行总位移量
    private int mItemPhase;          // 每个item的位移量
    private int mLineHeight;         // 每行总高度


    private ArrayList<AnimateListChildBean> mChildViewList;
    private AnimateListConfirmBean mConfirmBean;
    private View mHeadView;

    private int mHeadHeight;
    private int mHeadWidth;
    private float mProcess = 1.0f;
    private int mConfirmMaxWidth;
    private int mConfirmMaxHeight;

    private int mLastCheckedPos = -1;
    private AnimateListObserver mObserver;


    public AnimateListShow(Context context) {
        super(context);
        initViewGroup(context);

    }

    public AnimateListShow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup(context);
    }

    public void setAdapter(AnimateListAdapter adapter) {
        if (adapter == null) return;
        Log.e("SS", "setAdapter");
        cleanViewData();
        addHeadView(adapter.getHeadView(mContext));
        addConfirmView(adapter.getConfirmView(mContext));
        addViewList(adapter);
        startAnimate();
    }

    public void setObserver(AnimateListObserver observer) {
        mObserver = observer;
    }


    private void cleanViewData() {
        mHeadHeight = 0;
        mHeadHeight = 0;
        mHeadView = null;
        mConfirmBean = null;
        mChildViewList = null;
        removeAllViews();
    }

    private void addConfirmView(View confirmView) {
        if (confirmView == null) return;
        int width = (int) (mWindowWidth * DIMENS_CONFIRM_WIDTH_RATIO_WINDOW);
        int height = (int) (mLineHeight * DIMENS_CONFIRM_HEIGHT_RATIO_LINE);
        int centerX = mWindowWidth - DIMENS_CONFIRM_MARGIN_RIGHT - width / 2;
        mConfirmBean = new AnimateListConfirmBean(centerX, 0, width, height, 0.2f, confirmView);
        handConfirmView(mConfirmBean.getView(), width, height);
    }

    private void addHeadView(View headView) {
        if (headView == null) return;
        mHeadView = headView;
        addView(headView);
    }

    private void addViewList(AnimateListAdapter adapter) {
        mChildViewList = buildChildBean(adapter);
        for (AnimateListChildBean bean : mChildViewList) {
            handleChildView(bean.getIndex(), bean.getView(), mItemWidth, mItemHeight);
        }
    }

    private void handConfirmView(View view, int mItemWidth, int mItemHeight) {
        view.setTag(-1);
        view.setOnClickListener(mListChildLis);
        view.setScaleY(0.8f);
        view.setScaleX(0.8f);
        addView(view, mItemWidth, mItemHeight);
    }

    private void handleChildView(int index, View view, int mItemWidth, int mItemHeight) {
        view.setTag(index);
        view.setOnClickListener(mListChildLis);
        view.setScaleY(0.8f);
        view.setScaleX(0.8f);
        addView(view, mItemWidth, mItemHeight);
    }

    private ArrayList<AnimateListChildBean> buildChildBean(AnimateListAdapter adapter) {
        ArrayList<AnimateListChildBean> beanList = new ArrayList();
        if (adapter == null || adapter.getCount() == 0) return beanList;

        int confusedNum = Math.min(adapter.getCount(), NUM_CHILD_MAX);
        int index = 0;
        // first line
        if (confusedNum > 0) {
            for (int i=0; i<Math.min(confusedNum, NUM_LINE1_CHILD); i++) {
                beanList.add(new AnimateListChildBean(index, 0, i, mItemWidth, mItemHeight, adapter.getItemView(mContext, index), mWindowWidth, mLineHeight, mItemPhase));
                index++;
            }
        }
        confusedNum -= NUM_LINE1_CHILD;
        if (confusedNum > 0) {
            for (int i=0; i<Math.min(confusedNum, NUM_LINE2_CHILD); i++) {
                beanList.add(new AnimateListChildBean(index, 1, i, mItemWidth, mItemHeight, adapter.getItemView(mContext, index), mWindowWidth, mLineHeight, mItemPhase));
                index++;
            }
        }
        confusedNum -= NUM_LINE2_CHILD;
        if (confusedNum > 0) {
            for (int i=0; i<Math.min(confusedNum, NUM_LINE2_CHILD); i++) {
                beanList.add(new AnimateListChildBean(index, 2, i, mItemWidth, mItemHeight, adapter.getItemView(mContext, index), mWindowWidth, mLineHeight, mItemPhase));
                index++;
            }
        }
        return beanList;
    }

    private void initViewGroup(Context context) {
        Log.e("SS", "initMeasureData");
        mContext = context;
        initMeasureData(context);
    }

    private OnClickListener mListChildLis = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (position == -1) {
                if (mObserver != null && mLastCheckedPos != -1) {
                    mObserver.confirmCheck(mLastCheckedPos);
                }
            } else {
                renderChildChecked(position);
            }
        }
    };

    private void renderChildChecked(int position) {
        if (mObserver != null) mObserver.childChecked(position);
        mChildViewList.get(position).getView().setScaleX(1.2f);
        mChildViewList.get(position).getView().setScaleY(1.2f);
        if (mLastCheckedPos != position && mLastCheckedPos != -1) {
            if (mObserver != null) mObserver.chiildUnchecked(mLastCheckedPos);
            mChildViewList.get(mLastCheckedPos).getView().setScaleX(0.8f);
            mChildViewList.get(mLastCheckedPos).getView().setScaleY(0.8f);
        }
        mLastCheckedPos = position;
    }

    private void initMeasureData(Context context) {
        getWindowData(context);
        calculateItemData();
    }

    private void calculateItemData() {
        mItemWidth = mWindowWidth * 9 / 10 / 5;     // 总宽的0.9，平分给5个item
        mItemHeight = mItemWidth * 4 / 3;            // 长宽比 4：3
        mLinePhase = mItemWidth / 3;               // 每一行的总位移为高1/3
        mItemPhase = mLinePhase / 4;
        mLineHeight = mItemHeight + mLinePhase;

        mConfirmMaxWidth = (int) (mWindowWidth * DIMENS_CONFIRM_WIDTH_RATIO_WINDOW);
        mConfirmMaxHeight = (int) (mLineHeight * DIMENS_CONFIRM_HEIGHT_RATIO_LINE);
    }

    private void getWindowData(Context context) {
        mWindowWidth = getScreenSize((Activity) context)[0];
//        mWindowHeight = getScreenSize((Activity) context)[1];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("SS", "onMeasure");
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getHeadData();
        setConfirmData();
        setMeasuredDimension(mWindowWidth, mHeadHeight + mLineHeight * 3);  // head + 3个item
    }

    private void setConfirmData() {
        int centerY = (int) (mHeadHeight + mLineHeight * 3 - DIMENS_CONFIRM_MARGIN_BOTTOM - mLineHeight * DIMENS_CONFIRM_HEIGHT_RATIO_LINE);
        mConfirmBean.setCenterY(centerY);
    }

    private void getHeadData() {
        if (mHeadView != null) {
            View headView = mHeadView;
            mHeadHeight = headView.getMeasuredHeight();
            mHeadWidth = headView.getMeasuredWidth();
        } else {
            mHeadHeight = 0;
            mHeadWidth = 0;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("SS", "onLayout");
        int index = 0;
        if (mHeadView != null) {
            int left = (mWindowWidth - mHeadWidth) / 2;
            mHeadView.layout(left, 0, left + mHeadWidth, mHeadHeight);
        }

        if (mChildViewList != null && !mChildViewList.isEmpty()) {
            for (AnimateListChildBean bean : mChildViewList) {
                View childView = bean.getView();
                int left = bean.getShowX(parseListProcessData(mProcess));
                int top = mHeadHeight + bean.getShowY(parseListProcessData(mProcess));
                childView.layout(left, top, left + mItemWidth, top + mItemHeight);
                index ++;
            }
        }

        if(mConfirmBean != null) {
            View confirmView = mConfirmBean.getView();
            float x = parseConfirmViewProcess(mProcess);
            int left = mConfirmBean.getShowLeft(x);
            int top = mConfirmBean.getShowTop(x);
            int width = mConfirmBean.getCurrentWidth(x);
            int height = mConfirmBean.getCurrentHeight(x);

            confirmView.getLayoutParams().width = width;
            confirmView.getLayoutParams().height = height;
            confirmView.layout(left, top, left + width, top + height);
            Log.e("SSA", (left + width / 2) + "      " + (top + height / 2));
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("SS", "dispatchDraw");
        super.dispatchDraw(canvas);
        mHeadView.setAlpha(parseHeadProcessData(mProcess));
    }

    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }


    public void setProcess(float process) {
        Log.e("SS", "setProcess  " + process);
        mProcess = process;
        requestLayout();
    }

    private float parseConfirmViewProcess(float process) {
        process = MyMathUtil.parseSelfProcess(process, TIME_CONFIRM_DELAY_START, 1f - TIME_CONFIRM_DELAY_END);
        process = new OvershootInterpolator(3).getInterpolation(process);
//        process = new BounceInterpolator().getInterpolation(process);
        return process;
    }

    private float parseHeadProcessData(float process) {
        return MyMathUtil.parseSelfProcess(process, TIME_HEAD_DELAY_START, 1f - TIME_HEAD_DELAY_END);
    }

    private float parseListProcessData(float process) {
        return  MyMathUtil.parseSelfProcess(process, TIME_LIST_DELAY_START, 1f - TIME_LIST_DELAY_END);
    }

    public void startAnimate() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "process", 0.0f, 1f);
        animator.setDuration(ANIMATE_TOTAL_TIME);
        animator.start();
    }


}
