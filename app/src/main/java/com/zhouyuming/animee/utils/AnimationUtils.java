package com.zhouyuming.animee.utils;

import android.animation.Animator;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by ZhouYuming on 2017/7/16.
 */

public class AnimationUtils {

	public static void playStateChange(ImageView imageView, int resId) {
		int width = imageView.getWidth();
		int height = imageView.getHeight();

		float radius = (float) Math.hypot(width * 0.5, height * 0.5);
		Animator animator1 = ViewAnimationUtils.createCircularReveal(imageView, width / 2, height / 2, radius, 0.0f);
		Animator animator2 = ViewAnimationUtils.createCircularReveal(imageView, width / 2, height / 2, 0.0f, radius);

		animator1.setDuration(500);
		animator2.setDuration(500);

		animator1.start();
		animator1.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animator) {}

			@Override
			public void onAnimationEnd(Animator animator) {
				imageView.setImageResource(resId);
				animator2.start();
			}

			@Override
			public void onAnimationCancel(Animator animator) {}

			@Override
			public void onAnimationRepeat(Animator animator) {}
		});
	}
}
