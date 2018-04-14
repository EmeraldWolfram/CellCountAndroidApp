package com.example.foong.cellcount.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.example.foong.cellcount.R;

/**
 * Created by FOONG on 9/2/2017.
 */

public class KnobView extends View {

    private int maxInt;
    private int minInt;
    private int value;
    private boolean showText;
	
	public KnobView(Context context){
		super(context);
	}


    public KnobView(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.KnobView, 0, 0);

        try {
            maxInt  = a.getInteger(R.styleable.KnobView_maxInt, 10);
            minInt  = a.getInteger(R.styleable.KnobView_minInt, 0);
            value   = a.getInteger(R.styleable.KnobView_valueInt, 5);
            showText= a.getBoolean(R.styleable.KnobView_showLabel, false);


        } finally {
            a.recycle();
        }

    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
        invalidate();
        requestLayout();
    }
	
	public Integer getMaxInt(){
		return maxInt;
	}
	
	public void setMaxInt(Integer maxInt){
		this.maxInt	= maxInt;
		invalidate();
		requestLayout();
	}
	
	public Integer getMinInt(){
		return minInt;
	}
	
	public void setMinInt(Integer minInt){
		this.minInt	= minInt;
		invalidate();
		requestLayout();
	}
	
	public Integer getValue(){
		return value;
	}
	
	public void setValue(Integer value){
		this.value	= value;
		invalidate();
		requestLayout();
	}
	
}


