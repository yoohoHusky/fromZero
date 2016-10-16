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
