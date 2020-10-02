## XBanner

[![License](https://img.shields.io/badge/License-Apache--2.0-green.svg)](https://github.com/xiaohaibin/XBanner/blob/master/LICENSE)
 
![1](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/xbanner.png)

## 主要功能：
- 支持一屏显示多个
- 支持根据服务端返回的数据动态设置广告条的总页数
- 支持大于等于1页时的无限循环自动轮播、手指按下暂停轮播、抬起手指开始轮播
- 支持自定义状态指示点位置  左 、中 、右
- 支持自定义状态指示点
- 支持监听 item 点击事件
- 支持设置图片轮播间隔
- 支持指示器背景的修改及隐藏/显示
- 支持显示提示性文字功能
- 支持图片切换动画,目前支持10种切换动画，亦可设置自定义动画效果
- 支持设置图片切换速度
- 支持设置数字指示器
- 支持设置图片框架整体占位图
- 支持Glide/Fresco等主流图片加载框架加载图片
- 支持自定义布局
- 支持AndroidX
## 效果图

|模式|效果图
| :-: | :-: |
|指示器模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot3.png)|
|数字模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot6.png)|
|数字加标题模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot5.png)|
|指示器加标题模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot1.png)|
|标题模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot2.png)|
|一屏显示模式|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot4.png)|

## Demo Apk

![demo](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/apk_code.png)


## 基本使用

#### 1.添加 Gradle （以前是有的是Jecenter方式引入，由于国内被墙了，切换成JitPack方式引入，使用方式不变）

## Jitpack

Add it in your root build.gradle at the end of repositories:
```
allprojects {
     repositories {
	...
	maven { url 'https://jitpack.io' }
     }
}

```
Step 2. Add the dependency

[![Version](https://jitpack.io/v/xiaohaibin/XBanner.svg)](https://jitpack.io/#xiaohaibin/XBanner)

```
dependencies {

    //普通版本依赖
    implementation 'com.github.xiaohaibin:XBanner:1.7.9'
    
    //androidX 版本使用下面的依赖
    implementation 'com.github.xiaohaibin:XBanner:androidx_v1.1.2'
}
```
 
#### 2.在清单文件中添加网络权限

```
<uses-permission android:name="android.permission.INTERNET" />
```

#### 3.在布局文件中添加 XBanner
```
    <com.stx.xhb.xbanner.XBanner
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/xbanner"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        app:AutoPlayTime="3000"
        app:pointsContainerBackground="#44aaaaaa"
        app:pointNormal="@drawable/shape_noraml"
        app:pointSelect="@drawable/shape_selected"
        app:pointsPosition="RIGHT"
        app:tipTextSize="12sp"
        app:isShowNumberIndicator="true"
        app:isShowIndicatorOnlyOne="true"
        app:pageChangeDuration="800"/>
```


#### 4.在 Activity 或者 Fragment 中配置

> 初始化:直接传入视图集合进行初始化

```     
        //获取控件
        XBanner mXBanner = (XBanner) findViewById(R.id.xbanner);
        
       //添加轮播图片数据（图片数据不局限于网络图片、本地资源文件、View 都可以）,刷新数据也是调用该方法
        mXBanner.setBannerData(imgesUrl);//setData（）方法已过时，推荐使用setBannerData（）方法，具体参照demo使用

```


#### 5.图片加载

> 可根据自己项目需要使用相应的图片加载工具进行**加载图片**，此处使用 Glide ，进行加载

```
      //加载广告图片
      mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
            
       //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
       //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
       Glide.with(MainActivity.this).load(((AdvertiseEntity.OthersBean)
      model).getThumbnail()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });

```

#### 6.监听广告 item 的单击事件

```

 mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model,View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第"+position+"图片", Toast.LENGTH_SHORT).show();
            }
        });
```

#### 7.使用 Fresco 加载图片时，需要自定义布局文件

1.自定义布局文件 R.layout.image_fresco
```
 <?xml version="1.0" encoding="utf-8"?>
 <com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
  />
```
2.使用 setBannerData() 方法进行设置
```
  //setData（）方法已过时，推荐使用setBannerData（）方法，具体参照demo使用
  mXBanner.setBannerData(R.layout.image_fresco,“加载数据集合”);
   
```
#### 8.自定义布局

1.自定义自己需要展示的Banner显示布局，如：R.layout.customelayout

```
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tv"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:text="1"
    android:textSize="40dp"
    android:gravity="center"
    android:textColor="@android:color/white"
    android:background="@color/colorYellow"/>
```

2.使用 setBannerData() 方法进行设置
```
   mXBanner.setBannerData(R.layout.customelayout,“加载数据集合”);
```

3.设置数据，通过 loadImage() 方法回传的 View 根据自定义布局设置的Id找到相应的控件进行数据设置，具体请看 [CustomViewsActivity](https://github.com/xiaohaibin/XBanner/blob/master/sample/src/main/java/com/stx/xhb/demo/CustomViewsActivity.java)
```
mBanner.loadImage(new XBanner.XBannerAdapter() {
               @Override
               public void loadBanner(XBanner banner, Object model, View view, int position) {
                   TextView tvContent = (TextView) view.findViewById(R.id.tv);
                   tvContent.setText(String.valueOf(position + 1));
                   view.setBackgroundColor(Color.parseColor((String) model));
               }
           });
```


## 自定义属性说明

| 属性名 | 属性说明 | 属性值 | 
| ------------ | ------------- | ------------ |
| isAutoPlay| 是否支持自动轮播 | boolean类型，默认为true |
| isTipsMarquee| 是否支持提示性文字跑马灯效果 | boolean类型，默认为false|
| AutoPlayTime| 图片轮播时间间隔 | int值，默认为5s |
| pointNormal| 指示器未选中时状态点 | drawable，不设置的话为默认状态点 |
| pointSelect| 指示器选中时状态点 | drawable，不设置的话为默认状态点 |
| pointsVisibility| 是否显示指示器 | boolean类型，默认为true |
| pointsPosition| 指示点显示位置 | LEFT、CENTER、RIGHT类型，默认为CENTER |
| pointsContainerBackground| 指示器背景 | 可自定义设置指示器背景 |
| pointContainerPosition| 指示器显示位置 | TOP、BOTTOM类型，默认为BOTTOM |
| pointContainerLeftRightPadding| 指示点容器左右内间距 | dimension，默认为10dp |
| pointTopBottomPadding| 指示点上下内间距 | dimension，默认为6dp |
| pointLeftRightPadding| 指示点左右内间距 | dimension，默认为3dp |
| tipTextColor| 提示文案的文字颜色 | reference|color，默认为white |
| tipTextSize| 提示文案的文字大小| dimension，默认为10dp |
| isShowNumberIndicator| 是否显示数字指示器| boolean,默认为false不显示 |
| numberIndicatorBacgroud|数字指示器背景| reference |
| isShowIndicatorOnlyOne|当只有一张图片的时候是否显示指示点| boolean，默认为false，不显示 |
| isShowTips|是否展示文字| boolean，默认为false，不显示 |
| pageChangeDuration|图片切换速度| int值，默认为1000ms |
| isHandLoop|是否支持手动无限循环切换图片| boolean类型，默认为false |
| placeholderDrawable|设置整体轮播框架占位图| reference |
| isClipChildrenMode|是否开启一屏显示多个模式|  boolean类型，默认为false 默认不开启 |
| clipChildrenLefttMargin|一屏显示多个左间距| dimension ，默认为30dp|
| clipChildrenRightMargin|一屏显示多个右间距| dimension ，默认为30dp|
| clipChildrenTopBottomMargin|一屏显示多个上下间距| dimension ，默认为30dp|
| viewpagerMargin|viewpager页面间距| dimension ，默认为10dp|
| isClipChildrenModeLessThree|少于三张是否支持一屏多显模式|  boolean类型，默认为false 默认不开启 |
| bannerBottomMargin|banner轮播区域底部margin，可设置指示器距离轮播图的间距| dimension ，默认为0dp|
| viewPagerClipChildren|设置 viewpager clipChildren 属性，是否显示多个 |boolean类型| 
| scaleType|设置占位图缩放类型 |scaleType类型| 
| showIndicatorInCenter|设一屏多显模式下 指示器是否显示在中间图片位置，默认显示中间 |boolean类型| 

## 混淆配置

```
##XBanner 图片轮播混淆配置
-keep class com.stx.xhb.xbanner.**{*;}
```

## Q&A

- 1.一屏显示多个模式默认使用ScalePageTransformer切换动画，也可以自定义；自定义动画添加方法setCustomPageTransformer（自定义动画效果）；

- 2.一屏显示多个模式默认是会缩放左右两个页面，若想左右页面与中间页面保持一致，把切换动画设置成自己自定义的就可以；

- 3.图片不显示问题

  >1）确认是否实现了 **loadImage（）** 方法，需要使用自己的图片加载框架加载图片！！！
  >2）请把加载图片地址复制到浏览器看看是否打开图片，确认图片地址是否正确！！！

- 4.AndroidX模式配置问题
https://blog.csdn.net/qq_17766199/article/details/81433706

## 关于我

* **Email**: <xhb_199409@163.com>
* **Home**: <http://www.jxnk25.club>
* **掘金**: <https://juejin.im/user/56fcba0a71cfe4005ca1a57b>
* **简书**: <http://www.jianshu.com/users/42aed90cf5af/latest_articles>

## Thanks

[bingoogolapple](https://github.com/bingoogolapple)

感谢[tanweijiu](https://github.com/tanweijiu)修复版本 1.1.2 中bug

感谢[Leoand8](https://github.com/Leoand8)修复版本 1.6.1 中bug

也欢迎各位感兴趣的开发者共同维护该项目。

### Contract

[QQ群:271127803](http://qm.qq.com/cgi-bin/qm/qr?k=cM-ytK5bbZZZ4v7S1fMrTDzkjlFT0C9K)

![欢迎关注“大话微信”公众号](http://upload-images.jianshu.io/upload_images/1956769-2f49dcb0dc5195b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)


### 你的 Statr 是我最大的动力，谢谢~~~


License
--
    Copyright (C) 2016 xhb_199409@163.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
