package com.zhouyuming.animee.behavior;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by ZhouYuming on 2017/2/17.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

	private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
	private boolean mIsAnimatingOut = false;

	public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
		super();
	}

	@Override
	public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
									   final View directTargetChild, final View target, final int nestedScrollAxes) {
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
				|| super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
							   final View target, final int dxConsumed, final int dyConsumed,
							   final int dxUnconsumed, final int dyUnconsumed) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
		if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
			animateOut(child);
		} else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
			animateIn(child);
		}
	}

	private void animateOut(final FloatingActionButton button) {
		ViewCompat.animate(button).translationY(button.getHeight() + getMarginBottom(button)).setInterpolator(INTERPOLATOR).withLayer()
				.setListener(new ViewPropertyAnimatorListener() {
					public void onAnimationStart(View view) {
						ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
						button.setEnabled(false);
					}

					public void onAnimationCancel(View view) {
						ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
						button.setEnabled(true);
					}

					public void onAnimationEnd(View view) {
						ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
						view.setVisibility(View.GONE);
						button.setEnabled(false);
					}
				}).start();
	}

	private void animateIn(final FloatingActionButton button) {
		button.setVisibility(View.VISIBLE);
		ViewCompat.animate(button).translationY(0)
				.setInterpolator(INTERPOLATOR).withLayer().setListener(null).setListener(new ViewPropertyAnimatorListener() {
			@Override
			public void onAnimationStart(View view) {
				button.setEnabled(false);
			}

			@Override
			public void onAnimationEnd(View view) {
				button.setEnabled(true);
			}

			@Override
			public void onAnimationCancel(View view) {
				button.setEnabled(false);
			}
		}).start();
	}

	private int getMarginBottom(View v) {
		int marginBottom = 0;
		final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
		}
		return marginBottom;
	}
}
