##### 画彗星尾部的渐变
1. 利用paint的shader渲染，其共有5个子类
  1. BitmapShader   位图
  2. LinearGradient 线性渐变
  3. RadialGradient 光束渐变
  4. SweepGradient  梯度渐变
  5. ComposeShader  混合渐变
```
int[] indicatorColor = {0xffffffff,0x00ffffff,0x99ffffff,0xffffffff};
Shader shader = new SweepGradient(0,0, indicatorColor, null);
paint2.setShader(shader);
RectF rectF = new RectF(-radius-w, -radius-w, radius+w, radius+w);
canvas.drawArc(rectF, startAngle, sweep, false, paint2);
```

2. 画四周模糊的光点
  1. paint3.setMaskFilter(new BlurMaskFilter(i, BlurMaskFilter.Blur.SOLID));
  ```
    paint3.setMaskFilter(new BlurMaskFilter(i, BlurMaskFilter.Blur.SOLID));
    canvas.drawCircle(x, y, i, paint3);
  ```
