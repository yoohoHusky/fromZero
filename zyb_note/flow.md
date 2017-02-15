#### Activity
1. SplashActivity
2. InitActivity
3. ActivityLifecycleListener(用来做统计，和检测展示splash页面)
4. IndexActivity
5. LiveContainerFragment/TitleFragment/BaseFragment
6. LiveMainPagerAdapter（与学科相关联的item就是page每一页，但是不负责写学科）
7. LiveCourseFragment（上面那个需要new这个出来，专题课实在这里设置进adapter，数据都在这里面）
    mBanContainer(班课的LinearLayout)(live_main_season_course_item)   
    LiveCommonListAdapter（专题课的listView）（live_item_choose_private_course）

8. LiveZhuanListAdapter
   LiveCommonListAdapter（是复用率最高的课程的items）（其他的都用head等view实现）
9. CourseDetailActivity（课程详情页面）(webview)
   ChooseCourseActivity（ChooseCourseFragment）(班课列表的页面)
   LiveCommonListAdapter

10. PayBindActivity/RegistrationActivity/LivePayActivity(绑定手机/免费课/付费课)
11. LessonDetailListActivity(课程详情页)
12. 课程详情页面 page中包含的 LessonDetailFragment
13. LiveActivity（直播页面）

14. shopCartBindActivity（购物车页面）
    ShopCartBindAdapter(live_course_shop_cart_item_layout)


##### InitActivity
1. view是：index_activity_fragments_tabs
2. 检查Info
  - appInfo
  - configInfo
  - skin
3. 做updateHost
4. 注册push
5. 创建快捷方式
6. 注册首页下方tabs消息定时器
7. 启动定位一次
8. 注册好友监听，通过task更新messageManger（展示到tab上）
9. 向服务器请求所有未读消息
10. 设置好评相关参数
11. 同步提问记录


##### SplashActivity.java
1. view是单独定制的：SplashView
  - onMeasure时，划分出上下两个区域，得到Bitmap
  - onDraw时，不断变化上、下desRect，canvas.drawBitmap
  - onAttachedToWindow时，启动动画(runnable)。
  - onDetachedFromWindow时，回收Bitmap

  - runnabel:里面创建个animation
    - 设置duration
    - 设置interpolator
    - 设置UpdateListener，监听移动的距离 mOpenDistance
    - 设置Listener，监听动画取消、结束
  - 启动这个animation
2. checkShowSplashView，从intent中得到mSplashData，展示splash
3. checkSkipText，通过runnable实现时间变化
4. 对splashView设置AttachStateChangeListener
  - 在onViewDetachedFromWindow中，做finish


##### IndexActivity.java
1. 布局文件：index_activity_fragments_tabs
  1. 第一层：index_tabs/realTabContent（+）
  2. 第二层：layout-title + layout（+）
  3. 第三层：scrollView + ViewPage（+）
  4. 第四层：LinearLayout（价格行） + FragmentLayout/listView（复用）（数据在LiveMainPagerAdapter）

##### LiveContainerFragment
0. onCreate
  1. 获得grade
  2. 如果是第一次进入，就储存grade
1. onCreateView
  1. 创建一个LinearLayout
  2. initTitleView，初始化titleView/并填充数据
  3. 加入内容view（都是inflate出来的）
  4. 调用initView

2. initView
  1. 设置上方title
  2. 左边文字，增加imageView，点击事件
  3. 右边文件，点击事件
  4. 将mPage封装进mLayoutSwitchViewUtil
    * MAIN_VIEW：主页面；
    * ERROR_VIEW：“加载出错”页面
    * LOADING_VIEW, "正在加载"页面
    * EMPTY_VIEW, "加载为空"页面
    * NO_NETWORK_VIEW, "无网"页面
    * NO_LOGIN_VIEW “未登录”页面
    * CONTENT_DELETED 显示内存被删除
  5. 调用loadData，执行网络请求
    1. 调用onCateResponse

3. onCateResponse
  1. 调用net，url写死的，会调onCateResponse，response封装成bean
  2. 调用switchViewUtil.showView(MAIN_VIEW)
    1. stopAnimation
    2. 调用showMainView（删除掉customView，将mainView展示出来）
  3. 将返回的数据封进adapter(整个页面的)
  4. 将adapter设置进page
  5. 将page设置进index_tabs
  6. 编写年级点击监听，并在延迟task中重新更新view


##### LiveMainPagerAdapter
1. 是与tabs（即数学、语文）相关联的
2. 每一个item是一个大内容页面（包含时间，head，listView）
3. 每个item又被封装进LiveCourseFragment里面

#### LiveCourseFragment（包含 学科，listView的view，listView的head）
1. onCreate
2. onCreateView
  1. 布局文件为live_fragment_main_item
    1. LinearLayout包含：全部、时间、价格
    2. ListPullView
  2. 清空rootView的父对其的绑定
  3. inflate布局文件，返回这个view
  4. initView
    1. find到 学科，listPullView
    2. inflate出live_main_test_header，作为head
      1. find到 teacher_container，live_banner...
    3. 对mListView（封装的ListPullView）设置head，scroll，adapter，pullListener
    4. listView数据封装在LiveZhuanListAdapter中
    5. 内容bean是Courselistv1.ListItem

#### CourseDetailActivity（课程详情页面）
1. onCreate
  1. 关闭掉返回手势
  2. 从intent中读出传过来的数据
  3. initView
    1. 对web进行做设置（集成在hybridWebview中对js的回调做处理，以action形式）
    2. 创建出对应自己的action（PayLiveCourseAction）
    3. 在payAction中回调到gotoPay
  4. gotoPay - PayBindActivity - StartPayHelper.startPay
    1. 调到绑定手机页面，PayBindActivity
    2. 免费课，直接到 RegistrationActivity
    3. 付费课，到 LivePayActivity

#### LivePayActivity
1. onCreate
  1. 从intent中拿到数据
  2. 对未登录用户直接退出
  3. 布局 live_pay_cash_activity
  4. initView，添加点击回调
  5. udateUI


#### LessonDetailListActivity/LessonDetailFragment(page里面放的是这个bean)
1. 布局 live_lesson_detail_list_activity

#### LiveActivity
1. onCreate
  1. 通过activity拿到window，设置window参数
  2. new RecommendCourse
  3. new ChatRoom
  4. 布局：activity_stream_play（只有一个Player/RelativeLayout）
  5. new DialogUtils
  6. new ClockControler
  7. new MediaController


#### ChatRoom
  1. inflate出来一个view，直接从activity中find content，然后添加
  2. 布局文件：live_chatroom_layout
    1. 最外层relateLayout
    2. 内容是个ViewPage，里面是LiveChatFragment，里面有个ListPullView

#### LiveChatFragment（聊天室）
1. inflate（common_listview_layout），add进来
2. add footView


########
1. 通过一个WebSocket来完成直播接收
2. initialLcsService
  1. 先注册socket关闭监听
  2.




2.
下方tab是：通过select/animation-list来实现动画效果的
