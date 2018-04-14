package com.example.foong.cellcount.interfacer;

import com.example.foong.cellcount.display.PopUpException;
import com.example.foong.cellcount.display.SwipeAnimator;

/**
 * Created by FOONG on 22/1/2017.
 */

public interface MainMvp {
    interface MvpView extends SwipeAnimator.OnSwipeListener, ActivityView{
        void promptInsertName();
        void setViableCount(String count);
        void setNonViableCount(String count);
        void setPercentage(String percent);
        void setCellPerSquare(String cellPerSquare);
        void setDilutionFactor(String dilutionFactor);
        void setConcentration(String concentration);
    }

    interface MvpVPresenter{
        void onViable();
        void onNonViable();
        void onResume();
        void onSave();
        void onSave(String name);
        void onReset();
    }

    interface MvpMPresenter{
        void updateViableCount(Integer numOfViable);
        void updateNonViableCount(Integer numOfNonViable);
        void updatePercentage(Double percentage);
        void updateCellPerSquare(Double cellPerSquare);
        void updateDilutionFactor(Double dilutionFactor);
        void updateConcentration(Double concentration);

        void notifySaved(String sampleName);
    }

    interface MvpModel{
        void saveRecord(String name);
        void resetTray();
        void notifyPrefChanges();
        void viableIncremented();
        void nonViableIncremented();
    }
}
