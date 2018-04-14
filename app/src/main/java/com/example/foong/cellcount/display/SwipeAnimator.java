package com.example.foong.cellcount.display;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.foong.cellcount.R;

/**
 * Created by FOONG on 27/1/2017.
 */

public class SwipeAnimator implements View.OnTouchListener {

    float dX;
    float startingX, endingX, viewChanges;

    private Animation exitToRight, exitToLeft;
    private Animation enterFromRight, enterFromLeft;
    private OnSwipeListener swipeListener;

    public SwipeAnimator(Context context, final ViewGroup containerLayout, OnSwipeListener swipeListener){
        exitToLeft      = AnimationUtils.loadAnimation(context, R.anim.slide_out_at_left);
        exitToRight     = AnimationUtils.loadAnimation(context, R.anim.slide_out_at_right);
        enterFromLeft   = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left);
        enterFromRight  = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_right);
        this.swipeListener    = swipeListener;

        enterFromRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                containerLayout.animate().translationX(0).setDuration(300).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        enterFromLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                containerLayout.animate().translationX(0).setDuration(300).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        exitToLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                containerLayout.startAnimation(enterFromRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        exitToRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                containerLayout.startAnimation(enterFromLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startingX = view.getX();
                dX = startingX - event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                view.animate().x(event.getRawX() + dX).setDuration(0).start();
                break;
            case MotionEvent.ACTION_UP:
                endingX     = view.getX();
                viewChanges = endingX - startingX;
                if(viewChanges > 300){
                    view.startAnimation(exitToRight);
                    swipeListener.onSwipedRight();
                } else if(viewChanges < -300) {
                    view.startAnimation(exitToLeft);
                    swipeListener.onSwipedLeft();
                } else {
                    view.animate().translationX(0).setDuration(300).start();
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public interface OnSwipeListener {
        void onSwipedLeft();
        void onSwipedRight();
    }
}
