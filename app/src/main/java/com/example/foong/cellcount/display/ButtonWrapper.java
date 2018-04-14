package com.example.foong.cellcount.display;

import android.content.DialogInterface;

/**
 * Created by FOONG on 30/1/2017.
 */

public class ButtonWrapper {

    public static final short POSITIVE_BUTTON   = 0;
    public static final short NEUTRAL_BUTTON    = 1;
    public static final short NEGATIVE_BUTTON   = 2;

    private String buttonText;
    private DialogInterface.OnClickListener onClickListener;

    public ButtonWrapper(String buttonText, DialogInterface.OnClickListener onClickListener){
        this.buttonText         = buttonText;
        this.onClickListener    = onClickListener;
    }

    public DialogInterface.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public String getButtonText() {
        return buttonText;
    }
}
