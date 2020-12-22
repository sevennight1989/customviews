package com.android.custview.layout

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs


class MyLayoutManager : RecyclerView.LayoutManager() {
    /**
     * 最小的缩放
     */
    private val minScanSize = 0.8f
    /**
     * 最大的缩放
     */
    private val maxScanSize = 1.2f
    private var first_child_margin = 0
    private var child_widht = 0
    private var child_height = 0
    /**
     * 第一个控件的偏移量
     */
    private var horizontal_offset = 0
    private val width_offset = 25
    /**
     * 可见的高度
     */
    private var show_width = 0
    private var selectAnimator: ValueAnimator? = null
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (itemCount == 0 || state.isPreLayout) { ///没有数据或者是 执行动画期间
            return
        }
        val child: View? = if (childCount == 0) { ///屏幕上没有数据,
            recycler.getViewForPosition(0)
        } else {
            getChildAt(0) //执行requestLayout 后,屏幕上就有数据,所以执行的是这个方法
        }
        measureChildWithMargins(child!!, 0, 0) //测量child  由于所有child 的宽高都是一致的,
        child_height = getDecoratedMeasuredHeight(child) //或者child 的高度
        child_widht = getDecoratedMeasuredWidth(child) //获取child 的宽度
        first_child_margin = width / 2 - child_widht / 2 ///前面预留的宽度  recyclerview 的宽度除以2 - child的宽度除以2
        show_width = width
        layoutItem(recycler, state, 0)
    }

    /**
     * 根据偏移量 重新布局item
     * @param recycler
     * @param state
     * @param dx
     */
    private fun layoutItem(recycler: Recycler, state: RecyclerView.State, dx: Int) {
        if (state.isPreLayout) {
            return
        }
        detachAndScrapAttachedViews(recycler) ///暂时先detach
        for (i in 0 until itemCount) {
            if (isOutOfRange(i)) { ///如果在屏幕外
                continue
            }
            val child: View = recycler.getViewForPosition(i) ///或者到这个view
            measureChildWithMargins(child, 0, 0) ///重新测量,否则view 在复用的时候即使执行了bindViewHolder,也没有数据
            addView(child) //重新add
            ///这个左边应该是减去偏移的左边
            val left = first_child_margin + (child_widht + width_offset) * i
            setScanAnim(child, left) ///设置缩放动画
            layoutDecoratedWithMargins(child, left - horizontal_offset, 0, left + child_widht - horizontal_offset, child_height)
        }
        for (m in 0 until childCount) {
            val child: View? = getChildAt(m)
            val i = getPosition(child!!)
            if (isOutOfRange(i)) { ///如果在屏幕外面
                removeAndRecycleView(child, recycler) //在屏幕外则回收
            }
        }
    }

    /**
     * 判断是否在屏幕外
     *
     * 控件的右边小于偏移量或者左边大于偏移量+ 控件的的宽度
     *
     * @param position
     * @return
     */
    private fun isOutOfRange(position: Int): Boolean {
        val child_left = position * (child_widht + width_offset) + first_child_margin
        val child_right = (position + 1) * (width_offset + child_widht) + first_child_margin
        return child_right < horizontal_offset || child_left > horizontal_offset + show_width
    }

    /**
     * 缩放
     * @param child
     * @param left
     */
    private fun setScanAnim(child: View, left: Int) {
        val child_center = (left - horizontal_offset + left + child_widht - horizontal_offset) / 2
        val parentCenter = width / 2
        val scale: Float
        scale = if (child_center > parentCenter) {
            1.0f - (1 - 0.8f) * ((child_center - parentCenter) / (parentCenter * 1.0f))
        } else {
            1.0f - (1 - 0.8f) * ((parentCenter - child_center) / (parentCenter * 1.0f))
        }
        child.scaleX = scale
        child.scaleY = scale
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        var index = dx
        if (index + horizontal_offset < 0) { ///修正偏移量
            index = -horizontal_offset
        } else if (index + horizontal_offset > maxScrollWidth) { /////修正偏移量
            index = maxScrollWidth - horizontal_offset
        }
        horizontal_offset += index
        //        ///通知控件水平移动  非常重要,要不然不会移动  该方法必须要放在   offsetChildrenHorizontal 之前,否则有问题,
        layoutItem(recycler, state, index)
        offsetChildrenHorizontal(index)
        return index
    }

    private val maxScrollWidth: Int
        get() = itemCount * child_widht - width / 2 + first_child_margin - child_widht / 2 + width_offset * (itemCount + 1)

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            RecyclerView.SCROLL_STATE_DRAGGING ->  //当手指按下时，停止当前正在播放的动画
                cancelAnimator()
            RecyclerView.SCROLL_STATE_IDLE -> smoothScrollToPosition(findShouldSelectPosition())
        }
    }

    private fun smoothScrollToPosition(position: Int) {
        if (position > -1 && position < itemCount) {
            startValueAnimator(position)
        }
    }

    private fun cancelAnimator() {
        if (selectAnimator != null && (selectAnimator!!.isStarted || selectAnimator!!.isRunning)) {
            selectAnimator!!.cancel()
        }
    }

    /**
     * 开启动画,
     * @param position
     */
    private fun startValueAnimator(position: Int) {
        cancelAnimator()
        val distance = getScrollToPositionOffset(position)
        val minDuration: Long = 100
        val maxDuration: Long = 300
        val duration: Long
        val distanceFraction = Math.abs(distance) / (child_widht + width_offset)
        duration = if (distance <= child_widht + width_offset) {
            (minDuration + (maxDuration - minDuration) * distanceFraction).toLong()
        } else {
            (maxDuration * distanceFraction).toLong()
        }
        selectAnimator = ValueAnimator.ofFloat(0.0f, distance)
        selectAnimator!!.duration = duration
        selectAnimator!!.interpolator = LinearInterpolator()
        val startedOffset = horizontal_offset.toFloat()
        selectAnimator!!.addUpdateListener(AnimatorUpdateListener { animation ->
            val value = animation.animatedValue as Float
            horizontal_offset = (startedOffset + value).toInt()
            requestLayout()
        })
        selectAnimator!!.start()
    }

    /**
     * 计算这个位置的时候 少减去 first_child_margin 后,正好让下一个view 处于屏幕中央
     *
     * @param position
     * @return
     */
    private fun getScrollToPositionOffset(position: Int): Float {
        return (position * (child_widht + width_offset) - abs(horizontal_offset)).toFloat()
    }

    /**
     * 计算这个position的时候 少减去 first_child_margin 后,正好让下一个view 处于屏幕中央
     *
     * @return
     */
    private fun findShouldSelectPosition(): Int {
        if (itemCount == 0) {
            return -1
        }
        val position = (abs(horizontal_offset) / (child_widht + width_offset))
        // 超过一半，应当选中下一项
        if ((abs(horizontal_offset) % (child_widht + width_offset)) >= (child_widht + width_offset) / 2.0f) {
            if (position + 1 <= itemCount - 1) {
                return position + 1
            }
        }
        return position
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    init {
        horizontal_offset = 0
    }
}