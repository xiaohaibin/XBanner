package com.stx.xhb.xbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stx.xhb.xbanner.transformers.BasePageTransformer;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * link https://xiaohaibin.github.io/
 * email： xhb_199409@163.com
 * github: https://github.com/xiaohaibin
 * description： 图片轮播控件
 * 1.支持图片无限轮播控件;
 * 2.支持自定义指示器的背景和两种状态指示点;
 * 3.支持隐藏指示器、设置是否轮播、设置轮播时间间隔;
 * 4.支持设置图片描述;
 * 5.支持自定义图片切换动画、以及设置图片切换速度.
 * 6.支持设置提示性文字  不需要的时候直接设置提示性文字数据为null即可;
 */
public class XBanner extends RelativeLayout implements XBannerViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {

    private static final int RMP = LayoutParams.MATCH_PARENT;
    private static final int RWC = LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;

    private static final int VEL_THRESHOLD = 400;
    public static final int NO_PLACE_HOLDER = -1;
    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private OnItemClickListener mOnItemClickListener;

    /** 指示点位置 */
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    @IntDef({LEFT, CENTER, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface INDICATOR_GRAVITY { }

    private AutoSwitchTask mAutoSwitchTask;

    private LinearLayout mPointRealContainerLl;

    private XBannerViewPager mViewPager;

    /** 指示点左右内间距 */
    private int mPointLeftRightPading;

    /** 指示点上下内间距 */
    private int mPointTopBottomPading;

    /** 指示点容器左右内间距 */
    private int mPointContainerLeftRightPadding;

    /** 资源集合 */
    private List<?> mDatas;

    /** 处理少于三页时的无限轮播 */
    private List<View> mLessViews;

    /** 视图集合 */
    private List<View> mViews;

    /** 是否只有一张图片 */
    private boolean mIsOneImg = false;

    /** 是否开启自动轮播 */
    private boolean mIsAutoPlay = true;

    /** 自动播放时间 */
    private int mAutoPalyTime = 5000;

    /** 是否允许用户滑动 */
    private boolean mIsAllowUserScroll = true;

    /** viewpager从最后一张到第一张的动画效果 */
    private int mSlideScrollMode = OVER_SCROLL_ALWAYS;

    /** 指示点位置 */
    private int mPointPosition = CENTER;

    /** 正常状态下的指示点 */
    private Drawable mPointNoraml;

    /** 选中状态下的指示点 */
    private Drawable mPointSelected;

    /** 默认指示点资源 */
    private int mPointDrawableResId = R.drawable.selector_banner_point;

    /** 指示容器背景 */
    private Drawable mPointContainerBackgroundDrawable;

    /** 指示容器布局规则 */
    private LayoutParams mPointRealContainerLp;

    /** 提示语 */
    private TextView mTipTv;

    /** 提示文案数据 */
    private List<String> mTipData;

    /** 提示语字体颜色 */
    private int mTipTextColor;

    /** 指示点是否可见 */
    private boolean mPointsIsVisible = true;

    /** 提示语字体大小 */
    private int mTipTextSize;

    /** 指示器容器位置 */
    public static final int TOP = 10;
    public static final int BOTTOM = 12;

    @IntDef({TOP, BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface INDICATOR_POSITION { }

    private int mPointContainerPosition = BOTTOM;
    private XBannerAdapter mAdapter;

    /** 指示器容器 */
    private LayoutParams mPointContainerLp;

    /** 是否是数字指示器 */
    private boolean mIsNumberIndicator = false;
    private TextView mNumberIndicatorTv;

    /** 数字指示器背景 */
    private Drawable mNumberIndicatorBackground;

    /** 只有一张图片时是否显示指示点 */
    private boolean mIsShowIndicatorOnlyOne = false;

    /** 默认图片切换速度为1s */
    private int mPageChangeDuration = 1000;

    /** 是否支持提示文字跑马灯效果 */
    private boolean mIsTipsMarquee = false;

    /** 是否是第一次不可见 */
    private boolean mIsFirstInvisible = true;

    /** 非自动轮播状态下是否可以循环切换 */
    private boolean mIsHandLoop = false;

    private Transformer mTransformer;

    /** 轮播框架占位图资源Id */
    private int mPlaceholderDrawableResId = -1;

    private ImageView mPlaceholderImg;

    /** 是否开启一屏显示多个模式 */
    private boolean mIsClipChildrenMode = false;

    /**
     * 一屏显示多个模式左右间距
     */
    private int mClipChildrenLeftRightMargin;

    /**
     * 一屏显示多个模式上下间距
     */
    private int mClipChildrenTopBottomMargin;

    /**
     * viewpager之间的间距
     */
    private int mViewPagerMargin;


    /**
     * 请使用 {@link #loadImage} 替换
     * @param mAdapter
     */
    @Deprecated
    public void setmAdapter(XBannerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public void loadImage(XBannerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public XBanner(Context context) {
        this(context, null);
    }

    public XBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initCustomAttrs(context, attrs);
        initView();
    }

    private void init(Context context) {
        mAutoSwitchTask = new AutoSwitchTask(this);
        mPointLeftRightPading = XBannerUtils.dp2px(context, 3);
        mPointTopBottomPading = XBannerUtils.dp2px(context, 6);
        mPointContainerLeftRightPadding = XBannerUtils.dp2px(context, 10);
        mClipChildrenLeftRightMargin = XBannerUtils.dp2px(context, 30);
        mClipChildrenTopBottomMargin = XBannerUtils.dp2px(context, 10);
        mViewPagerMargin = XBannerUtils.dp2px(context, 10);
        mTipTextSize = XBannerUtils.sp2px(context, 10);
        mTransformer = Transformer.Default;
        /*设置默认提示语字体颜色*/
        mTipTextColor = Color.WHITE;
        /*设置指示器背景*/
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBanner);
        if (typedArray != null) {
            mIsAutoPlay = typedArray.getBoolean(R.styleable.XBanner_isAutoPlay, true);
            mIsHandLoop = typedArray.getBoolean(R.styleable.XBanner_isHandLoop, false);
            mIsTipsMarquee = typedArray.getBoolean(R.styleable.XBanner_isTipsMarquee, false);
            mAutoPalyTime = typedArray.getInteger(R.styleable.XBanner_AutoPlayTime, 5000);
            mPointsIsVisible = typedArray.getBoolean(R.styleable.XBanner_pointsVisibility, true);
            mPointPosition = typedArray.getInt(R.styleable.XBanner_pointsPosition, CENTER);
            mPointContainerLeftRightPadding = typedArray.getDimensionPixelSize(R.styleable.XBanner_pointContainerLeftRightPadding, mPointContainerLeftRightPadding);
            mPointLeftRightPading = typedArray.getDimensionPixelSize(R.styleable.XBanner_pointLeftRightPadding, mPointLeftRightPading);
            mPointTopBottomPading = typedArray.getDimensionPixelSize(R.styleable.XBanner_pointTopBottomPadding, mPointTopBottomPading);
            mPointContainerPosition = typedArray.getInt(R.styleable.XBanner_pointContainerPosition, BOTTOM);
            mPointContainerBackgroundDrawable = typedArray.getDrawable(R.styleable.XBanner_pointsContainerBackground);
            mPointNoraml = typedArray.getDrawable(R.styleable.XBanner_pointNormal);
            mPointSelected = typedArray.getDrawable(R.styleable.XBanner_pointSelect);
            mPointDrawableResId = typedArray.getResourceId(R.styleable.XBanner_indicatorDrawable, mPointDrawableResId);
            mTipTextColor = typedArray.getColor(R.styleable.XBanner_tipTextColor, mTipTextColor);
            mTipTextSize = typedArray.getDimensionPixelSize(R.styleable.XBanner_tipTextSize, mTipTextSize);
            mIsNumberIndicator = typedArray.getBoolean(R.styleable.XBanner_isShowNumberIndicator, mIsNumberIndicator);
            mNumberIndicatorBackground = typedArray.getDrawable(R.styleable.XBanner_numberIndicatorBacgroud);
            mIsShowIndicatorOnlyOne = typedArray.getBoolean(R.styleable.XBanner_isShowIndicatorOnlyOne, mIsShowIndicatorOnlyOne);
            mPageChangeDuration = typedArray.getInt(R.styleable.XBanner_pageChangeDuration, mPageChangeDuration);
            mPlaceholderDrawableResId = typedArray.getResourceId(R.styleable.XBanner_placeholderDrawable, mPlaceholderDrawableResId);
            mIsClipChildrenMode = typedArray.getBoolean(R.styleable.XBanner_isClipChildrenMode, false);
            mClipChildrenLeftRightMargin = typedArray.getDimensionPixelSize(R.styleable.XBanner_clipChildrenLeftRightMargin, mClipChildrenLeftRightMargin);
            mClipChildrenTopBottomMargin = typedArray.getDimensionPixelSize(R.styleable.XBanner_clipChildrenTopBottomMargin, mClipChildrenTopBottomMargin);
            mViewPagerMargin = typedArray.getDimensionPixelSize(R.styleable.XBanner_viewpagerMargin, mViewPagerMargin);
            typedArray.recycle();
        }

    }

    private void initView() {

        /*设置指示器背景容器*/
        RelativeLayout pointContainerRl = new RelativeLayout(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }

        /*设置内边距*/
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, mPointTopBottomPading, mPointContainerLeftRightPadding, mPointTopBottomPading);

        /*设定指示器容器布局及位置*/
        mPointContainerLp = new LayoutParams(RMP, RWC);
        mPointContainerLp.addRule(mPointContainerPosition);
        addView(pointContainerRl, mPointContainerLp);
        mPointRealContainerLp = new LayoutParams(RWC, RWC);
        /*设置指示器容器*/
        if (mIsNumberIndicator) {
            mNumberIndicatorTv = new TextView(getContext());
            mNumberIndicatorTv.setId(R.id.xbanner_pointId);
            mNumberIndicatorTv.setGravity(Gravity.CENTER);
            mNumberIndicatorTv.setSingleLine(true);
            mNumberIndicatorTv.setEllipsize(TextUtils.TruncateAt.END);
            mNumberIndicatorTv.setTextColor(mTipTextColor);
            mNumberIndicatorTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTextSize);
            mNumberIndicatorTv.setVisibility(View.INVISIBLE);
            if (mNumberIndicatorBackground != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mNumberIndicatorTv.setBackground(mNumberIndicatorBackground);
                } else {
                    mNumberIndicatorTv.setBackgroundDrawable(mNumberIndicatorBackground);
                }
            }
            pointContainerRl.addView(mNumberIndicatorTv, mPointRealContainerLp);
        } else {
            mPointRealContainerLl = new LinearLayout(getContext());
            mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
            mPointRealContainerLl.setId(R.id.xbanner_pointId);
            pointContainerRl.addView(mPointRealContainerLl, mPointRealContainerLp);
        }

        /*设置指示器是否可见*/
        if (mPointRealContainerLl != null) {
            if (mPointsIsVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }

        /*设置提示语*/
        LayoutParams pointLp = new LayoutParams(RMP, RWC);
        pointLp.addRule(CENTER_VERTICAL);
        mTipTv = new TextView(getContext());
        mTipTv.setGravity(Gravity.CENTER_VERTICAL);
        mTipTv.setSingleLine(true);
        if (mIsTipsMarquee) {
            mTipTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTipTv.setMarqueeRepeatLimit(3);
            mTipTv.setSelected(true);
        } else {
            mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        }
        mTipTv.setTextColor(mTipTextColor);
        mTipTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTextSize);
        pointContainerRl.addView(mTipTv, pointLp);

        /*设置指示器布局位置*/
        if (CENTER == mPointPosition) {
            mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            if (mIsClipChildrenMode) {
                mPointRealContainerLp.bottomMargin = mClipChildrenTopBottomMargin;
            }
            pointLp.addRule(RelativeLayout.LEFT_OF, R.id.xbanner_pointId);
        } else if (LEFT == mPointPosition) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mTipTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            if (mIsClipChildrenMode) {
                mPointRealContainerLp.setMargins(mClipChildrenLeftRightMargin, 0, 0, mClipChildrenTopBottomMargin);
            }
            pointLp.addRule(RelativeLayout.RIGHT_OF, R.id.xbanner_pointId);
        } else if (RIGHT == mPointPosition) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            if (mIsClipChildrenMode) {
                mPointRealContainerLp.setMargins(0, 0, mClipChildrenLeftRightMargin, mClipChildrenTopBottomMargin);
            }
            pointLp.addRule(RelativeLayout.LEFT_OF, R.id.xbanner_pointId);
        }

        setBannerPlaceholderDrawable();

    }

    /**
     * 设置图片轮播框架占位图
     */
    private void setBannerPlaceholderDrawable() {
        if (mPlaceholderDrawableResId != NO_PLACE_HOLDER && mPlaceholderImg == null) {
            mPlaceholderImg = new ImageView(getContext());
            mPlaceholderImg.setScaleType(ImageView.ScaleType.FIT_XY);
            mPlaceholderImg.setImageResource(mPlaceholderDrawableResId);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
            addView(mPlaceholderImg, layoutParams);
        }
    }

    /**
     * 移除图片轮播框架占位图
     */
    private void removeBannerPlaceHolderDrawable() {
        if (mPlaceholderImg != null && this.equals(mPlaceholderImg.getParent())) {
            removeView(mPlaceholderImg);
            mPlaceholderImg = null;
        }
    }

    /**
     * 设置bannner数据
     * @param data
     */
    private void setData(@NonNull List<View> views, @NonNull List<?> data, List<String> tips) {

        if (mIsAutoPlay && views.size() < 3 && mLessViews == null) {
            mIsAutoPlay = false;
        }

        if (views.size() < 3) {
            mIsClipChildrenMode = false;
        }

        this.mDatas = data;
        this.mTipData = tips;
        this.mViews = views;

        mIsOneImg = data.size() <= 1;

        initPoints();
        initViewPager();
        removeBannerPlaceHolderDrawable();
        if (!data.isEmpty()) {
            removeBannerPlaceHolderDrawable();
        } else {
            setBannerPlaceholderDrawable();
        }
    }

    public void setData(@LayoutRes int layoutResId, @NonNull List<?> models, List<String> tips) {
        mViews = new ArrayList<>();

        if (models == null) {
            models = new ArrayList<>();
        }

        for (int i = 0; i < models.size(); i++) {
            mViews.add(View.inflate(getContext(), layoutResId, null));
        }
        if (mViews.isEmpty()) {
            mIsAutoPlay = false;
            mIsClipChildrenMode = false;
        }
        if (mIsAutoPlay && mViews.size() < 3) {
            mLessViews = new ArrayList<>(mViews);
            mLessViews.add(View.inflate(getContext(), layoutResId, null));
            if (mLessViews.size() == 2) {
                mLessViews.add(View.inflate(getContext(), layoutResId, null));
            }
        }
        setData(mViews, models, tips);
    }

    /**
     * 设置数据模型和文案，布局资源默认为ImageView
     * @param models 每一页的数据模型集合
     * @param tips   每一页的提示文案集合
     */
    public void setData(@NonNull List<?> models, List<String> tips) {
        setData(R.layout.xbanner_item_image, models, tips);
    }

    /**
     * 设置指示点是否可见
     * @param isVisible
     */
    public void setPointsIsVisible(boolean isVisible) {
        if (null != mPointRealContainerLl) {
            if (isVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 对应三个位置 CENTER,RIGHT,LEFT
     * @param position
     */
    public void setPoinstPosition(@INDICATOR_GRAVITY int position) {
        //设置指示器布局位置
        if (CENTER == position) {
            mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (LEFT == position) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (RIGHT == position) {
            mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }

    /**
     * 设置指示器容器的位置  TOP,BOTTOM
     * @param position
     */
    public void setPointContainerPosition(@INDICATOR_POSITION int position) {
        if (BOTTOM == position) {
            mPointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else if (TOP == position) {
            mPointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        if (mViewPager != null && this.equals(mViewPager.getParent())) {
            this.removeView(mViewPager);
            mViewPager = null;
        }

        mViewPager = new XBannerViewPager(getContext());
        mViewPager.setAdapter(new XBannerPageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOverScrollMode(mSlideScrollMode);
        mViewPager.setIsAllowUserScroll(mIsAllowUserScroll);
        mViewPager.setPageTransformer(true, BasePageTransformer.getPageTransformer(mTransformer));
        setPageChangeDuration(mPageChangeDuration);
        LayoutParams layoutParams = new LayoutParams(RMP, RMP);

        if (mIsClipChildrenMode) {
            mViewPager.setClipChildren(false);
            /*fix 网络图片只有3张或加载本地资源图片的bug*/
            if (!(mDatas.get(0) instanceof Integer) && mDatas.size() > 4) {
                mViewPager.setOffscreenPageLimit(3);
            }
            mViewPager.setPageMargin(mViewPagerMargin);
            setClipChildren(false);
            layoutParams.leftMargin = mClipChildrenLeftRightMargin;
            layoutParams.rightMargin = mClipChildrenLeftRightMargin;
            layoutParams.topMargin = mClipChildrenTopBottomMargin;
            layoutParams.bottomMargin = mClipChildrenTopBottomMargin;
            setPageTransformer(Transformer.Scale);
        }

        addView(mViewPager, 0, layoutParams);

        /*当图片多于1张时开始轮播*/
        if (!mIsOneImg && mIsAutoPlay && getRealCount() != 0) {
            mViewPager.setAutoPlayDelegate(this);
            int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
            mViewPager.setCurrentItem(zeroItem, false);
            startAutoPlay();
        } else {
            if (mIsHandLoop && getRealCount() != 0) {
                int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
                mViewPager.setCurrentItem(zeroItem, false);
            }
            switchToPoint(0);
        }
    }

    /**
     * 获取广告页面数量
     * @return
     */
    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public XBannerViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        mPageScrollPosition = position;
        mPageScrollPositionOffset = positionOffset;

        if (mTipTv != null && mTipData != null && !mTipData.isEmpty()) {
            if (positionOffset > 0.5) {
                mTipTv.setText(mTipData.get((position + 1) % mTipData.size()));
                ViewCompat.setAlpha(mTipTv, positionOffset);
            } else {
                mTipTv.setText(mTipData.get(position % mTipData.size()));
                ViewCompat.setAlpha(mTipTv, 1 - positionOffset);
            }
        }

        if (null != mOnPageChangeListener && getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (getRealCount() == 0) {
            return;
        }
        position = position % getRealCount();
        switchToPoint(position);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void handleAutoPlayActionUpOrCancel(float xVelocity) {
        assert mViewPager != null;
        if (mPageScrollPosition < mViewPager.getCurrentItem()) {
            // 往右滑
            if (xVelocity > VEL_THRESHOLD || (mPageScrollPositionOffset < 0.7f && xVelocity > -VEL_THRESHOLD)) {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition, true);
            } else {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1, true);
            }
        } else {
            // 往左滑
            if (xVelocity < -VEL_THRESHOLD || (mPageScrollPositionOffset > 0.3f && xVelocity < VEL_THRESHOLD)) {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1, true);
            } else {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition, true);
            }
        }
    }

    private class XBannerPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            /*当只有一张图片时返回1*/
            if (mIsOneImg) {
                return 1;
            }
            return mIsAutoPlay ? Integer.MAX_VALUE : (mIsHandLoop ? Integer.MAX_VALUE : getRealCount());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if (getRealCount() == 0) {
                return null;
            }
            final int realPosition = position % getRealCount();

            View view;
            if (mLessViews == null) {
                view = mViews.get(realPosition);
            } else {
                view = mLessViews.get(position % mLessViews.size());
            }

            if (container.equals(view.getParent())) {
                container.removeView(view);
            }

            if (mOnItemClickListener != null && !mDatas.isEmpty()) {
                view.setOnClickListener(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        mOnItemClickListener.onItemClick(XBanner.this, mDatas.get(realPosition), v, realPosition);
                    }
                });
            }

            if (null != mAdapter && !mDatas.isEmpty()) {
                mAdapter.loadBanner(XBanner.this, mDatas.get(realPosition), view, realPosition);
            }

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    /**
     * 添加指示点
     */
    private void initPoints() {
        if (mPointRealContainerLl != null) {
            mPointRealContainerLl.removeAllViews();
            //当图片多于1张时添加指示点
            if (getRealCount() > 0 && (mIsShowIndicatorOnlyOne || !mIsOneImg)) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
                lp.setMargins(mPointLeftRightPading, mPointTopBottomPading, mPointLeftRightPading, mPointTopBottomPading);
                ImageView imageView;
                for (int i = 0; i < getRealCount(); i++) {
                    imageView = new ImageView(getContext());
                    imageView.setLayoutParams(lp);
                    if (mPointNoraml != null && mPointSelected != null) {
                        imageView.setImageDrawable(XBannerUtils.getSelector(mPointNoraml, mPointSelected));
                    } else {
                        imageView.setImageResource(mPointDrawableResId);
                    }
                    mPointRealContainerLl.addView(imageView);
                }
            }
        }

        if (mNumberIndicatorTv != null) {
            if (getRealCount() > 0 && (mIsShowIndicatorOnlyOne || !mIsOneImg)) {
                mNumberIndicatorTv.setVisibility(View.VISIBLE);
            } else {
                mNumberIndicatorTv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 切换指示器
     * @param currentPoint
     */
    private void switchToPoint(int currentPoint) {
        if (mPointRealContainerLl != null & mDatas != null && getRealCount() > 1) {
            for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
                mPointRealContainerLl.getChildAt(i).setEnabled(i == currentPoint);
                mPointRealContainerLl.getChildAt(i).requestLayout();
            }
        }

        if (mTipTv != null && mTipData != null) {
            mTipTv.setText(mTipData.get(currentPoint));
        }

        if (mNumberIndicatorTv != null && mViews != null && (mIsShowIndicatorOnlyOne || !mIsOneImg)) {
            mNumberIndicatorTv.setText(String.valueOf((currentPoint + 1) + "/" + mViews.size()));
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsAutoPlay && !mIsOneImg & mViewPager != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float touchX = ev.getRawX();
                    int paddingLeft = mViewPager.getLeft();
                    if (touchX >= paddingLeft && touchX < XBannerUtils.getScreenWidth(getContext()) - paddingLeft) {
                        stopAutoPlay();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public void startAutoPlay() {
        stopAutoPlay();
        if (mIsAutoPlay) {
            postDelayed(mAutoSwitchTask, mAutoPalyTime);
        }
    }

    public void stopAutoPlay() {
        if (mIsAutoPlay) {
            removeCallbacks(mAutoSwitchTask);
        }
    }

    /**
     * 添加ViewPager滚动监听器
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 设置图片从最后一张滚动到第一张的动画效果
     * @param slideScrollMode
     */
    public void setSlideScrollMode(int slideScrollMode) {
        mSlideScrollMode = slideScrollMode;
        if (null != mViewPager) {
            mViewPager.setOverScrollMode(slideScrollMode);
        }
    }

    /**
     * 设置是否允许用户手指滑动
     * @param allowUserScrollable true表示允许跟随用户触摸滑动，false反之
     */
    public void setAllowUserScrollable(boolean allowUserScrollable) {
        mIsAllowUserScroll = allowUserScrollable;
        if (null != mViewPager) {
            mViewPager.setIsAllowUserScroll(allowUserScrollable);
        }
    }

    /**
     * 设置是否自动轮播
     * @param mAutoPlayAble
     */
    public void setAutoPlayAble(boolean mAutoPlayAble) {
        this.mIsAutoPlay = mAutoPlayAble;
    }

    /**
     * 设置自动轮播时间间隔
     * @param mAutoPalyTime
     */
    public void setAutoPalyTime(int mAutoPalyTime) {
        this.mAutoPalyTime = mAutoPalyTime;
    }

    /**
     * 设置翻页动画效果
     * @param transformer
     */
    public void setPageTransformer(Transformer transformer) {
        mTransformer = transformer;
        if (mViewPager != null && transformer != null) {
            mViewPager.setPageTransformer(true, BasePageTransformer.getPageTransformer(transformer));
        }
    }

    /**
     * 设置viewpager间距
     * @param viewPagerMargin 单位dp
     */
    public void setViewPagerMargin(int viewPagerMargin) {
        this.mViewPagerMargin = viewPagerMargin;
        if (mViewPager != null) {
            mViewPager.setPageMargin(XBannerUtils.dp2px(getContext(), viewPagerMargin));
        }
    }

    /**
     * 自定义翻页动画效果
     * @param transformer
     */
    public void setCustomPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null && mViewPager != null) {
            mViewPager.setPageTransformer(true, transformer);
        }
    }

    public void setClipChildrenLeftRightMargin(int clipChildrenLeftRightMargin) {
        mClipChildrenLeftRightMargin = clipChildrenLeftRightMargin;
    }


    /**
     * 设置ViewPager切换速度
     * @param duration
     */
    public void setPageChangeDuration(int duration) {
        if (mViewPager != null) {
            mViewPager.setScrollDuration(duration);
        }
    }

    /**
     * 设置非自动轮播状态下是否可以循环切换
     * @param handLoop
     */
    public void setHandLoop(boolean handLoop) {
        mIsHandLoop = handLoop;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (VISIBLE == visibility) {
            startAutoPlay();
        } else if (GONE == visibility || INVISIBLE == visibility) {
            onInvisibleToUser();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onInvisibleToUser();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoPlay();
    }

    private static class AutoSwitchTask implements Runnable {
        private final WeakReference<XBanner> mXBanner;

        private AutoSwitchTask(XBanner mXBanner) {
            this.mXBanner = new WeakReference<>(mXBanner);
        }

        @Override
        public void run() {
            XBanner banner = mXBanner.get();
            if (banner != null) {
                if (banner.mViewPager != null) {
                    int currentItem = banner.mViewPager.getCurrentItem() + 1;
                    banner.mViewPager.setCurrentItem(currentItem);
                }
                banner.startAutoPlay();
            }
        }
    }

    private void onInvisibleToUser() {
        stopAutoPlay();
        // 处理 RecyclerView 中从对用户不可见变为可见时卡顿的问题
        if (!mIsFirstInvisible && mIsAutoPlay && mViewPager != null && getRealCount() > 0 && mPageScrollPositionOffset != 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, false);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, false);
        }
        mIsFirstInvisible = false;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(XBanner banner, Object model, View view, int position);
    }

    public interface XBannerAdapter {
        void loadBanner(XBanner banner, Object model, View view, int position);
    }
}
