#### d
1. 画一个渐进的进度条
```java
  canvas.save(Canvas.CLIP_SAVE_FLAGE)
  canvas.clipRect(0,0, xx, height) 剪切画布绘画空间(用到clipRect一定要用save，restore)
  canvas.drawColor()
  canvas.restore()
```

2. 画一个两图形叠加
```java
  将一张Abitmap设置进desCanvan(新建一个canvas)
  PorterDuffXfermode xfermode = new     PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
  paint.setXfermode(xfremode)
  desCanvas.drawBitmap(srcBitmap, left, top, paint);
  view的canvas.drawBitmap(Abitmap,0,0,null)

```
3. 剪裁一个图片
```java
  1/先通过bitmapFactory拿到这个图片
  2/根据长宽创建一个新的bitmap（Bitmap.createBitma(width,length,Config888)）
  3/根据长宽创建Rect
  4/在canvas根据Rect画RoundRect
  5/为画笔设置setXfermode
  6/canvas画drawBitmap(bitmap,srcRect,desRect,paint)
```


4. canvas画汉字
```java
  1、得到汉字的范围rect，paint.getTextBounds(textString, start, end, 空rect对象)
  2、根据rect对象得到width，height
  canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint); //yCoordinate是指汉子的左下角坐标
  3、变色
    canvas.save()
    canvas.clipRect()
    canvas.drawText()
    canvas.restore()
```
