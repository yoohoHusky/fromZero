##### 基础的动画
1. tween			补间动画
2. frame 			帧动画
3. valueAnimation	属性动画		ValueAnimation.ofInt() 
4. objectAnimation	
5. circularReveral	揭露动画		ViewAnimaitonUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
6. activity			基础动画
7. 					5.0新动画 	
    ```
		startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this))
		startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(actShareImg, "share_icon")).toBound);

		getWindow().setEnterTransition(new Explode());
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

		android:transitionName="share_icon"		// 可以省略
    ```


##### SVG 矢量动画(Scalable Vector Graphics)
1. 基础vector
	1. 根标签		<vector>	指定大小
	2. 组标签		<group>		对空间设置一个组，用户集中设置动画
		1. rotation
	3. 路径标签	<path> 		指定颜色，路径（画图）
		1. fillColor		
		2. pathData
		3. trimPathStart	按0-1的比例，从path的绘制过程，开始剪切
		4. trimPathEnd		按0-1的比例，从反向path的绘制过程，开始剪切
	4. 截取标签	<clip-path>	只留下指定范围内的图形，只对当前的group和子group有效
		1. pathData			截取范围的路径

2. 动画vector
	1. 根标签		<animated-vector>	设置静止时的vector
	2. 动画标签	<target>			指定静止图片里的空间名称，指定对应的动画，也可以直接将objectAnmation写进来
	3. 动画标签	<target>			指定静止图片里的空间名称，指定对应的动画，也可以直接将objectAnmation写进来(加aapt:attr name="animation"标签)
         ```xml
	    <target android:name="groupHeart">
	        <aapt:attr name="android:animation">
	            <objectAnimator
	                android:propertyName="rotation"
	                android:valueFrom="-360"
	                android:valueTo="0"
	                android:duration="1000" />
	        </aapt:attr>
	    </target>
         ```
	

3. 动画vector 选择器
	1. 根标签		<animated-selector>
	2. 图片标签	<item>
		1. drawable
		2. state_checked
	3. 动画vector标签	<transition>
		1. fromId 
		2. toId
		3. drawable			指定`动画vector`

4. trim动画
	1. trimPathStart		是基础vector，path标签的属性
	2. trimPathEnd			是基础vector，path标签的属性






#### SVG字母对应的含义
```
	M：新建起点，参数x，y（M20， 30）
	L：连接直线，参数x，y（L30， 20）
	H：纵坐标不变，横向连线，参数x（H20）
	V：横坐标不变，纵向连线，参数y（V30）
	Q：二次贝塞尔曲线，参数x1，y1，x2，y2（Q10，20，30，40）
	C：三次贝塞尔曲线，参数x1，y1，x2，y2，x3，y3（C10，20，30，40，50， 60）
	Z：连接首尾，闭合曲线，无参数
	- 所有指令大小写均可。大写绝对定位，参照全局坐标系；小写相对定位，参照父容器坐标系
	- rXxxTo方法的r意思是relative，即相对的意思，方法有四个，如上图所示，其功能与对应的xxxTo方法一样，区别在于rXxxTo方法在绘制Path时是以当前path画笔位置为坐标原点，即相对于path画笔位置进行绘制，而xxxTo方法的坐标原点则与当前canvas坐标原点一致。例如，我们使用xxxTo方法

```






