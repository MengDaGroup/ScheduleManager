# base引用说明

## 所有需要设置的初始参数在BaseConfig类里去获取，统一通过BaseManager抛给外部去设置
    new BaseManager.Builder(ins)
                    .mTimeCache(Constants.TIME_CACHE)
                    .build();

## 图片数据上传封装工具 MultiparBodyUtil
    MultiparBodyUtil.image2MultipartBody("fileImg", imgPath);