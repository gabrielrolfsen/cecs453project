package edu.csulb.android.smartbook.views;

import edu.csulb.android.cecs453project.R;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class MenuAnimation implements AnimationListener 
{
    Context context;

    RelativeLayout filterLayout, otherLayout;

    private Animation filterSlideIn, filterSlideOut, otherSlideIn, otherSlideOut;

    private static int otherLayoutWidth, otherLayoutHeight;

    private boolean isOtherSlideOut = false;

    private int deviceWidth;

    private int margin;

    public MenuAnimation(Context context) 
    {
        this.context = context;

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        deviceWidth = displayMetrics.widthPixels; // as my animation is x-axis related so i gets the device width and will use that width,so that this sliding menu will work fine in all screen resolutions
    }

    public void initializeFilterAnimations(RelativeLayout filterLayout)
    {
        this.filterLayout = filterLayout;

        filterSlideIn = AnimationUtils.loadAnimation(context, R.anim.menu_slide_in);

        filterSlideOut = AnimationUtils.loadAnimation(context, R.anim.menu_slide_out);    

    }

    public void initializeOtherAnimations(RelativeLayout otherLayout)
    {       
        this.otherLayout = otherLayout;

        otherLayoutWidth = otherLayout.getWidth();

        otherLayoutHeight = otherLayout.getHeight();


       otherSlideIn = AnimationUtils.loadAnimation(context, R.anim.other_slide_in);
        otherSlideIn.setAnimationListener(this);

       otherSlideOut = AnimationUtils.loadAnimation(context, R.anim.other_slide_out);
        otherSlideOut.setAnimationListener(this);
    }

    public void toggleSliding()
    {
        if(isOtherSlideOut) //check if findLayout is already slided out so get so animate it back to initial position
        {

            filterLayout.startAnimation(filterSlideOut);

            filterLayout.setVisibility(View.INVISIBLE);

            otherLayout.startAnimation(otherSlideIn);

        }
        else //slide findLayout Out and filterLayout In
        {
            otherLayout.startAnimation(otherSlideOut);

            filterLayout.setVisibility(View.VISIBLE);

            filterLayout.startAnimation(filterSlideIn);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) 
    {
        if(isOtherSlideOut) //Now here we will actually move our view to the new position,because animations just move the pixels not the view
        {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(otherLayoutWidth, otherLayoutHeight);

            otherLayout.setLayoutParams(params);

            isOtherSlideOut = false;
        }
        else
        {   
            margin = (deviceWidth * 80) / 100; //here im coverting device percentage width into pixels, in my other_slide_in.xml or other_slide_out.xml you can see that i have set the android:toXDelta="80%",so it means the layout will move to 80% of the device screen,to work across all screens i have converted percentage width into pixels and then used it



            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(otherLayoutWidth, otherLayoutHeight);

            params.leftMargin = margin;

            params.rightMargin = -margin; //same margin from right side (negavite) so that our layout won't get shrink


            otherLayout.setLayoutParams(params);

            isOtherSlideOut = true;

            dimOtherLayout();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) 
    {

    }

    @Override
    public void onAnimationStart(Animation animation) 
    {

    }

    private void dimOtherLayout()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);

        alphaAnimation.setFillAfter(true);

        otherLayout.startAnimation(alphaAnimation);
    }

}
