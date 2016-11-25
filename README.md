# XBanner
支持图片无限轮播的控件，可进行自定义功能。


## 主要功能：
- 支持根据服务端返回的数据动态设置广告条的总页数
- 支持大于等于1页时的无限循环自动轮播、手指按下暂停轮播、抬起手指开始轮播
- 支持自定义状态指示点位置  左 、中 、右
- 支持自定义状态指示点
- 支持监听 item 点击事件
- 支持设置图片轮播间隔
- 支持指示器背景的修改及隐藏/显示
- 支持显示提示性文字功能
- 支持图片切换动画,目前支持10种切换动画，具体可看demo
- 支持设置图片切换速度
- 支持设置数字指示器
- 支持Glide/Fresco等主流图片加载框架加载图片

## 效果图

![1](https://github.com/xiaohaibin/XBanner/blob/master/sceenshots/xbanner.gif)

## 基本使用

[![latestVersion](https://api.bintray.com/packages/jxnk25/maven/XBanner/images/download.svg) ](https://bintray.com/jxnk25/maven/XBanner/_latestVersion)

#### 1.添加Gradle依赖

```
dependencies {
    compile 'com.xhb:xbanner:latestVersion'//将latestVersion替换成上面最新的版本号
    compile 'com.nineoldandroids:library:2.4.0'
}
```
#### 2.在清单文件中添加网络权限

```
<uses-permission android:name="android.permission.INTERNET" />
```

#### 3.在布局文件中添加XBanner
```xml
    <com.stx.xhb.xbanner.XBanner
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/banner_1"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        app:pointsPosition="CENTER"
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


#### 4.在Activity或者Fragment中配置

> 初始化:直接传入视图集合进行初始化

```
        List<String> imgesUrl = new ArrayList<>();
        imgesUrl.add("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
        //添加广告数据
        mBannerNet.setData(imgesUrl,null);//第二个参数为提示文字资源集合

```


#### 5.加载广告

> 可根据自己项目需要使用相应的图片加载工具进行加载图片，此处使用Glide，进行加载，可配置占位图等


```
 mBannerNet.setmAdapter(this);
 @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Glide.with(this).load(imgesUrl.get(position)).into((ImageView) view);
    }
    
```

#### 6.监听广告 item 的单击事件

```java
 mBannerNet.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(MainActivity.this, "点击了第"+position+"图片", Toast.LENGTH_SHORT).show();
            }
        });
```

#### 7.为了更好的体验，建议j在添加以下代码

```
  /** 为了更好的体验效果建议在下面两个生命周期中调用下面的方法 **/
    @Override
    protected void onResume() {
        super.onResume();
        mXBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mXBanner.stopAutoPlay();
    }

```

#### 8.当使用Fresco加载图片时，需要自定义布局文件
1.自定义xml布局文件 image_fresco.layout
```
 <?xml version="1.0" encoding="utf-8"?>
 <com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
  />
```
2.使用setData()方法进行设置
```
   mXBanner.setData(R.layout.image_fresco.layout,mOthersList,tips);
   
```

## 自定义属性说明

| 属性名 | 属性说明 | 属性值 | 
| ------------ | ------------- | ------------ |
| isAutoPlay| 是否支持自动轮播 | boolean类型，默认为true |
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
| pageChangeDuration|图片切换速度| int值，默认为1000ms |

## 混淆配置

```
##XBanner 图片轮播混淆配置
-keep class com.stx.xhb.xbanner.**{*;}
```

>## 更新说明
>v1.0.1 新增自定义指示器显示位置、 指示点上下内间距、指示点左右内间距等功能  <br />
>v1.1.1 修改可能引起内存泄漏的bug  <br />

>v1.1.2
 - 修复当通过setData接口再次刷新数据后无效的问题  <br />
 
>v1.2.2
 - 新增支持显示提示文字  <br />
 - 新增图片切换动画、设置图片切换速度的功能<br />
 - 修复快速滑动出现卡顿的bug <br />

## 关于我
个人邮箱：xhb_199409@163.com

[GitHub主页](https://github.com/xiaohaibin)

[简书主页](http://www.jianshu.com/users/42aed90cf5af/latest_articles)

[个人博客](http://blog.csdn.net/jxnk25)

## Thanks
[bingoogolapple](https://github.com/bingoogolapple)

感谢[tanweijiu](https://github.com/tanweijiu)修复版本1.1.2中bug，也欢迎各位感兴趣的开发者共同维护该项目。

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
    
#如果喜欢，还请statr&Fork&follow进行支持，谢谢O(∩_∩)O~。#
