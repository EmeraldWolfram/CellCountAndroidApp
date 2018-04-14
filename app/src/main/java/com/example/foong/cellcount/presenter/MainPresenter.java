package com.example.foong.cellcount.presenter;

import com.example.foong.cellcount.R;
import com.example.foong.cellcount.display.PopUpException;
import com.example.foong.cellcount.interfacer.MainMvp;

import java.text.DecimalFormat;

/**
 * Created by FOONG on 22/1/2017.
 */

public class MainPresenter implements MainMvp.MvpVPresenter, MainMvp.MvpMPresenter{

    private MainMvp.MvpView     taskView;
    private MainMvp.MvpModel    taskModel;
    private DecimalFormat       dcFormat;


    public MainPresenter(MainMvp.MvpView taskView){
        this.taskView   = taskView;
        this.dcFormat   = new DecimalFormat("#.####");
    }

    public void setTaskModel(MainMvp.MvpModel taskModel) {
        this.taskModel = taskModel;
    }

    @Override
    public void onResume() {
        taskModel.notifyPrefChanges();
    }

    @Override
    public void onSave() {
        taskView.promptInsertName();
    }

    @Override
    public void onSave(String name) {
        taskModel.saveRecord(name);
    }

    @Override
    public void onReset() {
        taskModel.resetTray();
        taskView.setViableCount("0");
        taskView.setNonViableCount("0");
        taskView.setPercentage("0 %");
        taskView.setCellPerSquare("0");
        taskView.setDilutionFactor("-");
        taskView.setConcentration("-");
    }

    @Override
    public void onNonViable() {
        taskModel.nonViableIncremented();
    }

    @Override
    public void onViable() {
        taskModel.viableIncremented();
    }

    @Override
    public void updateCellPerSquare(Double cellPerSquare) {
        taskView.setCellPerSquare(dcFormat.format(cellPerSquare) + " cells/sq");
    }

    @Override
    public void updateConcentration(Double concentration) {
        taskView.setConcentration(dcFormat.format(concentration) + " viable cells/ml");
    }

    @Override
    public void updateDilutionFactor(Double dilutionFactor) {
        taskView.setDilutionFactor(dcFormat.format(dilutionFactor));
    }

    @Override
    public void updateNonViableCount(Integer numOfNonViable) {
        taskView.setNonViableCount(numOfNonViable.toString());
    }

    @Override
    public void updatePercentage(Double percentage) {
        taskView.setPercentage(dcFormat.format(percentage) + " %");
    }

    @Override
    public void updateViableCount(Integer numOfViable) {
        taskView.setViableCount(numOfViable.toString());
    }

    @Override
    public void notifySaved(String sampleName) {
        taskView.displayMessage(new PopUpException(sampleName + " saved",
                PopUpException.MESSAGE_TOAST, R.drawable.icon_file_save));
    }
}
