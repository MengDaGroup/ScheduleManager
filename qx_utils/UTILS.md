#
## BlurViewUtils

### 模糊背景工具类，参数：activity界面对象

        BlurViewUtils blurUtils = new BlurViewUtils(activity);
        blurUtils.showBlurView();//显示模糊背景
        blurUtils.hideBlurView;//隐藏模糊背景

        在界面销毁时 blurUtils = null；释放资源
        

## FragmentHelper

### fragment管理工具

        FragmentHelper.switchFragment(R.id.user_fl_login, mFragmentLogin, this);//添加/切换fragment到activity
        FragmentHelper.back(activity);//返回
        

## PermissionUtils 权限申请工具

### 1.在要使用的地方申请需要的权限

        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
        
### 2.在界面的onRequestPermissionsResult回调监听结果

        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);

### DateUtil 管理时间转换，具体见方法注释

### ToastUtils 管理土司工具 ，可自定义时间，位置，背景，具体查看注释

### DimensUtil 屏幕尺寸相关工具

### FileUtil 文件操作相关

### StatusBarUtils 状态栏相关工具

### TypefaceUtil 文字处理工具

### VerifyUtil 验证类工具

### DensityUtil 尺寸转换工具

### DoubleUtil double数据处理工具类

### RCaster R与R2映射工具 具体使用参考注释

### BitmapUtil 图片转换工具

### NetworkUtils 网络诊断工具

### SPUtils SharedPreferences管理工具

### LanguageUtils 语言管理工具

### QRCodeUtil 二维码生成工具

### PriceUtil   价格转换工具

### ConvertStringUtil   字符串操作工具类 操做字符串的方法都定义在此类中