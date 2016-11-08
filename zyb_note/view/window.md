#### window的一些属性
1. 得到屏幕展示范围的宽
```java
  DisplayMetrics dm = context.getResources().getDisplayMetrics();
  dm.widthPixels;
```
2. 获得scrollView滑动的距离
computeHorizontalScrollOffset（）
getScrollX（），可能得到超出边际的值
