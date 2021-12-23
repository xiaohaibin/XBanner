## XBanner

[![License](https://img.shields.io/badge/License-Apache--2.0-green.svg)](https://github.com/xiaohaibin/XBanner/blob/master/LICENSE)
 
![1](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/xbanner.png)

## English | [中文](https://github.com/xiaohaibin/XBanner/blob/master/README.md)

## Functions：
- Support multiple display on one screen
- Support dynamic setting data
- Support loop auto play
- Support set indicator position left, center, right
- Support custom indicator
- Support Page OnClickListener
- Support setting the playback interval
- Support tip text
- Support picture switching animation or customize
- Support setting picture switching speed
- Support setting digital indicator
- Support setting imageplaceholder
- Support Glide/Fresco
- Support Custom layout
- Support AndroidX

## Styles

|mode|picture
| :-: | :-: |
|normal indicator|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot3.png)|
|number indicator|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot6.png)|
|number indicator and title|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot5.png)|
|normal indicator and title|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot1.png)|
|title|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot2.png)|
|multiple page|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot4.png)|
|multiple page|![效果示例](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/screenshot7.png)|
## Demo Apk

![demo](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/apk_code.png)


## Usage

#### 1.Add Gradle

#### Jitpack

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

    //Normal
    implementation 'com.github.xiaohaibin:XBanner:1.8.5'
    
    //androidX
    implementation 'com.github.xiaohaibin:XBanner:androidx_v1.2.2'
}
```
 
#### 2.Add network permissions to the manifest file

```
<uses-permission android:name="android.permission.INTERNET" />
```

#### 3.Add XBanner in the layout file
```
    <com.stx.xhb.xbanner.XBanner
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/xbanner"
        android:layout_width="match_parent"
        android:layout_height="高度自定义"
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


#### 4.Use in Activity or Fragment

> initialization:set banner data

```     
        //get xbanner
        XBanner mXBanner = (XBanner) findViewById(R.id.xbanner);
        
       //add data（network data、local file、View）,refresh data also use this method
        mXBanner.setBannerData("data");

```


#### 5.load image

> according your project choose imageload framework，this use Glide load image

```
      //load image
      mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
            
       Glide.with(MainActivity.this).load(((AdvertiseEntity.OthersBean)
      model).getThumbnail()).placeholder(R.drawable.default_image).error(R.drawable.default_image).into((ImageView) view);
            }
        });

```

#### 6.setOnItemClickListener

```
 mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model,View view, int position) {
                Toast.makeText(MainActivity.this, "点击了第"+position+"图片", Toast.LENGTH_SHORT).show();
            }
        });
```

#### 7.Use Fresco load image

1.custom layout R.layout.image_fresco
```
 <?xml version="1.0" encoding="utf-8"?>
 <com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
  />
```
2.use setBannerData() method
```
  mXBanner.setBannerData(R.layout.image_fresco,“banner data”);
   
```
3.setBannerData

> Before you use setBannerData，you should let banner eneity implement the interface,eg：

```
    public class CustomViewsInfo implements BaseBannerInfo {

        private String info;

        public CustomViewsInfo(String info) {
            this.info = info;
        }

        //image  url
        @Override
        public String getXBannerUrl() {
            return info;
        }

       //tips
        @Override
        public String getXBannerTitle() {
            return "tips";
        }
    }
```
#### 8.Custom layout

1.Custom layout，eg：R.layout.customelayout

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

2.Use setBannerData() method
```
   mXBanner.setBannerData("custom latout",“data”);
```

3.loadimage，see [CustomViewsActivity](https://github.com/xiaohaibin/XBanner/blob/master/sample/src/main/java/com/stx/xhb/demo/CustomViewsActivity.java)
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

## Attributes in xml

| Attributes | description | format | 
| ------------ | ------------- | ------------ |
| isAutoPlay| set isAutoPlay | boolean，default true |
| isTipsMarquee| set isTipsMarquee | boolean，default false|
| AutoPlayTime| set AutoPlayTime | integer，default 5s |
| pointNormal| set indicator pointNormal | drawable|
| pointSelect| set indicator pointSelect | drawable |
| pointsVisibility| set indicator isVisible | boolean，default true |
| pointsPosition| set indicator postion | LEFT、CENTER、RIGHT，default CENTER |
| pointsContainerBackground| set indicator baground | drawable |
| pointContainerPosition| set pointContainerPosition | TOP、BOTTOM，default BOTTOM |
| pointContainerLeftRightPadding| set pointContainerLeftRightPadding | dimension，default 10dp |
| pointTopBottomPadding| set pointTopBottomPadding | dimension，default 6dp |
| pointLeftRightPadding| set pointLeftRightPadding | dimension，default 3dp |
| tipTextColor| 提示文案的文字颜色 | reference|color，default white |
| tipTextSize| 提示文案的文字大小| dimension，default 10dp |
| isShowNumberIndicator| 是否显示数字指示器| boolean,default false |
| numberIndicatorBacgroud|数字指示器背景| reference |
| isShowIndicatorOnlyOne|当只有一张图片的时候是否显示指示点| boolean，default false |
| isShowTips|是否展示文字| boolean，default false |
| pageChangeDuration|图片切换速度| integer，default 1000ms |
| isHandLoop|是否支持手动无限循环切换图片| boolean，default false |
| placeholderDrawable|设置整体轮播框架占位图| reference |
| isClipChildrenMode|是否开启一屏显示多个模式|  boolean，default false  |
| clipChildrenLeftMargin|一屏显示多个左间距| dimension ，default 30dp|
| clipChildrenRightMargin|一屏显示多个右间距| dimension ，default 30dp|
| clipChildrenTopBottomMargin|一屏显示多个上下间距| dimension ，default 30dp|
| viewpagerMargin|viewpager页面间距| dimension ，default 10dp|
| isClipChildrenModeLessThree|少于三张是否支持一屏多显模式|  boolean，default false  |
| bannerBottomMargin|banner轮播区域底部margin，可设置指示器距离轮播图的间距| dimension ，default 0dp|
| scaleType|设置占位图缩放类型 |scaleType类型|
| showIndicatorInCenter|设一屏多显模式下 指示器是否显示在中间图片位置，默认显示中间 |boolean| 
| isClickSide|一屏多显模式下 是否支持点击侧边切换图片，默认开启 |boolean|

## Progard

```
##XBanner Progard
-keep class com.stx.xhb.xbanner.**{*;}
```

## About

* **Email**: <xhb_199409@163.com>
* **Home**: <http://www.jxnk25.club>
* **掘金**: <https://juejin.im/user/56fcba0a71cfe4005ca1a57b>
* **简书**: <http://www.jianshu.com/users/42aed90cf5af/latest_articles>

## Thanks

[bingoogolapple](https://github.com/bingoogolapple)

Thanks[tanweijiu](https://github.com/tanweijiu)fix 1.1.2 中bug

Thanks[Leoand8](https://github.com/Leoand8)fix 1.6.1 中bug


### Your Star is my biggest motivation，Thanks~~~


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
