package com.example.foong.cellcount.model;

import android.content.SharedPreferences;

import com.example.foong.cellcount.database.CountRecord;
import com.example.foong.cellcount.database.DatabaseLoader;
import com.example.foong.cellcount.interfacer.MainMvp;

/**
 * Created by FOONG on 22/1/2017.
 */

public class MainModel implements MainMvp.MvpModel {

    private Integer viableCount;
    private Integer nonViableCount;
    private Integer numOfSquare;
    private Double dFactor;

    private MainMvp.MvpMPresenter taskPresenter;
    private SharedPreferences preferences;
    private DatabaseLoader dbLoader;

    public MainModel(MainMvp.MvpMPresenter taskPresenter,
                     SharedPreferences preferences,
                     DatabaseLoader dbLoader){
        this.taskPresenter  = taskPresenter;
        this.preferences    = preferences;
        this.dbLoader       = dbLoader;
        this.viableCount    = 0;
        this.nonViableCount = 0;
        this.numOfSquare    = Integer.parseInt(preferences.getString("NumOfSquare", "5"));
        Integer finalVolume = Integer.parseInt(preferences.getString("FinalVolume", "10"));
        Integer volumeOfCell= Integer.parseInt(preferences.getString("VolumeOfCell", "10"));
        this.dFactor        = ((double)finalVolume) / ((double)volumeOfCell);
    }

    @Override
    public void notifyPrefChanges() {
        this.numOfSquare    = Integer.parseInt(preferences.getString("NumOfSquare", "5"));
        Integer finalVolume = Integer.parseInt(preferences.getString("FinalVolume", "10"));
        Integer volumeOfCell= Integer.parseInt(preferences.getString("VolumeOfCell", "10"));

        this.dFactor    = (double)finalVolume / (double)volumeOfCell;

        taskPresenter.updateDilutionFactor(dFactor);
        taskPresenter.updateConcentration((double) viableCount * dFactor * 10000 / (double) numOfSquare);
    }

    @Override
    public void resetTray() {
        this.viableCount    = 0;
        this.nonViableCount = 0;
        this.dFactor        = 0.0;
    }

    @Override
    public void saveRecord(String name) {
        CountRecord countRecord = new CountRecord();
        if(name != null){
            if(!name.isEmpty()){
                countRecord.setRecordName(name);
            }
        }
        countRecord.setViableCount(viableCount);
        countRecord.setNonViableCount(nonViableCount);
        countRecord.setdFactor(dFactor);
        countRecord.setNumOfSquare(numOfSquare);

        dbLoader.saveToDatabase(countRecord);

        taskPresenter.notifySaved(countRecord.getRecordName());
    }

    @Override
    public void viableIncremented() {
        taskPresenter.updateViableCount(++viableCount);
        taskPresenter.updateCellPerSquare(((double) viableCount / (double) numOfSquare));
        double p = ((double) viableCount / ((double) viableCount + (double) nonViableCount) * 100);
        taskPresenter.updatePercentage(p);
        taskPresenter.updateConcentration((double) viableCount * dFactor * 10000 / (double) numOfSquare);
    }

    @Override
    public void nonViableIncremented() {
        taskPresenter.updateNonViableCount(++nonViableCount);
        taskPresenter.updateCellPerSquare(((double) viableCount / (double) numOfSquare));
        double p = ((double) viableCount / ((double) viableCount + (double) nonViableCount) * 100);
        taskPresenter.updatePercentage(p);
    }
}
