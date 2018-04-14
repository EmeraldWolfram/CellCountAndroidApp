package com.example.foong.cellcount.display;

import android.content.DialogInterface;

import java.util.HashMap;

/**
 * Created by FOONG on 30/1/2017.
 */

public class PopUpException extends Exception {
    public static final int MESSAGE_TOAST   = 0;
    public static final int MESSAGE_1_BTN   = 1;
    public static final int MESSAGE_2_BTN   = 2;
    public static final int MESSAGE_3_BTN   = 3;

    private HashMap<Short, ButtonWrapper> buttonMap;
    private DialogInterface.OnCancelListener backPressListener;
    private int errorType;
    private String errorMsg;
    private int errorIconType;

    public PopUpException(int errorType){
        this.errorType      = errorType;
        this.errorMsg       = null;
        this.errorIconType  = 0;
    }

    public PopUpException(String errMsg, int errType, int errIconType){
        super(errMsg);
        this.errorType      = errType;
        this.errorMsg       = errMsg;
        this.errorIconType  = errIconType;
        this.buttonMap      = new HashMap<>();
    }

    public void setListener(short buttonPosition,
                            String btnText,
                            DialogInterface.OnClickListener listener){
        buttonMap.put(buttonPosition, new ButtonWrapper(btnText, listener));
    }

    public void setBackPressListener(DialogInterface.OnCancelListener backPressListener) {
        this.backPressListener = backPressListener;
    }

    public DialogInterface.OnClickListener getListener(short buttonPosition){
        return buttonMap.get(buttonPosition).getOnClickListener();
    }

    public String getBtnText(short buttonPosition){
        return buttonMap.get(buttonPosition).getButtonText();
    }

    public DialogInterface.OnCancelListener getBackPressListener() {
        if(backPressListener == null){
            return new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            };
        } else {
            return backPressListener;
        }
    }

    public int getErrorType(){
        return errorType;
    }

    public String getErrorMsg() {
        if(errorMsg == null){
            errorMsg = "";
        }
        return errorMsg;
    }

    public int getErrorIcon(){
        return 0;
    }

}
