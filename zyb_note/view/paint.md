######## paint画笔相关
1. paint的基本设置
  ```java
  Paint dashLinePaint = new Paint();
  dashLinePaint.setColor(new Color().WHITE);
  PathEffect effect = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);   // 设置虚线
  dashLinePaint.setPathEffect(effect);
  dashLinePaint.setStrokeWidth(3);                                      // 设置宽度
  dashLinePaint.setAntiAlias(true);                                     // 设置抗锯齿
  dashLinePaint.setDither(true);                                        // 设置防抖动
  dashLinePaint.setStyle(Paint.Style.STROKE);                           // 设置实心

  ```
2. 画一条虚线
```java
  paint.setPathEffect(new DashPathEffect(new float[]{1.0f, 2.0f, 4.0f, 8.0f}, 30.0f));
```

3. deawText画文字
```java
  Rect rect = listItems.get(i).windyBoxRect;
  Rect targetRect = new Rect(rect.left, rect.bottom, rect.right, rect.bottom + bottomTextHeight);
  Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
  int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
  textPaint.setTextAlign(Paint.Align.CENTER);

  String text = listItems.get(i).time;
  canvas.drawText(text, targetRect.centerX(), baseline, textPaint);
```

4. 画图片有两种方法
```java
  BitmapFactory.decodeResource(getResources(), R.mipmap.hour_24_float);
  // 方法1，canvas画bitmap
  canvas.drawBitmap(bitmap,hourItem.tempPoint.x, hourItem.tempPoint.y - 20, null);

  // 方法二，drawable，setBound， 画canvas
  Drawable drawable = getResources().getDrawable(R.mipmap.hour_24_float);
  drawable.setBounds(calculateScrollX(),
        temperatureBarY - DisplayUtil.dip2px(getContext(), 24),
        calculateScrollX() + ITEM_WIDTH,
        temperatureBarY - DisplayUtil.dip2px(getContext(), 4));
  drawable.draw(canvas);

```
5. 测量text的两种方法
```java
  String centerStr = “信用”；
  canvas.drawText(centerStr, -paint3.measureText(centerStr)/2, 0, paint3);


  String addStr = "信用" + evaluateList[i];
  Rect rect = new Rect();
  paint3.getTextBounds(addStr, 0, addStr.length(), rect);
  canvas.drawText(addStr, -rect.width()/2, rect.height()/2 + 20, paint3);
```
