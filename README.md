# SimpleWeibo
基于MVP框架的简易新浪微博阅读器

没有用新浪提供的SDK
因为新浪接口限制，只有阅读微博功能（其实是因为写入接口权限没申请下来。。。TAT）

程序基于MVP+Rxjava+Retrofit编写

请在使用前修改model.entity.KEY里的APP_KEY、APP_SECRET、DEFAULT_PAGE（授权默认回调页面）为自己申请的信息，不然无法正常获取微博内容。
