package com.menicorp.moviematch.ui.main

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.menicorp.moviematch.R

fun Animator.doOnEnd(action: () -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) { action() }
        override fun onAnimationRepeat(animation: Animator) {}
    })
}

class TinderStackRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private var swipeEnabled = true
    private var minSwipeDistance = 100f
    private var minFlingVelocity = 1000f

    private var lastX = 0f
    private var isDragging = false
    private var currentCardView: View? = null
    private var currentCardPosition = 0

    init {
        overScrollMode = OVER_SCROLL_NEVER
        clipToPadding = false
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        
        val childCount = childCount
        if (childCount > 0) {
            val lastChild = getChildAt(childCount - 1)
            val layoutParams = lastChild?.layoutParams as LayoutParams
            val totalHeight = lastChild?.measuredHeight?.plus(layoutParams.bottomMargin) ?: 0
            setMeasuredDimension(getMeasuredWidth(), totalHeight)
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        
        val childCount = childCount
        if (childCount >= 2) {
            val topChild = getChildAt(0)
            val bottomChild = getChildAt(1)
            
            val topChildHeight = topChild?.height ?: 0
            val offset = Math.max(0, t) / topChildHeight.toFloat()
            
            bottomChild?.let { child ->
                child.scaleX = 1f - offset * 0.1f
                child.scaleY = 1f - offset * 0.1f
                child.translationY = t.toFloat() * 0.5f
                child.alpha = 1f - offset * 0.3f
            }
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        if (!swipeEnabled) {
            return super.onInterceptTouchEvent(e)
        }

        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = e.x
                isDragging = false
                currentCardView = findChildViewUnder(e.x, e.y)
                currentCardPosition = if (currentCardView != null) getChildAdapterPosition(currentCardView!!) else -1
            }
            MotionEvent.ACTION_MOVE -> {
                val x = e.x
                val dx = x - lastX
                
                if (Math.abs(dx) > ViewConfiguration.get(context).scaledTouchSlop) {
                    isDragging = true
                }
                
                lastX = x
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
            }
        }
        
        return isDragging && currentCardPosition == 0
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (!swipeEnabled || currentCardPosition != 0) {
            return super.onTouchEvent(e)
        }

        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = e.x
                val dx = x - lastX
                
                currentCardView?.translationX = dx
                currentCardView?.scaleX = 1f - Math.abs(dx) / (measuredWidth * 2f)
                
                val rotateAngle = dx / measuredWidth * 20f
                currentCardView?.rotation = rotateAngle.coerceIn(-20f, 20f)
                
                lastX = x
                
                val swipeListener = onSwipeListener
                if (swipeListener != null) {
                    if (dx > 0) {
                        swipeListener.onSwipeProgress(0, dx / measuredWidth)
                    } else {
                        swipeListener.onSwipeProgress(0, dx / measuredWidth)
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val x = e.x
                val dx = x - lastX
                
                if (Math.abs(dx) > minSwipeDistance) {
                    val isLike = dx > 0
                    currentCardView?.let { card ->
                        val animator = ValueAnimator.ofFloat(card.translationX, if (isLike) width.toFloat() else -width.toFloat())
                        animator.duration = 300
                        animator.addUpdateListener { animation ->
                            card.translationX = animation.animatedValue as Float
                            card.alpha = 1f - Math.abs(card.translationX) / width.toFloat()
                        }
                        animator.start()
                        animator.doOnEnd {
                            if (adapter != null && adapter!!.itemCount > 0) {
                                adapter?.notifyItemRemoved(0)
                                smoothScrollToPosition(0)
                            }
                            val listener = onSwipeListener
                            if (isLike && listener != null) {
                                listener.onLiked(currentCardPosition)
                            } else if (listener != null) {
                                listener.onDisliked(currentCardPosition)
                            }
                        }
                    }
                } else {
                    currentCardView?.let { card ->
                        val animator = ValueAnimator.ofFloat(card.translationX, 0f)
                        animator.duration = 300
                        animator.addUpdateListener { animation ->
                            card.translationX = animation.animatedValue as Float
                            card.rotation = 0f
                            card.scaleX = 1f
                            card.alpha = 1f
                        }
                        animator.start()
                    }
                }
                
                isDragging = false
                currentCardView = null
            }
        }
        
        return true
    }

    fun enableSwipe(enable: Boolean) {
        swipeEnabled = enable
    }

    fun setMinSwipeDistance(distance: Float) {
        minSwipeDistance = distance
    }

    fun setMinFlingVelocity(velocity: Float) {
        minFlingVelocity = velocity
    }

    interface OnSwipeListener {
        fun onLiked(position: Int)
        fun onDisliked(position: Int)
        fun onSwipeProgress(position: Int, progress: Float)
    }

    private var onSwipeListener: OnSwipeListener? = null

    fun setOnSwipeListener(listener: OnSwipeListener) {
        onSwipeListener = listener
    }
}
