##### 为任何view设置动画属性
1. 通过ObjectAnimation.ofFloat对view的某一个属性设置`起始值`，`终点值`，并且通过动画实现
```java
  ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "alpha", 0.0f, 1.0f);  //设置透明度
  ObjectAnimator animator = ObjectAnimator.ofFloat(DynamicalLine.this, "phase", 0.0f, 1.0f);  //设置进度
  animator.setDuration(3000);
  animator.start();
```


phase 相位
dash 折线
