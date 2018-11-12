##### 简介
glide是和volley，picasso，fresco 并列，用来给开发者实现图片加载的第三方工具。由于其调用方便，占用内存资源小，可加载gif，可支持更为丰富的特效，被大多数人引入到工程中。

##### 常用API
1. Glide.with(context).load(resId/url/binCode).into(imageView)
2. with()				返回requestManager，其他都是requestBuilder

3. asBitmap():			指定只加载静态图片，gif将只取第一帧
4. asGif():				指定只加载动态图，如传入静态，则走现实失败
5. apply(option):		指定加载图片的大小。glide自身本会去读取imageView大小，根据实际需要的大小，将图片加载到内存中
6. into():				返回viewTarget



glider如今已经将扩展内容放在了Requestoption中，Glide只做主流程对的事情
1. option.placeholder():		指定占位图
2. error():						指定访问失败的图片
3. override（):					指定图片尺寸，其实glide本身会根据imageView的大小自适配。Target.SIZE_RIGINAL:加载图片真实大小，不做压缩
4. *fitcenter()*				图片缩放类型。长边达到最大值，另一边按比例压缩（压缩了长的边，保证长宽比例）
5. *centerCrop()*				图片缩放类型。短边到达最大值，另一边按比例放大，然后从中间剪裁
6. *circleCrop()*				图片缩放类型。先做fitcenter，尽可能多的展示图片，然后用圆形截取

7. skipMemoryCache(true):		跳过内存缓存
8. diskCacheStrategy(type):			设置硬盘缓存策略
	1. DiskCacheStrategy.ALL:		缓存所有版本（大小比例）的图像
	2. DiskCacheStrategy.NONE:		跳过硬盘缓存
	3. DiskCacheStrategy.DATA:		只缓存原始大小的图片
	4. DiskCacheStrategy.RESOURCE:	只缓存根据imageView大小压缩后的图片
	5. DiskCacheStrategy.AUTOMATIC:	表示让Glide根据图片资源智能地选择哪一种缓存策略（默认值）


##### 阅读源码









