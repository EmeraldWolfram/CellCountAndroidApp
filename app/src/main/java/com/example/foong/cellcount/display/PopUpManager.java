package com.example.foong.cellcount.display;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by FOONG on 27/1/2017.
 */

public class PopUpManager {

    private Context context;

    public PopUpManager(Context context){
        this.context    = context;
    }

    public void displayException(PopUpException exception){
        switch(exception.getErrorType()){
            case PopUpException.MESSAGE_1_BTN:
                this.showOneBtnMsg(exception);
                break;
            case PopUpException.MESSAGE_2_BTN:
                this.showTwoBtnMsg(exception);
                break;
            case PopUpException.MESSAGE_TOAST:
                this.showToastMsg(exception);
                break;
            case PopUpException.MESSAGE_3_BTN:
                this.showThreeBtnMsh(exception);
                break;
        }
    }

    private void showToastMsg(PopUpException err){
        CustomToast toast   = new CustomToast(context);
        toast.showMessage(err.getErrorMsg(), err.getErrorIcon());
    }

    private void showOneBtnMsg(PopUpException err){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("MESSAGE");
        dialog.setIcon(err.getErrorIcon());
        dialog.setMessage(err.getMessage());
        dialog.setCancelable(false);
        dialog.setNeutralButton(
                err.getBtnText(ButtonWrapper.NEUTRAL_BUTTON),
                getListener(err, ButtonWrapper.NEUTRAL_BUTTON)
        );

        AlertDialog alert = dialog.create();
        alert.show();
    }

    private void showTwoBtnMsg(PopUpException err){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("MESSAGE");
        dialog.setIcon(err.getErrorIcon());
        dialog.setMessage(err.getMessage());
        dialog.setCancelable(true);
        dialog.setOnCancelListener(err.getBackPressListener());
        dialog.setPositiveButton(
                err.getBtnText(ButtonWrapper.POSITIVE_BUTTON),
                getListener(err, ButtonWrapper.POSITIVE_BUTTON)
        );
        dialog.setNegativeButton(
                err.getBtnText(ButtonWrapper.NEGATIVE_BUTTON),
                getListener(err, ButtonWrapper.NEGATIVE_BUTTON)
        );

        AlertDialog alert = dialog.create();
        alert.show();
    }

    private void showThreeBtnMsh(PopUpException err){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("MESSAGE");
        dialog.setIcon(err.getErrorIcon());
        dialog.setMessage(err.getMessage());
        dialog.setCancelable(true);
        dialog.setOnCancelListener(err.getBackPressListener());
        dialog.setPositiveButton(
                err.getBtnText(ButtonWrapper.POSITIVE_BUTTON),
                getListener(err, ButtonWrapper.POSITIVE_BUTTON)
        );
        dialog.setNegativeButton(
                err.getBtnText(ButtonWrapper.NEGATIVE_BUTTON),
                getListener(err, ButtonWrapper.NEGATIVE_BUTTON)
        );
        dialog.setNeutralButton(
                err.getBtnText(ButtonWrapper.NEUTRAL_BUTTON),
                getListener(err, ButtonWrapper.NEUTRAL_BUTTON)
        );

        AlertDialog alert = dialog.create();
        alert.show();
    }

    private DialogInterface.OnClickListener getListener(PopUpException err, short buttonPosition){
        DialogInterface.OnClickListener listener = err.getListener(buttonPosition);
        if(listener == null){
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
        }

        return listener;
    }
}
