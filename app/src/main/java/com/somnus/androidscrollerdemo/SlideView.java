package com.somnus.androidscrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * @date： 2016/5/12.
 * @FileName: com.somnus.androidscrollerdemo.SlideView.java
 * @author: Somnus
 * @Description:
 */
public class SlideView extends LinearLayout {
    private Context mContext;
    private LinearLayout mViewContent;
    private LinearLayout mHolder;
    private TextView tv_delete;
    // 弹性滑动对象,实现View平滑滚动的一个帮助类
    private Scroller mScroller;

    // 滑动回调接口，用来向上层通知滑动事件
    private OnSlideListener mOnSlideListener;

    private int mHolderWidth = 100;

    private int mLastX = 0;
    private int mLastY = 0;
    private static final int TAN = 2;

    public SlideView(Context context) {
        super(context);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mContext = getContext();
        mScroller = new Scroller(mContext);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        View.inflate(mContext, R.layout.slide_view, this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        mHolder = (LinearLayout) findViewById(R.id.holder);
        tv_delete = (TextView) findViewById(R.id.delete);
    }

    public void setButtonText(CharSequence text) {
        tv_delete.setText(text);
    }

    public void setContentView(View view) {
        mViewContent.addView(view);
    }

    public void onRequireTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int scrollX = getScrollX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this, OnSlideListener.SLIDE_STATUS_START_SCROLL);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
// 滑动不满足条件 不做横向滑动
                    break;
                }
                int newScrollX = scrollX - deltaX;
                if (deltaX != 0) {
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    this.scrollTo(newScrollX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                int newScrollx = 0;
                if (scrollX - mHolderWidth * 0.75 > 0) {
                    newScrollx = mHolderWidth;
                }
                this.smoothScrollTo(newScrollx, 0);
// 通知上层滑动事件
                if (mOnSlideListener != null) {
                    mOnSlideListener.onSlide(this, newScrollx == 0 ? OnSlideListener.SLIDE_STATUS_OFF
                            : OnSlideListener.SLIDE_STATUS_ON);
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
    }

    /**
     * 134.
     * 调用此方法滚动到目标位置
     * 135.
     *
     * @param fx 目标x坐标
     *           136.
     * @param fy 目标Y坐标
     *           137.
     */
    private void smoothScrollTo(int fx, int fy) {
        int scrollX = getScrollX();
        int dx = fx - scrollX;
        int scrollY = getScrollY();
        int dy = fy - scrollY;
//设置mScroller的滚动偏移量
        mScroller.startScroll(scrollX, scrollY, dx, dy, Math.abs((dx) * 3));
        invalidate();
    }

    /**
     * 152.
     * 由mScroller记录/计算好View滚动的位置后，最后由View的computeScroll()，完成实际的滚动
     * 153.
     */
    @Override
    public void computeScroll() {
//先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
//这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 168.
     * 设置滑动回调
     * 169.
     *
     * @param onSlideListener 170.
     */
    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.mOnSlideListener = onSlideListener;
    }

    public interface OnSlideListener {
        public static final int SLIDE_STATUS_OFF = 0;
        public static final int SLIDE_STATUS_START_SCROLL = 1;
        public static final int SLIDE_STATUS_ON = 2;

        public void onSlide(View view, int status);
    }

}
