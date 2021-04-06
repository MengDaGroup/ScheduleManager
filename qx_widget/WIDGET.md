# qx_widget说明

## Bannerview   广告轮播图

## flowlayout   流式布局 ，比如商品详情页规格弹窗的规格选择

## TabLayoutWidget 自定义带滑块tablayout

## BasePop BasePopView 自定义pop基类，使用详情可参考common里BottomCommonPop中的使用逻辑

## TitleBar 自定义titleBar

## CircleImageView 自定义圆形ImageView

## CircularCornerImageView 自定义圆角ImageView

## CusEditText 自定义带删除icon的输入框

## ProgressWebView 自定义带加载进度条的webview

## QBadgeView 提醒气泡视图
    setBadgeNumber	设置Badge数字
    setBadgeText	设置Badge文本
    setBadgeTextSize	设置文本字体大小
    setBadgeTextColor	设置文本颜色
    setExactMode	设置是否显示精确模式数值
    setBadgeGravity	设置Badge相对于TargetView的位置
    setGravityOffset	设置外边距
    setBadgePadding	设置内边距
    setBadgeBackgroundColor	设置背景色
    setBadgeBackground	设置背景图片
    setShowShadow	设置是否显示阴影
    setOnDragStateChangedListener	打开拖拽消除模式并设置监听
    stroke	描边
    hide	隐藏Badge
    使用：
    new QBadgeView(context).bindTarget(targetview).setBadgeNumber(5);