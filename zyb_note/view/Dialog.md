#### dialog
  1. dialog的一些属性
  ```xml
  <style name="TransparentDialog" parent="@android:style/Theme.Dialog">
      <item name="android:backgroundDimAmount">0.75</item>                    # 设置背景透明度
      <item name="android:windowBackground">@android:color/transparent</item> # 设置整个框背景（）
      <item name="android:windowFrame">@null</item>                           # 背景是否有边框
      <item name="android:windowNoTitle">true</item>                          # 是否有上半部的部分
      <item name="android:windowIsFloating">true</item>
      <item name="android:windowIsTranslucent">true</item>
      <item name="android:background">@android:color/transparent</item>       # 设置title，context背景
      <item name="android:backgroundDimEnabled">true</item>
  </style>
  ```
  2. 设置dialog动画
    1. 得到window
    2. 对window设置animation
    3. 得到layoutParams
    4. 对param设置宽、高、x/y、gravity
    ```
      *   Window window = getWindow();
      *  window.setWindowAnimations(R.style.WebViewDialogAnim);             设置动画
      *   WindowManager.LayoutParams attributes = window.getAttributes();   得到layout参数
      *   attributes.x = x;
      *   attributes.y = y;
      *   attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
      *   attributes.gravity = Gravity.BOTTOM;
    ```
3. 悬浮按钮实现
  1. 通过getService得到WindowManager:
  `(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);`
  2. 得到WindowManager.LayoutParams
  3. 对param设置type:phone `(phone则view可点击,overLay不接收点击)`
  4. 对param设置flags:FLAG_NOT_TOUCH_MODAL|FLAG_NOT_FOCUSABLE  `(如果不设置这两个,其他组件将无法获得点击)`
  5. 设置宽、高、gravity
  6. addView(chileView,params)
  ```
    WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    //      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;   //如果设置为
    params.type = WindowManager.LayoutParams.TYPE_PHONE; //
    params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.LEFT | Gravity.TOP;
    params.x = 0;
    params.y = 0;
    wm.addView(childView, params);
  ```
4. 动画设置interpolator篡改器,调节动画播放速度
  - AccelerateDecelerateInterpolator 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
  - AccelerateInterpolator  在动画开始的地方速率改变比较慢，然后开始加速
  - AnticipateInterpolator 开始的时候向后然后向前甩
  - AnticipateOvershootInterpolator 开始的时候向后然后向前甩一定值后返回最后的值
  - BounceInterpolator   动画结束的时候弹起
  - CycleInterpolator 动画循环播放特定的次数，速率改变沿着正弦曲线
  - DecelerateInterpolator 在动画开始的地方快然后慢
  - LinearInterpolator   以常量速率改变
  - OvershootInterpolator    向前甩一定值后再回到原来位置

  5. xml设置动画的代码
  ```
  <set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1000"
    android:interpolator="@android:anim/bounce_interpolator"  >
    <scale
      android:fromXScale="1.0"
      android:toXScale="1.0"
      android:fromYScale="0.0"
      android:toYScale="1.0"
      android:pivotX="0"
      android:pivotY="100%"
      android:fillAfter="false"
      android:duration="300"
    />
  </set>
  ```
