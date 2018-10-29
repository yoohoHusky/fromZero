##### 简介
glide是和volley，picasso，fresco 并列，用来给开发者实现图片加载的第三方工具。由于其调用方便，占用内存资源小，可加载gif，可支持更为丰富的特效，被大多数人引入到工程中。

##### 常用API
1. Glide.with(context).load(resId/url/binCode).into(imageView)
2. placeholder():		添加占位图
3. error():				图片加载失败图片
4. diskCacheStrategy():	设置缓存规则，DiskCacheStrategy.NONE为禁用缓存
5. asBitmap():			指定只加载静态图片，gif将只取第一帧
6. asGif():				指定只加载动态图，如传入静态，则走现实失败
7. override():			指定加载图片的大小。glide自身本会去读取imageView大小，根据实际需要的大小，将图片加载到内存中