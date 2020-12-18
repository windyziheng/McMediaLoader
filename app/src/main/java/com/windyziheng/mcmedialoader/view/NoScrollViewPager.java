package com.windyziheng.mcmedialoader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 继承ViewPager ，解决ViewPager与PhotoView多点触控冲突导致闪退的bug
 * https://www.jianshu.com/p/948fa4c39a2d
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-15
 * @Organization Convergence Ltd.
 */
public class NoScrollViewPager extends ViewPager {

    private boolean isScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 1.dispatchTouchEvent一般情况不做处理
     * ,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // return true;不行
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可行,不拦截事件,
        // return true;//不行,孩子无法处理事件
        //return super.onInterceptTouchEvent(ev);//不行,会有细微移动
        try {
            if (isScroll) {
                return super.onInterceptTouchEvent(ev);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可行,不消费,传给父控件
        //return true;// 可行,消费,拦截事件
        //super.onTouchEvent(ev); //不行,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage里面子控件不是viewgroup,还是会调用这个方法.
        try {
            if (isScroll) {
                return super.onTouchEvent(ev);
            } else {
                // 可行,消费,拦截事件
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
